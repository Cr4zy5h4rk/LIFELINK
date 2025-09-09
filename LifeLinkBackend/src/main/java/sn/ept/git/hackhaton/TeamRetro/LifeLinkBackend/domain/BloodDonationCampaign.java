package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.CampaignStatus;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A BloodDonationCampaign.
 */
@Entity
@Table(name = "blood_donation_campaign")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodDonationCampaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CampaignStatus status;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;


    @Column(name = "start_at", nullable = false)
    private ZonedDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private ZonedDateTime endAt;

    @Column(name = "contact")
    private String contact;

    @Column(name = "campaign_details")
    private String campaignDetails;

    @Column(name = "image")
    private String image;

    @Column(name = "wolof_audio_description", length = 512)
    private String wolofAudioDescription;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bloodDonationCampaign")
    @JsonIgnoreProperties(value = { "donor", "bloodDonationCampaign", "donationCenter" }, allowSetters = true)
    private Set<BloodDonationHistory> bloodDonationHistories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bloodDonationCampaign")
    @JsonIgnoreProperties(value = { "donor", "bloodDonationCampaign" }, allowSetters = true)
    private Set<DonorCampaign> donorCampaigns = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Address address;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BloodDonationCampaign id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CampaignStatus getStatus() {
        return this.status;
    }

    public BloodDonationCampaign status(CampaignStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public BloodDonationCampaign createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(ZonedDateTime endAt) {
        this.endAt = endAt;
    }

    public ZonedDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(ZonedDateTime startAt) {
        this.startAt = startAt;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getContact() {
        return this.contact;
    }

    public BloodDonationCampaign contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCampaignDetails() {
        return this.campaignDetails;
    }

    public BloodDonationCampaign campaignDetails(String campaignDetails) {
        this.setCampaignDetails(campaignDetails);
        return this;
    }

    public void setCampaignDetails(String campaignDetails) {
        this.campaignDetails = campaignDetails;
    }

    public String getImage() {
        return this.image;
    }

    public BloodDonationCampaign image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWolofAudioDescription() {
        return this.wolofAudioDescription;
    }

    public BloodDonationCampaign wolofAudioDescription(String wolofAudioDescription) {
        this.setWolofAudioDescription(wolofAudioDescription);
        return this;
    }

    public void setWolofAudioDescription(String wolofAudioDescription) {
        this.wolofAudioDescription = wolofAudioDescription;
    }

    public Set<BloodDonationHistory> getBloodDonationHistories() {
        return this.bloodDonationHistories;
    }

    public void setBloodDonationHistories(Set<BloodDonationHistory> bloodDonationHistories) {
        if (this.bloodDonationHistories != null) {
            this.bloodDonationHistories.forEach(i -> i.setBloodDonationCampaign(null));
        }
        if (bloodDonationHistories != null) {
            bloodDonationHistories.forEach(i -> i.setBloodDonationCampaign(this));
        }
        this.bloodDonationHistories = bloodDonationHistories;
    }

    public BloodDonationCampaign bloodDonationHistories(Set<BloodDonationHistory> bloodDonationHistories) {
        this.setBloodDonationHistories(bloodDonationHistories);
        return this;
    }

    public BloodDonationCampaign addBloodDonationHistory(BloodDonationHistory bloodDonationHistory) {
        this.bloodDonationHistories.add(bloodDonationHistory);
        bloodDonationHistory.setBloodDonationCampaign(this);
        return this;
    }

    public BloodDonationCampaign removeBloodDonationHistory(BloodDonationHistory bloodDonationHistory) {
        this.bloodDonationHistories.remove(bloodDonationHistory);
        bloodDonationHistory.setBloodDonationCampaign(null);
        return this;
    }

    public Set<DonorCampaign> getDonorCampaigns() {
        return this.donorCampaigns;
    }

    public void setDonorCampaigns(Set<DonorCampaign> donorCampaigns) {
        if (this.donorCampaigns != null) {
            this.donorCampaigns.forEach(i -> i.setBloodDonationCampaign(null));
        }
        if (donorCampaigns != null) {
            donorCampaigns.forEach(i -> i.setBloodDonationCampaign(this));
        }
        this.donorCampaigns = donorCampaigns;
    }

    public BloodDonationCampaign donorCampaigns(Set<DonorCampaign> donorCampaigns) {
        this.setDonorCampaigns(donorCampaigns);
        return this;
    }

    public BloodDonationCampaign addDonorCampaign(DonorCampaign donorCampaign) {
        this.donorCampaigns.add(donorCampaign);
        donorCampaign.setBloodDonationCampaign(this);
        return this;
    }

    public BloodDonationCampaign removeDonorCampaign(DonorCampaign donorCampaign) {
        this.donorCampaigns.remove(donorCampaign);
        donorCampaign.setBloodDonationCampaign(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BloodDonationCampaign)) {
            return false;
        }
        return getId() != null && getId().equals(((BloodDonationCampaign) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodDonationCampaign{" +
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
