package com.example.Medico.user.controller;

import com.example.Medico.user.dto.ExtraDetails;
import com.example.Medico.user.model.UserDetails;
import com.example.Medico.user.responses.UserDetailsResponse;
import com.example.Medico.user.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/personalInfo")
public class UserDetailsController {

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/getPersonalInfo/{id}")
    public UserDetails getPersonalInfo(@PathVariable UUID id) {
        return userDetailsService.getPersonalInfo(id);
    }

    @GetMapping("/getPersonalInfoId/{id}")
    public ExtraDetails getPersonalInfoId(@PathVariable UUID id) {
        return userDetailsService.getPersonalInfoId(id);
    }

    @PutMapping("/extraDetails/{id}")
    public ResponseEntity<UserDetails> updateExtraDetails(@RequestBody UserDetailsResponse response, @PathVariable UUID id) {
        UserDetails updatedUser = userDetailsService.extraDetails(response, id);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/uploadPhoto/{id}")
    public UserDetails uploadPhoto(@RequestParam("file") MultipartFile file, @PathVariable UUID id) throws IOException {
        return userDetailsService.addPhoto(file, id);
    }

    @GetMapping("/getPhoto/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable UUID id) {
        UserDetails userDetails = userDetailsService.getPersonalInfo(id);
        byte[] photo = userDetails.getPhoto();
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(photo);
    }
}
