package com.aarmas.globallogic.services;

import com.aarmas.globallogic.DTOs.PhoneDTO;
import com.aarmas.globallogic.DTOs.UserDTO;
import com.aarmas.globallogic.DTOs.UserResponseDTO;
import com.aarmas.globallogic.entities.LoginResponse;
import com.aarmas.globallogic.entities.Phone;
import com.aarmas.globallogic.entities.User;
import com.aarmas.globallogic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JWTService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponseDTO createUser(UserDTO userDTO, List<PhoneDTO> phones) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = addUser(userDTO, phones);
        userRepository.save(user);
        return new UserResponseDTO(user.getId(), user.getCreated(), user.getLastLogin(), user.getToken(), user.isActive());
    }

    public Optional<LoginResponse> login(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                LoginResponse loginResponse = getLoginResponse(user);
                userRepository.save(user);
                return Optional.of(loginResponse);
            }
        }

        return Optional.empty();
    }

    private LoginResponse getLoginResponse(User user) {
        String token = jwtService.generateToken(user);

        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setCreated(user.getCreated());
        response.setLastLogin(user.getLastLogin());
        response.setToken(token);
        response.setActive(user.isActive());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhones(user.getPhones().stream()
                .map(phone -> new PhoneDTO(phone.getPhoneNumber(), phone.getCityCode(), phone.getCountryCode()))
                .collect(Collectors.toList()));
        user.setLastLogin(LocalDateTime.now());
        return response;
    }


    private User addUser(UserDTO userDTO, List<PhoneDTO> phones) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        for (PhoneDTO p : phones) {
            Phone phone = new Phone(p.getPhoneNumber(), p.getCityCode(), p.getCountryCode(), user);
            user.addPhone(phone);
        };

        String token = jwtService.generateToken(user);
        user.setToken(token);
        return user;
    }

}
