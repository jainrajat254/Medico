package com.example.Medico.doctor.service;

import com.example.Medico.doctor.dto.DoctorDTO;
import com.example.Medico.doctor.dto.EditDocAddressDetails;
import com.example.Medico.doctor.dto.EditDocMedicalDetails;
import com.example.Medico.doctor.dto.EditDocPersonalDetails;
import com.example.Medico.common.jwt.JWTService;
import com.example.Medico.doctor.model.DoctorDetails;
import com.example.Medico.doctor.repository.DoctorRepository;
import com.example.Medico.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public DoctorDetails register(DoctorDetails doctorDetails) {
        doctorDetails.setPassword(bCryptPasswordEncoder.encode(doctorDetails.getPassword()));
        return doctorRepository.save(doctorDetails);
    }

    public void addPhoto(MultipartFile file, UUID id) throws IOException {
        DoctorDetails doctorDetails = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with UID: " + id));

        doctorDetails.setProfilePicture(file.getBytes()); // Save the photo as a byte array
        doctorRepository.save(doctorDetails); // Save the doctor with the new photo
    }

    @Transactional(readOnly = true)
    public DoctorDetails getPersonalInfo(UUID id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with UID: " + id));
    }

    public DoctorDetails editDocPersonalDetails(EditDocPersonalDetails userDTO, UUID id) {
        DoctorDetails doctorDetails = doctorRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Doctor not found"));
        doctorDetails.setUid(userDTO.getUid());
        doctorDetails.setDob(userDTO.getDob());
        doctorDetails.setPhone(userDTO.getPhone());
        doctorDetails.setEmail(userDTO.getEmail());
        return doctorRepository.save(doctorDetails);
    }

    public DoctorDetails editDocAddressDetails(EditDocAddressDetails docDTO, UUID id) {
        DoctorDetails doctorDetails = doctorRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Doctor not found"));
        doctorDetails.setWorkspaceName(docDTO.getWorkspaceName());
        doctorDetails.setAddress(docDTO.getAddress());
        doctorDetails.setState(docDTO.getState());
        doctorDetails.setDistrict(docDTO.getDistrict());
        doctorDetails.setZipCode(docDTO.getZipCode());
        return doctorRepository.save(doctorDetails);
    }

    public DoctorDetails editDocMedicalDetails(EditDocMedicalDetails docDTO, UUID id) {
        DoctorDetails doctorDetails = doctorRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Doctor not found"));
        doctorDetails.setMedicalRegNo(docDTO.getMedicalRegNo());
        doctorDetails.setQualification(docDTO.getQualification());
        doctorDetails.setSpecialization(docDTO.getSpecialization());
        doctorDetails.setExperience(docDTO.getExperience());
        doctorDetails.setFee(docDTO.getFee());
        doctorDetails.setAvailableForOnlineConsultation(docDTO.isAvailableForOnlineConsultation());
        return doctorRepository.save(doctorDetails);
    }

    public List<DoctorDTO> getAllDoctors() {
        List<DoctorDetails> doctors = doctorRepository.findAll();
        return doctors.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private DoctorDTO convertToDTO(DoctorDetails doctor) {
        return new DoctorDTO(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getGender(),
                doctor.getSpecialization(),
                doctor.getExperience(),
                doctor.getFee(),
                doctor.getWorkspaceName(),
                doctor.getAddress(),
                doctor.getMedicalRegNo(),
                doctor.getQualification(),
                doctor.getState(),
                doctor.getDistrict(),
                doctor.getZipCode(),
                doctor.getPhone(),
                doctor.getEmail(),
                doctor.getWorkingTime(),
                doctor.isAvailableForOnlineConsultation()
        );
    }
}
