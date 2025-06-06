package com.example.Medico.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 25)
    @NotNull(message = "First name cannot be null")
    @Size(max = 25, message = "First name cannot exceed 25 characters")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 25)
    @NotNull(message = "Last name cannot be null")
    @Size(max = 25, message = "Last name cannot exceed 25 characters")
    private String lastName;

    @Column(name = "age", nullable = false, length = 3)
    @NotNull(message = "Age cannot be null")
    @Size(max = 3, message = "Age cannot exceed 3 characters")
    private String age;

    @Column(name = "gender", nullable = false, length = 10)
    @NotNull(message = "Gender cannot be null")
    @Size(max = 10, message = "Gender cannot exceed 10 characters")
    private String gender;

    @Column(name = "blood_group", nullable = false, length = 5)
    @NotNull(message = "Blood group cannot be null")
    @Size(max = 5, message = "Blood group cannot exceed 5 characters")
    private String bloodGroup;

    @Column(name = "phone", nullable = false, length = 15)
    @NotNull(message = "Phone number cannot be null")
    @Size(max = 15, message = "Phone number cannot exceed 15 characters")
    private String phone;

    @Column(name = "role", nullable = false, updatable = false)
    private String role = "USER";

    @Column(name = "email", nullable = false, length = 200, unique = true)
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "password", nullable = false, length = 200)
    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private UserDetails userDetails;

    public Users() {
        this.userDetails = new UserDetails(this); // Auto-create UserDetails with null values
    }

    public Users(UUID id, String firstName, String lastName, String age, String gender, String bloodGroup, String phone, String email, String password, UserDetails userDetails, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.userDetails = userDetails;
        this.role = role;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
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

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getRole() {
        return role;
    }
}
