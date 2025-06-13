package com.example.Medico.doctor.controller;

import com.example.Medico.doctor.dto.DoctorDTO;
import com.example.Medico.doctor.dto.EditDocAddressDetails;
import com.example.Medico.doctor.dto.EditDocMedicalDetails;
import com.example.Medico.doctor.dto.EditDocPersonalDetails;
import com.example.Medico.doctor.model.DoctorDetails;
import com.example.Medico.doctor.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PostMapping("/register")
    public DoctorDetails register(@RequestBody DoctorDetails doctorDetails) {
        return doctorService.register(doctorDetails);
    }

    @PutMapping("/editDocPersonalDetails/{id}")
    public DoctorDetails editDocPersonalDetails(@RequestBody @Valid EditDocPersonalDetails userDTO, @PathVariable UUID id) {
        return doctorService.editDocPersonalDetails(userDTO, id);
    }

    @PutMapping("/editDocAddressDetails/{id}")
    public DoctorDetails editDocAddressDetails(@RequestBody @Valid EditDocAddressDetails docDTO, @PathVariable UUID id) {
        return doctorService.editDocAddressDetails(docDTO, id);
    }

    @PutMapping("/editDocMedicalDetails/{id}")
    public DoctorDetails editDocMedicalDetails(@RequestBody @Valid EditDocMedicalDetails docDTO, @PathVariable UUID id) {
        return doctorService.editDocMedicalDetails(docDTO, id);
    }

    @PutMapping("/uploadPhoto/{id}")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file, @PathVariable UUID id) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty. Please upload a valid image.");
        }
        doctorService.addPhoto(file, id);
        return ResponseEntity.ok("Photo uploaded successfully");
    }

    @GetMapping("/getPhoto/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable UUID id) {
        DoctorDetails doctorDetails = doctorService.getPersonalInfo(id); // Use UID to fetch doctor

        if (doctorDetails == null || doctorDetails.getProfilePicture() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(doctorDetails.getProfilePicture());
    }

    @GetMapping("/getDoctors")
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
}
