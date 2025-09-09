package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodDonationHistoryDTO implements Serializable {

    private Long id;

    private ZonedDateTime date;

    private Integer volume;

    private DonorDTO donor;

    private BloodDonationCampaignDTO bloodDonationCampaign;

    private DonationCenterDTO donationCenter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public DonorDTO getDonor() {
        return donor;
    }

    public void setDonor(DonorDTO donor) {
        this.donor = donor;
    }

    public BloodDonationCampaignDTO getBloodDonationCampaign() {
        return bloodDonationCampaign;
    }

    public void setBloodDonationCampaign(BloodDonationCampaignDTO bloodDonationCampaign) {
        this.bloodDonationCampaign = bloodDonationCampaign;
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
        if (!(o instanceof BloodDonationHistoryDTO)) {
            return false;
        }

        BloodDonationHistoryDTO bloodDonationHistoryDTO = (BloodDonationHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bloodDonationHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodDonationHistoryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", volume=" + getVolume() +
            ", donor=" + getDonor() +
            ", bloodDonationCampaign=" + getBloodDonationCampaign() +
            ", donationCenter=" + getDonationCenter() +
            "}";
    }
}
