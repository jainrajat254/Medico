package com.example.Medico.user.dto;

import javax.validation.constraints.*;

public class EditUserDetails {

    @Min(1)
    @Max(150)
    private String age;

    @NotBlank(message = "Blood group is required")
    private String bloodGroup;

    @Size(min = 10, max = 10)
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    // Getters and Setters
    public String getAge() { return age; }
    public void setAge(String  age) { this.age = age; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

