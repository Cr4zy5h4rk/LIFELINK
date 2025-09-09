package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import jakarta.validation.constraints.NotNull;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodRequestStatus;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodType;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ResusType;

public class BloodRequestCreateDTO {

    public @NotNull BloodRequestStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull BloodRequestStatus status) {
        this.status = status;
    }

    public @NotNull BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(@NotNull BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public @NotNull ResusType getResusType() {
        return resusType;
    }

    public void setResusType(@NotNull ResusType resusType) {
        this.resusType = resusType;
    }

    public String getWolofAudioDetails() {
        return wolofAudioDetails;
    }

    public void setWolofAudioDetails(String wolofAudioDetails) {
        this.wolofAudioDetails = wolofAudioDetails;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public @NotNull Long getDonationCenterId() {
        return donationCenterId;
    }

    public void setDonationCenterId(@NotNull Long donationCenterId) {
        this.donationCenterId = donationCenterId;
    }

    @NotNull
    private BloodRequestStatus status;

    @NotNull
    private BloodType bloodType;

    @NotNull
    private ResusType resusType;

    private String details;

    private String wolofAudioDetails;

    @NotNull
    private Long donationCenterId;
}
