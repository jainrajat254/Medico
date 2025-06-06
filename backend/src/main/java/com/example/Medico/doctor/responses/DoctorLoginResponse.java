package com.example.Medico.doctor.responses;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DoctorLoginResponse {
    private UUID id;
    private String token;
    private String uid;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String gender;
    private String state;
    private String district;
    private String zipCode;
    private String address;
    private String workspaceName;
    private int fee;
    private List<String> workingTime;
    private String medicalRegNo;
    private String specialization;
    private String qualification;
    private int experience;
    private String phone;
    private String email;
    private boolean availableForOnlineConsultation;
    private String password;

    public DoctorLoginResponse() {

    }

    public DoctorLoginResponse(String token, UUID id, String firstName, String lastName, String email) {
        this.token = token;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public DoctorLoginResponse(String token, UUID id, String uid, String firstName, String lastName, LocalDate dob, String gender, String state, String district, String zipCode, String address, String workspaceName, int fee, List<String> workingTime, String medicalRegNo, String specialization, String qualification, int experience, String phone, String email, boolean availableForOnlineConsultation, String password) {
        this.token = token;
        this.id = id;
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.state = state;
        this.district = district;
        this.zipCode = zipCode;
        this.address = address;
        this.workspaceName = workspaceName;
        this.fee = fee;
        this.workingTime = workingTime;
        this.medicalRegNo = medicalRegNo;
        this.specialization = specialization;
        this.qualification = qualification;
        this.experience = experience;
        this.phone = phone;
        this.email = email;
        this.availableForOnlineConsultation = availableForOnlineConsultation;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public List<String> getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(List<String> workingTime) {
        this.workingTime = workingTime;
    }

    public String getMedicalRegNo() {
        return medicalRegNo;
    }

    public void setMedicalRegNo(String medicalRegNo) {
        this.medicalRegNo = medicalRegNo;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
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

    public boolean isAvailableForOnlineConsultation() {
        return availableForOnlineConsultation;
    }

    public void setAvailableForOnlineConsultation(boolean availableForOnlineConsultation) {
        this.availableForOnlineConsultation = availableForOnlineConsultation;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
