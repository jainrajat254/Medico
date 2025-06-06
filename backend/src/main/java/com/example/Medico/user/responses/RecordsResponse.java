package com.example.Medico.user.responses;

import java.time.LocalDate;
import java.util.UUID;

public class RecordsResponse {
    private UUID id;
    private String recordName;
    private String reviewedBy;
    private String review;
    private byte[] record;
    private LocalDate date;

    public RecordsResponse() {

    }

    public RecordsResponse(UUID id, String recordName, String reviewedBy, String review, LocalDate date) {
        this.id = id;
        this.recordName = recordName;
        this.reviewedBy = reviewedBy;
        this.review = review;
        this.date = date;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public byte[] getRecord() {
        return record;
    }

    public void setRecord(byte[] record) {
        this.record = record;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
