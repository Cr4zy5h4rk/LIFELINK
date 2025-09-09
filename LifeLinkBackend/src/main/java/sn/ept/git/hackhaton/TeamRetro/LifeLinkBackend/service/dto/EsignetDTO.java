package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;


import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.Gender;

import java.time.ZonedDateTime;

public class EsignetDTO {
    private String email;
    private String name;
    private String phone_number;
    private String birthdate;
    private String gender;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
