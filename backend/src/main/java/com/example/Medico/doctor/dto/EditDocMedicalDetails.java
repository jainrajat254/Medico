package com.example.Medico.doctor.dto;

public class EditDocMedicalDetails {
    private String medicalRegNo;
    private String qualification;
    private String specialization;
    private int experience;
    private int fee;
    private boolean availableForOnlineConsultation;

    public String getMedicalRegNo() {
        return medicalRegNo;
    }

    public void setMedicalRegNo(String medicalRegNo) {
        this.medicalRegNo = medicalRegNo;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public boolean isAvailableForOnlineConsultation() {
        return availableForOnlineConsultation;
    }

    public void setAvailableForOnlineConsultation(boolean availableForOnlineConsultation) {
        this.availableForOnlineConsultation = availableForOnlineConsultation;
    }
}
