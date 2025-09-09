package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonorCampaign} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonorCampaignDTO implements Serializable {

    private Long id;

    private ZonedDateTime subscriptionDate;

    private DonorDTO donor;

    private BloodDonationCampaignDTO bloodDonationCampaign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(ZonedDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonorCampaignDTO)) {
            return false;
        }

        DonorCampaignDTO donorCampaignDTO = (DonorCampaignDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donorCampaignDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonorCampaignDTO{" +
            "id=" + getId() +
            ", subscriptionDate='" + getSubscriptionDate() + "'" +
            ", donor=" + getDonor() +
            ", bloodDonationCampaign=" + getBloodDonationCampaign() +
            "}";
    }
}
