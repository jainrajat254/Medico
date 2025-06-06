package com.example.Medico.doctor.dto;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class EditDocPersonalDetails {

    @Min(12)
    @Max(12)
    private String uid;

    @NotBlank(message = "DOB is required")
    private LocalDate dob;

    @Size(min = 10, max = 10)
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
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
}

