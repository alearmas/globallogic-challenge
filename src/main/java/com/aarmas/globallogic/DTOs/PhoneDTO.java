package com.aarmas.globallogic.DTOs;

import javax.validation.constraints.NotEmpty;

public class PhoneDTO {

    @NotEmpty(message = "Phone number is required")
    private long phoneNumber;
    private int cityCode;
    private String countryCode;

    public PhoneDTO() {
    }

    public PhoneDTO(long phoneNumber, int cityCode, String countryCode) {
        this.phoneNumber = phoneNumber;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
    }

    @NotEmpty(message = "Phone number is required")
    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotEmpty(message = "Phone number is required") long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
