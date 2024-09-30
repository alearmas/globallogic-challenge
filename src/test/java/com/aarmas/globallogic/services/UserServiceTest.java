package com.aarmas.globallogic.services;

import com.aarmas.globallogic.DTOs.PhoneDTO;
import com.aarmas.globallogic.DTOs.UserDTO;
import com.aarmas.globallogic.DTOs.UserResponseDTO;
import com.aarmas.globallogic.entities.LoginResponse;
import com.aarmas.globallogic.entities.User;
import com.aarmas.globallogic.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_Success() {
        // Arrange
        UserDTO userDTO = createValidUser("test@test.com", "Pedro", "");

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setPhoneNumber(123456789);
        phoneDTO.setCityCode(12);
        phoneDTO.setCountryCode("34");

        List<PhoneDTO> phoneDTOs = List.of(phoneDTO);

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        // Act
        UserResponseDTO response = userService.createUser(userDTO, phoneDTOs);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("existing@test.com");

        User existingUser = new User();
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userDTO, List.of());
        });

        assertEquals("User already exists", thrown.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLogin_Success() {
        // Arrange
        UserDTO userDTO = createValidUser("test@mail.com", "Maria", "");

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setPhoneNumber(123456789);
        phoneDTO.setCityCode(12);
        phoneDTO.setCountryCode("34");

        List<PhoneDTO> phoneDTOs = List.of(phoneDTO);

        User user = new User();
        user.setPassword("encodedPassword");
        user.setId(UUID.randomUUID());

        // Mockeo correcto de dependencias
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userDTO.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // Act
        Optional<LoginResponse> loginResponse = userService.login(userDTO);

        // Assert
        assertTrue(loginResponse.isPresent());
        assertEquals("jwtToken", loginResponse.get().getToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLogin_FailedUserNotFound() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("notfound@test.com");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        // Act
        Optional<LoginResponse> loginResponse = userService.login(userDTO);

        // Assert
        assertFalse(loginResponse.isPresent());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLogin_WrongPassword() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@test.com");
        userDTO.setPassword("wrongPassword");

        User user = new User();
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userDTO.getPassword(), user.getPassword())).thenReturn(false);

        // Act
        Optional<LoginResponse> loginResponse = userService.login(userDTO);

        // Assert
        assertFalse(loginResponse.isPresent());
        verify(userRepository, never()).save(any(User.class));
    }

    private static UserDTO createValidUser(String email, String name, String password) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setName(name);
        userDTO.setPassword(password);
        return userDTO;
    }
}
