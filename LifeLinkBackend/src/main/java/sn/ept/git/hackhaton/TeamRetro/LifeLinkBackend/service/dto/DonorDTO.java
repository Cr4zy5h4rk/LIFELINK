package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodType;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.Gender;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ResusType;

/**
 * A DTO for the {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonorDTO implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Gender gender;

    private ZonedDateTime birthDate;

    private String email;

    private String password;

    private Double weight;

    private Double size;

    private Integer fidelityPoints;

    private BloodType bloodType;

    private ResusType resusType;

    private Boolean receiveOtpForBloodRequest;

    private ZonedDateTime lastDonationDate;

    private AddressDTO address;

    private Set<ArticleDTO> favorites = new HashSet<>();

    private Set<RoleDTO> roles = new HashSet<>();

    private DonationCenterDTO medicalStaff;

    private BloodRequestDTO createdBloodRequests;

    private BloodRequestDTO canceledBloodRequests;

    private String medicalData;

    private String Picture;

    private OrganizationPartnerDTO employees;

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMedicalData() {
        return medicalData;
    }

    public void setMedicalData(String medicalData) {
        this.medicalData = medicalData;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ZonedDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFidelityPoints(Integer fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public ResusType getResusType() {
        return resusType;
    }

    public void setResusType(ResusType resusType) {
        this.resusType = resusType;
    }

    public Boolean getReceiveOtpForBloodRequest() {
        return receiveOtpForBloodRequest;
    }

    public void setReceiveOtpForBloodRequest(Boolean receiveOtpForBloodRequest) {
        this.receiveOtpForBloodRequest = receiveOtpForBloodRequest;
    }

    public ZonedDateTime getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(ZonedDateTime lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Set<ArticleDTO> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<ArticleDTO> favorites) {
        this.favorites = favorites;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public DonationCenterDTO getMedicalStaff() {
        return medicalStaff;
    }

    public void setMedicalStaff(DonationCenterDTO medicalStaff) {
        this.medicalStaff = medicalStaff;
    }

    public BloodRequestDTO getCreatedBloodRequests() {
        return createdBloodRequests;
    }

    public void setCreatedBloodRequests(BloodRequestDTO createdBloodRequests) {
        this.createdBloodRequests = createdBloodRequests;
    }

    public BloodRequestDTO getCanceledBloodRequests() {
        return canceledBloodRequests;
    }

    public void setCanceledBloodRequests(BloodRequestDTO canceledBloodRequests) {
        this.canceledBloodRequests = canceledBloodRequests;
    }

    public OrganizationPartnerDTO getEmployees() {
        return employees;
    }

    public void setEmployees(OrganizationPartnerDTO employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonorDTO)) {
            return false;
        }

        DonorDTO donorDTO = (DonorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonorDTO{" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", fidelityPoints=" + getFidelityPoints() +
            ", bloodType='" + getBloodType() + "'" +
            ", resusType='" + getResusType() + "'" +
            ", receiveOtpForBloodRequest='" + getReceiveOtpForBloodRequest() + "'" +
            ", lastDonationDate='" + getLastDonationDate() + "'" +
            ", address=" + getAddress() +
            ", favorites=" + getFavorites() +
            ", roles=" + getRoles() +
            ", medicalStaff=" + getMedicalStaff() +
            ", createdBloodRequests=" + getCreatedBloodRequests() +
            ", canceledBloodRequests=" + getCanceledBloodRequests() +
            ", employees=" + getEmployees() +
            "}";
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
}
