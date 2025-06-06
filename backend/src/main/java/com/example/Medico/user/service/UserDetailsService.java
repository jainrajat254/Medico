package com.example.Medico.user.service;

import com.example.Medico.user.dto.ExtraDetails;
import com.example.Medico.user.model.UserDetails;
import com.example.Medico.user.model.Users;
import com.example.Medico.user.repository.UserDetailsRepository;
import com.example.Medico.user.repository.UserRepository;
import com.example.Medico.user.responses.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    public UserDetails addPersonalInfo(UserDetailsResponse userDetailsResponse, UUID id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        UserDetails userDetails = new UserDetails(
                user,
                userDetailsResponse.getHeight(),
                userDetailsResponse.getWeight(),
                userDetailsResponse.getDob(),
                userDetailsResponse.getEmergencyContactName(),
                userDetailsResponse.getEmergencyContactRelation(),
                userDetailsResponse.getEmergencyContactPhone(),
                userDetailsResponse.getState(),
                userDetailsResponse.getDistrict(),
                userDetailsResponse.getCity(),
                userDetailsResponse.getCurrentAddress(),
                userDetailsResponse.getPermanentAddress(),
                userDetailsResponse.getKnownHealthConditions(),
                userDetailsResponse.getFamilyMedicalHistory(),
                userDetailsResponse.getInsuranceProvider(),
                userDetailsResponse.getPolicyNumber(),
                userDetailsResponse.getGroupNumber(),
                userDetailsResponse.getCoverageDetails()
        );
        return userDetailsRepository.save(userDetails);
    }

    public UserDetails addPhoto(MultipartFile file, UUID id) throws IOException {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = userDetailsRepository.findByUsers(user);
        if (userDetails == null) {
            userDetails = new UserDetails();
            userDetails.setUsers(user);  // Corrected here
        }

        byte[] photoBytes = file.getBytes();
        userDetails.setPhoto(photoBytes);

        return userDetailsRepository.save(userDetails);
    }

    public UserDetails getPersonalInfo(UUID userId) {
        return userDetailsRepository.findByUsers_Id(userId)
                .orElseThrow(() -> new RuntimeException("User details not found"));
    }

    public ExtraDetails getPersonalInfoId(UUID userId) {
        UserDetails userDetails = userDetailsRepository.findByUsers_Id(userId)
                .orElseThrow(() -> new RuntimeException("UserDetails not found for user ID: " + userId));
        return new ExtraDetails(userDetails.getId());
    }


    public UserDetails extraDetails(UserDetailsResponse response, UUID id) {
        UserDetails existingUser = userDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields if they are not null or empty
        Optional.ofNullable(response.getHeight()).filter(h -> !h.isEmpty()).ifPresent(existingUser::setHeight);
        Optional.ofNullable(response.getWeight()).filter(w -> !w.isEmpty()).ifPresent(existingUser::setWeight);
        Optional.ofNullable(response.getDob()).ifPresent(existingUser::setDob);
        Optional.ofNullable(response.getEmergencyContactName()).filter(c -> !c.isEmpty()).ifPresent(existingUser::setEmergencyContactName);
        Optional.ofNullable(response.getEmergencyContactRelation()).filter(r -> !r.isEmpty()).ifPresent(existingUser::setEmergencyContactRelation);
        Optional.ofNullable(response.getEmergencyContactPhone()).filter(p -> !p.isEmpty()).ifPresent(existingUser::setEmergencyContactPhone);
        Optional.ofNullable(response.getState()).filter(s -> !s.isEmpty()).ifPresent(existingUser::setState);
        Optional.ofNullable(response.getDistrict()).filter(d -> !d.isEmpty()).ifPresent(existingUser::setDistrict);
        Optional.ofNullable(response.getCity()).filter(c -> !c.isEmpty()).ifPresent(existingUser::setCity);
        Optional.ofNullable(response.getCurrentAddress()).filter(a -> !a.isEmpty()).ifPresent(existingUser::setCurrentAddress);
        Optional.ofNullable(response.getPermanentAddress()).filter(a -> !a.isEmpty()).ifPresent(existingUser::setPermanentAddress);
        Optional.ofNullable(response.getKnownHealthConditions()).filter(k -> !k.isEmpty()).ifPresent(existingUser::setKnownHealthConditions);
        Optional.ofNullable(response.getFamilyMedicalHistory()).filter(f -> !f.isEmpty()).ifPresent(existingUser::setFamilyMedicalHistory);
        Optional.ofNullable(response.getInsuranceProvider()).filter(i -> !i.isEmpty()).ifPresent(existingUser::setInsuranceProvider);
        Optional.ofNullable(response.getPolicyNumber()).filter(p -> !p.isEmpty()).ifPresent(existingUser::setPolicyNumber);
        Optional.ofNullable(response.getGroupNumber()).filter(g -> !g.isEmpty()).ifPresent(existingUser::setGroupNumber);
        Optional.ofNullable(response.getCoverageDetails()).filter(c -> !c.isEmpty()).ifPresent(existingUser::setCoverageDetails);
//        Optional.ofNullable(response.getPhoto()).ifPresent(existingUser::setPhoto);

        return userDetailsRepository.save(existingUser);
    }
}
