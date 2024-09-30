package com.aarmas.globallogic.controllers;

import com.aarmas.globallogic.DTOs.*;
import com.aarmas.globallogic.entities.ErrorResponse;
import com.aarmas.globallogic.entities.LoginResponse;
import com.aarmas.globallogic.exceptions.UserNotFoundException;
import com.aarmas.globallogic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        // Asumimos que el servicio puede lanzar UserNotFoundException
        UserResponseDTO userResponse = userService.createUser(userRequestDTO.getUser(), userRequestDTO.getPhones());
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO userDTO) {
        Optional<LoginResponse> response = userService.login(userDTO);

        // Si no se encuentra el usuario, lanzamos UserNotFoundException
        return response.map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}