package com.example.Medico.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reports")
public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Users users;

    @Column(name = "report_name",nullable = false)
    private String reportName;

    @Column(name = "reviewed_by",nullable = false)
    private String reviewedBy;

    @Column(name = "attention_level",nullable = false)
    private String attentionLevel;

    @CreationTimestamp
    @Column(name = "date", updatable = false)
    private LocalDate date;

    @Lob
    @Column(name = "report",nullable = false,updatable = false)
    private byte[] report;

    public Reports() {

    }

    public Reports(UUID id, Users users, String reportName, String reviewedBy, String attentionLevel, byte[] report) {
        this.id = id;
        this.users = users;
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
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
