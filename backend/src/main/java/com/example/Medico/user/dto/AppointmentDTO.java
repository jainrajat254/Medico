package com.example.Medico.user.dto;

import com.example.Medico.user.model.Appointments;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentDTO {
    private UUID id;
    private UUID userId;
    private String patientName;
    private Integer queueIndex;
    private LocalDate date;
    private String time;
    private LocalDateTime appointmentBookingTime;
    private Appointments.AppointmentStatus appointmentStatus;

    public AppointmentDTO(UUID id, UUID userId, String patientName, Integer queueIndex, LocalDate date, String time, LocalDateTime appointmentBookingTime, Appointments.AppointmentStatus appointmentStatus) {
        this.id = id;
        this.userId = userId;
        this.patientName = patientName;
        this.queueIndex = queueIndex;
        this.date = date;
        this.time = time;
        this.appointmentBookingTime = appointmentBookingTime;
        this.appointmentStatus = appointmentStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getQueueIndex() {
        return queueIndex;
    }

    public void setQueueIndex(Integer queueIndex) {
        this.queueIndex = queueIndex;
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

    public LocalDateTime getAppointmentBookingTime() {
        return appointmentBookingTime;
    }

    public void setAppointmentBookingTime(LocalDateTime appointmentBookingTime) {
        this.appointmentBookingTime = appointmentBookingTime;
    }

    public Appointments.AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(Appointments.AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }
}

