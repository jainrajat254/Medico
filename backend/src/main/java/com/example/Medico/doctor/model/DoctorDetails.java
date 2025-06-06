package com.example.Medico.doctor.model;

import jakarta.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "doctor", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid"),
        @UniqueConstraint(columnNames = "phone"),
        @UniqueConstraint(columnNames = "email")})
public class DoctorDetails {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "uid", columnDefinition = "CHAR(12)", nullable = false, unique = true)
    @Size(min = 12, max = 12, message = "UID must be exactly 12 characters long")
    private String uid;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Column(name = "district", nullable = false, length = 50)
    private String district;

    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "workspace_name", nullable = false)
    private String workspaceName;

    @Column(name = "fee", nullable = false)
    @Min(value = 0, message = "Fee must be a positive number")
    private int fee;

    @ElementCollection
    @Column(name = "working_time", nullable = false, length = 100)
    @CollectionTable(name = "doctor_working_hours", joinColumns = @JoinColumn(name = "doctor_id"))
    private List<String> workingTime;

    @Column(name = "medical_reg_no", nullable = false, unique = true, length = 50)
    private String medicalRegNo;

    @Column(name = "specialization", nullable = false, length = 100)
    private String specialization;

    @Column(name = "qualification", nullable = false, length = 100)
    private String qualification;

    @Column(name = "experience", nullable = false)
    @Min(value = 0, message = "Experience must be a positive number")
    private int experience;

    @Column(name = "phone", nullable = false, unique = true, length = 12)
    private String phone;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "available_for_online_consultation", nullable = false)
    private boolean availableForOnlineConsultation;

    @Column(name = "role", nullable = false, updatable = false, insertable = false)
    private final String role = "DOCTOR";

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    public DoctorDetails() {

    }

    public DoctorDetails(UUID id, String uid, String firstName, String lastName, LocalDate dob, String gender, String state, String district, String zipCode, String address, String workspaceName, int fee, List<String> workingTime, String medicalRegNo, String specialization, String qualification, int experience, String phone, String email, String password, boolean availableForOnlineConsultation) {
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
        this.password = password;
        this.availableForOnlineConsultation = availableForOnlineConsultation;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAvailableForOnlineConsultation() {
        return availableForOnlineConsultation;
    }

    public void setAvailableForOnlineConsultation(boolean availableForOnlineConsultation) {
        this.availableForOnlineConsultation = availableForOnlineConsultation;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}
