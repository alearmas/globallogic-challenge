package com.aarmas.globallogic.entities;

import com.aarmas.globallogic.DTOs.PhoneDTO;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue
    private UUID id;
    @Email(message = "Email is not valid")
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private boolean isActive;
    private String token;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Phone> phones;

    public User() {
        this.phones = new ArrayList<>();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @Email(message = "Email is not valid") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email is not valid") String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<PhoneDTO> getPhones() {
        List<PhoneDTO> phoneDTOs = new ArrayList<>();
        for (Phone phone : phones) {
            PhoneDTO dto = new PhoneDTO();
            dto.setPhoneNumber(phone.getPhoneNumber());
            dto.setCityCode(phone.getCityCode());
            dto.setCountryCode(phone.getCountryCode());
            phoneDTOs.add(dto);
        }
        return phoneDTOs;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void addPhone(Phone phone) {
        if (this.phones == null) {
            this.phones = new ArrayList<Phone>();
        }
        this.phones.add(phone);
    }
}
