package com.example.Medico.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "personal_info")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private String weight;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emergency_contact_relation")
    private String emergencyContactRelation;

    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;

    @Column(name = "state")
    private String state;

    @Column(name = "district")
    private String district;

    @Column(name = "city")
    private String city;

    @Column(name = "current_address")
    private String currentAddress;

    @Column(name = "permanent_address")
    private String permanentAddress;

    @Column(name = "known_health_conditions")
    private String knownHealthConditions;

    @Column(name = "family_medical_history")
    private String familyMedicalHistory;

    @Column(name = "insurance_provider")
    private String insuranceProvider;

    @Column(name = "policy_number")
    private String policyNumber;

    @Column(name = "group_number")
    private String groupNumber;

    @Column(name = "coverage_details")
    private String coverageDetails;

    @Column(name = "photo")
    private byte[] photo;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private Users users;

    public UserDetails() {
    }

    public UserDetails(Users users) {
        this.users = users;
    }

    public UserDetails(Users users, String height, String weight, LocalDate dob, String emergencyContactName, String emergencyContactRelation, String emergencyContactPhone, String state, String district, String city, String currentAddress, String permanentAddress, String knownHealthConditions, String familyMedicalHistory, String insuranceProvider, String policyNumber, String groupNumber, String coverageDetails) {
        this.users = users;
        this.height = height;
        this.weight = weight;
        this.dob = dob;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactRelation = emergencyContactRelation;
        this.emergencyContactPhone = emergencyContactPhone;
        this.state = state;
        this.district = district;
        this.city = city;
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
        this.knownHealthConditions = knownHealthConditions;
        this.familyMedicalHistory = familyMedicalHistory;
        this.insuranceProvider = insuranceProvider;
        this.policyNumber = policyNumber;
        this.groupNumber = groupNumber;
        this.coverageDetails = coverageDetails;
    }

    public UserDetails(UUID id, Users users, String height, String weight, LocalDate dob, String emergencyContactName, String emergencyContactRelation, String emergencyContactPhone, String state, String district, String city, String currentAddress, String permanentAddress, String knownHealthConditions, String familyMedicalHistory, String insuranceProvider, String policyNumber, String groupNumber, String coverageDetails) {
        this.id = id;
        this.users = users;
        this.height = height;
        this.weight = weight;
        this.dob = dob;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactRelation = emergencyContactRelation;
        this.emergencyContactPhone = emergencyContactPhone;
        this.state = state;
        this.district = district;
        this.city = city;
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
        this.knownHealthConditions = knownHealthConditions;
        this.familyMedicalHistory = familyMedicalHistory;
        this.insuranceProvider = insuranceProvider;
        this.policyNumber = policyNumber;
        this.groupNumber = groupNumber;
        this.coverageDetails = coverageDetails;
//        this.photo = photo;
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactRelation() {
        return emergencyContactRelation;
    }

    public void setEmergencyContactRelation(String emergencyContactRelation) {
        this.emergencyContactRelation = emergencyContactRelation;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getKnownHealthConditions() {
        return knownHealthConditions;
    }

    public void setKnownHealthConditions(String knownHealthConditions) {
        this.knownHealthConditions = knownHealthConditions;
    }

    public String getFamilyMedicalHistory() {
        return familyMedicalHistory;
    }

    public void setFamilyMedicalHistory(String familyMedicalHistory) {
        this.familyMedicalHistory = familyMedicalHistory;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getCoverageDetails() {
        return coverageDetails;
    }

    public void setCoverageDetails(String coverageDetails) {
        this.coverageDetails = coverageDetails;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

}
