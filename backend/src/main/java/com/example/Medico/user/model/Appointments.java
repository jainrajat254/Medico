package com.example.Medico.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private Users users;

    @Column(name = "doctor_id",nullable = false)
    private UUID doctorId;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(name = "doctor_name", nullable = false)
    private String doctorName;

    private Integer queueIndex;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "workspace_name", nullable = false)
    private String workspaceName;

    @CreationTimestamp
    @Column(name = "appointment_booking_time", updatable = false)
    private LocalDateTime appointmentBookingTime;

    public enum AppointmentStatus {
        BOOKED,
        COMPLETED,
        ABSENT
    }

    public Appointments() {

    }

    public Appointments(Users users, UUID doctorId, String doctorName, String patientName,
                        LocalDate date, String time, String specialization, String workspaceName) {
        this.users = users;
        this.doctorId = doctorId;
        this.status = AppointmentStatus.BOOKED; // Default to BOOKED
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.date = date;
        this.time = time;
        this.specialization = specialization;
        this.workspaceName = workspaceName;
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

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getQueueIndex() {
        return queueIndex;
    }

    public void setQueueIndex(Integer queueIndex) {
        this.queueIndex = queueIndex;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public LocalDateTime getAppointmentBookingTime() {
        return appointmentBookingTime;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setAppointmentBookingTime(LocalDateTime appointmentBookingTime) {
        this.appointmentBookingTime = appointmentBookingTime;
    }
}
