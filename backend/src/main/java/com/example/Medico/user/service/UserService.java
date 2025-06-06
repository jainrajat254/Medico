package com.example.Medico.user.service;

import com.example.Medico.common.jwt.JWTService;
import com.example.Medico.user.dto.EditUserDetails;
import com.example.Medico.user.dto.UserDTO;
import com.example.Medico.user.model.UserDetails;
import com.example.Medico.user.model.Users;
import com.example.Medico.user.repository.UserDetailsRepository;
import com.example.Medico.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    JWTService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Users editDetails(EditUserDetails userDTO, UUID id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setAge(userDTO.getAge());
        user.setBloodGroup(userDTO.getBloodGroup());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }

    public UserDTO getDetails(UUID id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDetails userDetails = userDetailsRepository.findByUsers_Id(id)
                .orElseThrow(() -> new RuntimeException("UserDetails not found for user ID: " + id));

        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getGender(),
                userDetails.getHeight(),
                userDetails.getWeight(),
                user.getBloodGroup(),
                user.getPhone(),
                user.getEmail()
        );
    }
}
