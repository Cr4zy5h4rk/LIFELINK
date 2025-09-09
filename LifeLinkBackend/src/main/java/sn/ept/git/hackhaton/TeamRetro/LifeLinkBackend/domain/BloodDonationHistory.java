package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A BloodDonationHistory.
 */
@Entity
@Table(name = "blood_donation_history")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodDonationHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "volume")
    private Integer volume;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "organizationPartner", "donors", "address", "bloodRequests", "bloodDonationHistories" },
        allowSetters = true
    )
    private DonationCenter donationCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BloodDonationHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public BloodDonationHistory date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getVolume() {
        return this.volume;
    }

    public BloodDonationHistory volume(Integer volume) {
        this.setVolume(volume);
        return this;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Donor getDonor() {
        return this.donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public BloodDonationHistory donor(Donor donor) {
        this.setDonor(donor);
        return this;
    }

    public BloodDonationCampaign getBloodDonationCampaign() {
        return this.bloodDonationCampaign;
    }

    public void setBloodDonationCampaign(BloodDonationCampaign bloodDonationCampaign) {
        this.bloodDonationCampaign = bloodDonationCampaign;
    }

    public BloodDonationHistory bloodDonationCampaign(BloodDonationCampaign bloodDonationCampaign) {
        this.setBloodDonationCampaign(bloodDonationCampaign);
        return this;
    }

    public DonationCenter getDonationCenter() {
        return this.donationCenter;
    }

    public void setDonationCenter(DonationCenter donationCenter) {
        this.donationCenter = donationCenter;
    }

    public BloodDonationHistory donationCenter(DonationCenter donationCenter) {
        this.setDonationCenter(donationCenter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BloodDonationHistory)) {
            return false;
        }
        return getId() != null && getId().equals(((BloodDonationHistory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodDonationHistory{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", volume=" + getVolume() +
            "}";
    }
}
