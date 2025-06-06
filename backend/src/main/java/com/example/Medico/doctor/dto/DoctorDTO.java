package com.example.Medico.doctor.dto;

import java.util.List;
import java.util.UUID;

public class DoctorDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String gender;
    private String specialization;
    private int experience;
    private int fee;
    private String workspaceName;
    private String address;
    private String medicalRegNo;
    private String qualification;
    private String state;
    private String district;
    private String zipCode;
    private String phone;
    private String email;
    private List<String> workingTime;
    private boolean availableForOnlineConsultation;

    public DoctorDTO(UUID id, String firstName, String lastName, String gender, String specialization, int experience, int fee, String workspaceName, String address, String medicalRegNo, String qualification, String state, String district, String zipCode, String phone, String email, List<String> workingTime, boolean availableForOnlineConsultation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.specialization = specialization;
        this.experience = experience;
        this.fee = fee;
        this.workspaceName = workspaceName;
        this.address = address;
        this.medicalRegNo = medicalRegNo;
        this.qualification = qualification;
        this.state = state;
        this.district = district;
        this.zipCode = zipCode;
        this.phone = phone;
        this.email = email;
        this.workingTime = workingTime;
        this.availableForOnlineConsultation = availableForOnlineConsultation;
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(List<String> workingTime) {
        this.workingTime = workingTime;
    }

    public boolean isAvailableForOnlineConsultation() {
        return availableForOnlineConsultation;
    }

    public void setAvailableForOnlineConsultation(boolean availableForOnlineConsultation) {
        this.availableForOnlineConsultation = availableForOnlineConsultation;
    }
}
