package com.aarmas.globallogic.DTOs;

import javax.validation.constraints.*;

public class UserDTO {

    @Email(message = "Email format is invalid")
    @NotEmpty(message = "Email is required")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=(.*\\d){2})(?!.*\\d{3,})(?=.*[a-z]).{8,12}$",
            message = "Password must have 1 uppercase, 2 digits, and 8-12 characters")
    @NotEmpty(message = "Password is required")
    private String password;
    private String name;

    public UserDTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserDTO() {

    }

    public @Email(message = "Email format is invalid") @NotEmpty(message = "Email is required") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email format is invalid") @NotEmpty(message = "Email is required") String email) {
        this.email = email;
    }

    public @Pattern(regexp = "^(?=.*[A-Z])(?=(.*\\d){2})(?!.*\\d{3,})(?=.*[a-z]).{8,12}$",
            message = "Password must have 1 uppercase, 2 digits, and 8-12 characters") @NotEmpty(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@Pattern(regexp = "^(?=.*[A-Z])(?=(.*\\d){2})(?!.*\\d{3,})(?=.*[a-z]).{8,12}$",
            message = "Password must have 1 uppercase, 2 digits, and 8-12 characters") @NotEmpty(message = "Password is required") String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
