package com.aarmas.globallogic.DTOs;

import java.util.List;

public class UserRequestDTO {
    private UserDTO user;
    private List<PhoneDTO> phones;

    public UserRequestDTO(UserDTO user, List<PhoneDTO> phones) {
        this.user = user;
        this.phones = phones;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }
}
