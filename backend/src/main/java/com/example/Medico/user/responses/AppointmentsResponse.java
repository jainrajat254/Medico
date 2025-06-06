package com.example.Medico.user.responses;

import com.example.Medico.user.model.Users;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentsResponse {
    private Users users;
    private UUID doctorId;
    private String doctorName;
    private String patientName;
    private String date;
    private String time;
    private String specialization;
    private String workspaceName;
    private LocalDateTime appointmentBookingTime;

    public AppointmentsResponse(Users users, UUID doctorId, String doctorName, String patientName, String date, String time, String specialization, String workspaceName) {
        this.users = users;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.date = date;
        this.time = time;
        this.specialization = specialization;
        this.workspaceName = workspaceName;
    }

    public AppointmentsResponse() {

    }

    public AppointmentsResponse(Users users,String patientName, String date, String time, LocalDateTime appointmentBookingTime) {
        this.users = users;
        this.patientName = patientName;
        this.date = date;
        this.time = time;
        this.appointmentBookingTime = appointmentBookingTime;
    }

    public Users getUserId() {
        return users;
    }

    public void setUserId(Users users) {
        this.users = users;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public void setAppointmentBookingTime(LocalDateTime appointmentBookingTime) {
        this.appointmentBookingTime = appointmentBookingTime;
    }
}
