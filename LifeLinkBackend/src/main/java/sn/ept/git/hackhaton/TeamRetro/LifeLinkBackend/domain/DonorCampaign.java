package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A DonorCampaign.
 */
@Entity
@Table(name = "donor_campaign")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonorCampaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "subscription_date")
    private ZonedDateTime subscriptionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "address",
            "favorites",
            "roles",
            "medicalStaff",
            "createdBloodRequests",
            "canceledBloodRequests",
            "employees",
            "bloodDonationHistories",
            "createdBies",
            "donorCampaigns",
            "userNotifications",
        },
        allowSetters = true
    )
    private Donor donor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bloodDonationHistories", "donorCampaigns" }, allowSetters = true)
    private BloodDonationCampaign bloodDonationCampaign;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DonorCampaign id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getSubscriptionDate() {
        return this.subscriptionDate;
    }

    public DonorCampaign subscriptionDate(ZonedDateTime subscriptionDate) {
        this.setSubscriptionDate(subscriptionDate);
        return this;
    }

    public void setSubscriptionDate(ZonedDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Donor getDonor() {
        return this.donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public DonorCampaign donor(Donor donor) {
        this.setDonor(donor);
        return this;
    }

    public BloodDonationCampaign getBloodDonationCampaign() {
        return this.bloodDonationCampaign;
    }

    public void setBloodDonationCampaign(BloodDonationCampaign bloodDonationCampaign) {
        this.bloodDonationCampaign = bloodDonationCampaign;
    }

    public DonorCampaign bloodDonationCampaign(BloodDonationCampaign bloodDonationCampaign) {
        this.setBloodDonationCampaign(bloodDonationCampaign);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonorCampaign)) {
            return false;
        }
        return getId() != null && getId().equals(((DonorCampaign) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonorCampaign{" +
            "id=" + getId() +
            ", subscriptionDate='" + getSubscriptionDate() + "'" +
            "}";
    }
}
