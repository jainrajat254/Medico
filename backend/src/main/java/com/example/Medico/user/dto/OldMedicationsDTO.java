package com.example.Medico.user.dto;

public class OldMedicationsDTO {

    private String medicationName;
    private String startDate;
    private String endDate;
    private String doctorName;

    public OldMedicationsDTO(String medicationName, String startDate, String endDate, String doctorName) {
        this.medicationName = medicationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.doctorName = doctorName;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
