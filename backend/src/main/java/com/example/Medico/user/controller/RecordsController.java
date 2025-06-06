package com.example.Medico.user.controller;

import com.example.Medico.user.model.Records;
import com.example.Medico.user.responses.RecordsResponse;
import com.example.Medico.user.service.RecordsService;
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

@RequestMapping("/records")
@RestController
public class RecordsController {

    @Autowired
    RecordsService recordsService;

    @PostMapping(value = "/addRecord/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Records addRecord(
            @RequestPart("recordName") String recordName,
            @RequestPart("reviewedBy") String reviewedBy,
            @RequestPart("review") String review,
            @RequestPart("recordFile") MultipartFile recordFile,
            @PathVariable UUID id) throws IOException {

        if (recordFile.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        if (!recordFile.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        RecordsResponse recordsResponse = new RecordsResponse();
        recordsResponse.setRecordName(recordName);
        recordsResponse.setReviewedBy(reviewedBy);
        recordsResponse.setReview(review);
        recordsResponse.setRecord(recordFile.getBytes());
        return recordsService.addRecords(recordsResponse, id);
    }

    @GetMapping("/getRecord/{user_id}")
    public List<RecordsResponse> getRecord(@PathVariable UUID user_id) {
        return recordsService.getRecord(user_id);
    }

    @GetMapping("/getRecordFile/{recordId}")
    public ResponseEntity<byte[]> getReportFile(@PathVariable UUID recordId) {
        Records record = recordsService.getRecordFile(recordId);
        if (record == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        byte[] recordData = record.getRecord();  // Retrieve file as byte array
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + record.getRecordName())  // 'inline' to view in browser
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(recordData);
    }
}
