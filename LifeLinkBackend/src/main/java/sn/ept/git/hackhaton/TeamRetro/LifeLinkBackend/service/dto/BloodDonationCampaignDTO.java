package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.CampaignStatus;

/**
 * A DTO for the {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationCampaign} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodDonationCampaignDTO implements Serializable {

    private Long id;

    private CampaignStatus status;

    private ZonedDateTime createdAt;

    private String contact;

    private String campaignDetails;

    private String image;

    @Lob
    private String wolofAudioDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCampaignDetails() {
        return campaignDetails;
    }

    public void setCampaignDetails(String campaignDetails) {
        this.campaignDetails = campaignDetails;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWolofAudioDescription() {
        return wolofAudioDescription;
    }

    public void setWolofAudioDescription(String wolofAudioDescription) {
        this.wolofAudioDescription = wolofAudioDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BloodDonationCampaignDTO)) {
            return false;
        }

        BloodDonationCampaignDTO bloodDonationCampaignDTO = (BloodDonationCampaignDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bloodDonationCampaignDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodDonationCampaignDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", contact='" + getContact() + "'" +
            ", campaignDetails='" + getCampaignDetails() + "'" +
            ", image='" + getImage() + "'" +
            ", wolofAudioDescription='" + getWolofAudioDescription() + "'" +
            "}";
    }
}
