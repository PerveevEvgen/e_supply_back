package ua.kpi.diploma.e_supply.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.diploma.e_supply.dto.reportDto.ReportRequestDTO;
import ua.kpi.diploma.e_supply.dto.reportDto.ReportResponseDTO;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PdfReportService {
    private final S3Util S3Util;

    public void generatePdfReport(List<ReportResponseDTO> reportData) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            Paragraph title = new Paragraph("Звіт по переміщенням майна", headerFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            Stream.of("ID", "Назва майна", "Операція", "Звідки", "Куди", "Дата", "Документ")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setPhrase(new Phrase(columnTitle));
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        table.addCell(header);
                    });

            for (ReportResponseDTO item : reportData) {
                table.addCell(item.getItemId().toString());
                table.addCell(item.getItemName());
                table.addCell(item.getOperationTypeName());
                table.addCell(item.getFromUnitName());
                table.addCell(item.getToUnitName());
                table.addCell(item.getOperationDate());
                table.addCell(item.getDocumentName());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Помилка генерації PDF звіту", e);
        }

        byte[] pdfBytes = out.toByteArray();
        MultipartFile file = convertToMultipartFile(pdfBytes, "report.pdf");
        saveFile(file);

    }

    public String saveFile(MultipartFile file) {
        try {
            return S3Util.uploadFile(
                    "acceptance",
                    file.getOriginalFilename(),
                    file.getInputStream(),
                    file.getSize(),
                    file.getContentType()
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    public MultipartFile convertToMultipartFile(byte[] bytes, String fileName) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return fileName;
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return "application/pdf";
            }

            @Override
            public boolean isEmpty() {
                return bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
                throw new UnsupportedOperationException("Not implemented");
            }
        };
    }
}
