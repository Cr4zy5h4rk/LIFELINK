package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodRequestStatus;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodType;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ResusType;

/**
 * A DTO for the {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodRequest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodRequestDTO implements Serializable {

    private Long id;

    @JsonProperty("date")
    private ZonedDateTime createdAt;
    @JsonProperty("statut")
    private BloodRequestStatus status;

    private BloodType bloodType;

    @JsonIgnore
    private ResusType resusType;

    private String details;

    private String wolofAudioDetails;

    @JsonProperty("hospital")
    private DonationCenterDTO donationCenter;

    @JsonProperty("title")
    public String getTitle() {
        return "Urgence, Besoin de sang " + getFullBloodType();
    }
    
    @JsonProperty("bloodType")
    public String getFullBloodType() {
        return bloodType.name() + (resusType == ResusType.POSITIVE ? "+" : "-");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BloodRequestStatus getStatus() {
        return status;
    }

    public void setStatus(BloodRequestStatus status) {
        this.status = status;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getWolofAudioDetails() {
        return wolofAudioDetails;
    }

    public void setWolofAudioDetails(String wolofAudioDetails) {
        this.wolofAudioDetails = wolofAudioDetails;
    }

    public DonationCenterDTO getDonationCenter() {
        return donationCenter;
    }

    public void setDonationCenter(DonationCenterDTO donationCenter) {
        this.donationCenter = donationCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BloodRequestDTO)) {
            return false;
        }

        BloodRequestDTO bloodRequestDTO = (BloodRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bloodRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodRequestDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", bloodType='" + getBloodType() + "'" +
            ", resusType='" + getResusType() + "'" +
            ", details='" + getDetails() + "'" +
            ", wolofAudioDetails='" + getWolofAudioDetails() + "'" +
            ", donationCenter=" + getDonationCenter() +
            "}";
    }
}
