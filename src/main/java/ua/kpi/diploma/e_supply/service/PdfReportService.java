package ua.kpi.diploma.e_supply.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.diploma.e_supply.dto.reportDto.ReportResponseDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfReportService {
    private final S3Util s3Util;

    public void generatePdfReport(List<ReportResponseDTO> reportData)
            throws DocumentException, IOException {

        // 1. Зареєструвати шрифт з classpath (без "src/main/resources")
        String fontPath = PdfReportService.class
                .getClassLoader()
                .getResource("static/fonts/Bebas_Neue_Cyrillic.ttf")
                .getPath();
        FontFactory.register(fontPath, "MyFont");

        // 2. Створити три варіанти Font з нашим шрифтом
        Font titleFont  = FontFactory.getFont(
                "MyFont", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 16, Font.BOLD);
        Font headerFont = FontFactory.getFont(
                "MyFont", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.BOLD);
        Font rowFont    = FontFactory.getFont(
                "MyFont", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 11, Font.NORMAL);

        // 3. Підготовка документа (A4, ландшафт)
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 54, 36);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        // 4. Заголовок із кастомним шрифтом
        Paragraph title = new Paragraph("Звіт про переміщення одиниць", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // 5. Таблиця на 7 колонок
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{3, 3, 3, 4, 4, 2, 4});

        // 6. Заголовки колонок із кастомним шрифтом
        String[] headers = {
                "Назва одиниці",
                "Інд. номер",
                "Тип операції",
                "Звідки",
                "Куди",
                "Дата операції",
                "Документ"
        };
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(5);
            table.addCell(cell);
        }

        // 7. Рядки з reportData, теж із кастомним шрифтом
        for (ReportResponseDTO dto : reportData) {
            table.addCell(new PdfPCell(new Phrase(dto.getItemName(), rowFont)));
            table.addCell(new PdfPCell(new Phrase(dto.getIndividualNumber(), rowFont)));
            table.addCell(new PdfPCell(new Phrase(dto.getOperationTypeName(), rowFont)));
            table.addCell(new PdfPCell(new Phrase(dto.getFromUnitName(), rowFont)));
            table.addCell(new PdfPCell(new Phrase(dto.getToUnitName(), rowFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(dto.getOperationDate()), rowFont)));
            // клікабельне посилання
            Chunk linkChunk = new Chunk(dto.getDocumentLink(), rowFont);
            linkChunk.setAnchor(dto.getDocumentLink());
            PdfPCell linkCell = new PdfPCell(new Phrase(linkChunk));
            linkCell.setPadding(5);
            table.addCell(linkCell);
        }

        document.add(table);
        document.close();

        // 8. Збереження у ваш спосіб
        String fileName = "report_" + LocalDate.now() + ".pdf";
        byte[] pdfBytes = baos.toByteArray();
        MultipartFile file = convertToMultipartFile(pdfBytes, fileName);
        saveFile(file);
    }


    private MultipartFile convertToMultipartFile(byte[] bytes, String fileName) {
        return new MultipartFile() {
            @Override public String getName() { return fileName; }
            @Override public String getOriginalFilename() { return fileName; }
            @Override public String getContentType() { return "application/pdf"; }
            @Override public boolean isEmpty() { return bytes.length == 0; }
            @Override public long getSize() { return bytes.length; }
            @Override public byte[] getBytes() throws IOException { return bytes; }
            @Override public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }
            @Override public void transferTo(java.io.File dest) {
                throw new UnsupportedOperationException("Not implemented");
            }
        };
    }

    public String saveFile(MultipartFile file) {
        try {
            return s3Util.uploadFile(
                    "reports",
                    file.getOriginalFilename(),
                    file.getInputStream(),
                    file.getSize(),
                    file.getContentType()
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }
}
