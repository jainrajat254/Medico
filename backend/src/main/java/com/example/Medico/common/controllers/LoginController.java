package com.example.Medico.common.controllers;

import com.example.Medico.common.dto.EditPassword;
import com.example.Medico.common.model.LoginCredentials;
import com.example.Medico.common.service.LoginService;
import com.example.Medico.doctor.responses.DoctorLoginResponse;
import com.example.Medico.user.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials credentials) {
        Object response = loginService.login(credentials);

        if (response instanceof DoctorLoginResponse || response instanceof UserResponse) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }

    @PutMapping("/editPassword/{id}")
    public ResponseEntity<String> editPassword(@RequestBody EditPassword request, @PathVariable UUID id) {
        try {
            String response = loginService.editPassword(request, id);
            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect current password.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }
}