package com.example.Medico.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "medications")
public class Medications {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Users users;

    @Column(name = "doctor_id", nullable = false, updatable = false)
    private UUID doctorId;

    @Column(name = "doctor_name", nullable = false, updatable = false)
    private String doctorName;

    @Column(name = "medication_name", nullable = false)
    private String medicationName;

    @Column(name = "dosage_type", nullable = false)
    private String dosageType;

    @Column(name = "medication_type", nullable = false)
    private String medicationType;

    @Column(name = "frequency", nullable = false)
    private String frequency;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "intake_method", nullable = false)
    private String intakeMethod;

    @Column(name = "time")
    private String time;

    @CreationTimestamp
    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    public Medications(Users users, UUID doctorId, String doctorName, String medicationName, String dosageType,
                       String medicationType, String frequency, String duration, String intakeMethod, String time) {
        this.users = users;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.medicationName = medicationName;
        this.dosageType = dosageType;
        this.medicationType = medicationType;
        this.frequency = frequency;
        this.duration = duration;
        this.intakeMethod = intakeMethod;
        this.time = time;
        this.startDate = LocalDate.now(); // ✅ Automatically set the current date
    }

    public Medications() {
    }

    // ✅ Getters & Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosageType() {
        return dosageType;
    }

    public void setDosageType(String dosageType) {
        this.dosageType = dosageType;
    }

    public String getMedicationType() {
        return medicationType;
    }

    public void setMedicationType(String medicationType) {
        this.medicationType = medicationType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIntakeMethod() {
        return intakeMethod;
    }

    public void setIntakeMethod(String intakeMethod) {
        this.intakeMethod = intakeMethod;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
