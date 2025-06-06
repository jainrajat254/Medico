package com.example.Medico.user.controller;

import com.example.Medico.user.dto.EditUserDetails;
import com.example.Medico.user.dto.UserDTO;
import com.example.Medico.user.model.Users;
import com.example.Medico.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/u")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("editDetails/{id}")
    public Users editDetails(@RequestBody @Valid EditUserDetails userDTO, @PathVariable UUID id) {
        return userService.editDetails(userDTO, id);
    }

    @GetMapping("getDetails/{id}")
    public UserDTO getDetails(@PathVariable UUID id) {
        return userService.getDetails(id);
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.register(user);
    }

}
