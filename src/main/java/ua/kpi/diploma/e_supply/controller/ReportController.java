package ua.kpi.diploma.e_supply.controller;


import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kpi.diploma.e_supply.dto.reportDto.ReportRequestDTO;
import ua.kpi.diploma.e_supply.dto.reportDto.ReportResponseDTO;
import ua.kpi.diploma.e_supply.service.PdfReportService;
import ua.kpi.diploma.e_supply.service.ReportService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final PdfReportService pdfReportService;

    @PostMapping
    public ResponseEntity<List<ReportResponseDTO>> getReport(@RequestBody ReportRequestDTO request) {
        List<ReportResponseDTO> report = reportService.getReport(request);
        return ResponseEntity.ok(report);
    }

    @PostMapping("/pdf")
    public ResponseEntity<String> getPdfReport(@RequestBody List<ReportResponseDTO>  request) throws DocumentException, IOException {
        pdfReportService.generatePdfReport(request);
        return  ResponseEntity.ok("Report generated successfully");
    }

}
