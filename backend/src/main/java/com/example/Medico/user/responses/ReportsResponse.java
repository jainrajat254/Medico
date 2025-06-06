package com.example.Medico.user.responses;

import java.time.LocalDate;
import java.util.UUID;

public class ReportsResponse {

    private UUID id;
    private String reportName;
    private String reviewedBy;
    private String attentionLevel;
    private byte[] report;
    private LocalDate date;

    public ReportsResponse(UUID id, String reportName, String reviewedBy, String attentionLevel, LocalDate date) {
        this.id = id;
        this.reportName = reportName;
        this.reviewedBy = reviewedBy;
        this.attentionLevel = attentionLevel;
        this.date = date;
    }

    public ReportsResponse() {

    }

    public ReportsResponse(UUID id, String reportName, String reviewedBy, String attentionLevel, byte[] report) {
        this.id = id;
        this.reportName = reportName;
        this.reviewedBy = reviewedBy;
        this.attentionLevel = attentionLevel;
        this.report = report;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getAttentionLevel() {
        return attentionLevel;
    }

    public void setAttentionLevel(String attentionLevel) {
        this.attentionLevel = attentionLevel;
    }

    public byte[] getReport() {
        return report;
    }

    public void setReport(byte[] report) {
        this.report = report;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
