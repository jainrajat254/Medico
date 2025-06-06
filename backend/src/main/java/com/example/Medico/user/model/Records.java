package com.example.Medico.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "records")
public class Records {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false,updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    @JsonIgnore
    private Users users;

    @Column(name = "record_name",nullable = false,updatable = false)
    private String recordName;

    @Column(name = "reviewed_by",nullable = false,updatable = false)
    private String reviewedBy;

    @CreationTimestamp
    @Column(name = "date", updatable = false)
    private LocalDate date;

    @Lob
    @Column(name = "record",nullable = false,updatable = false)
    private byte[] record;

    @Column(name = "review",nullable = false,updatable = false)
    private String review;

    public Records() {

    }

    public Records(UUID id, Users users, String recordName, String reviewedBy, LocalDate date, byte[] record, String review) {
        this.id = id;
        this.users = users;
        this.recordName = recordName;
        this.reviewedBy = reviewedBy;
        this.date = date;
        this.record = record;
        this.review = review;
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
