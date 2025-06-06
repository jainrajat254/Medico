package com.example.Medico.user.controller;

import com.example.Medico.user.model.Reports;
import com.example.Medico.user.responses.ReportsResponse;
import com.example.Medico.user.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/reports")
@RestController
public class ReportsController {

    @Autowired
    ReportsService reportsService;

    @PostMapping(value = "/addReport/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Reports addReport(
            @RequestPart("reportName") String reportName,
            @RequestPart("reviewedBy") String reviewedBy,
            @RequestPart("attentionLevel") String attentionLevel,
            @RequestPart("reportFile") MultipartFile reportFile,  // File upload
            @PathVariable UUID id) throws IOException {

        if (reportFile.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        if (!reportFile.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        ReportsResponse reportsResponse = new ReportsResponse();
        reportsResponse.setReportName(reportName);
        reportsResponse.setReviewedBy(reviewedBy);
        reportsResponse.setAttentionLevel(attentionLevel);
        reportsResponse.setReport(reportFile.getBytes());
        return reportsService.addReport(reportsResponse, id);
    }

    @GetMapping("/getReports/{user_id}")
    public List<ReportsResponse> getReports(@PathVariable UUID user_id) {
        return reportsService.getReport(user_id);
    }


    @GetMapping("/getReportFile/{reportId}")
    public ResponseEntity<byte[]> getReportFile(@PathVariable UUID reportId) {
        Reports report = reportsService.getReportFile(reportId);
        if (report == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        byte[] reportData = report.getReport();  // Retrieve file as byte array
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + report.getReportName())  // 'inline' to view in browser
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(reportData);
    }
}
