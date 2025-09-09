package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodRequestStatus;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodType;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ResusType;

/**
 * A BloodRequest.
 */
@Entity
@Table(name = "blood_request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BloodRequestStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    private BloodType bloodType;

    @Enumerated(EnumType.STRING)
    @Column(name = "resus_type")
    private ResusType resusType;

    @Column(name = "details")
    private String details;

    @Column(name = "wolof_audio_details",length = 512)
    private String wolofAudioDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBloodRequests")
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
    private Set<Donor> createdBies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "canceledBloodRequests")
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
    private Set<Donor> canceledBies = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "organizationPartner", "donors", "bloodRequests", "bloodDonationHistories" },
        allowSetters = true
    )
    private DonationCenter donationCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BloodRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public BloodRequest createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BloodRequestStatus getStatus() {
        return this.status;
    }

    public BloodRequest status(BloodRequestStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(BloodRequestStatus status) {
        this.status = status;
    }

    public BloodType getBloodType() {
        return this.bloodType;
    }

    public BloodRequest bloodType(BloodType bloodType) {
        this.setBloodType(bloodType);
        return this;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public ResusType getResusType() {
        return this.resusType;
    }

    public BloodRequest resusType(ResusType resusType) {
        this.setResusType(resusType);
        return this;
    }

    public void setResusType(ResusType resusType) {
        this.resusType = resusType;
    }

    public String getDetails() {
        return this.details;
    }

    public BloodRequest details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getWolofAudioDetails() {
        return this.wolofAudioDetails;
    }

    public BloodRequest wolofAudioDetails(String wolofAudioDetails) {
        this.setWolofAudioDetails(wolofAudioDetails);
        return this;
    }

    public void setWolofAudioDetails(String wolofAudioDetails) {
        this.wolofAudioDetails = wolofAudioDetails;
    }

    public Set<Donor> getCreatedBies() {
        return this.createdBies;
    }

    public void setCreatedBies(Set<Donor> donors) {
        if (this.createdBies != null) {
            this.createdBies.forEach(i -> i.setCreatedBloodRequests(null));
        }
        if (donors != null) {
            donors.forEach(i -> i.setCreatedBloodRequests(this));
        }
        this.createdBies = donors;
    }

    public BloodRequest createdBies(Set<Donor> donors) {
        this.setCreatedBies(donors);
        return this;
    }

    public BloodRequest addCreatedBy(Donor donor) {
        this.createdBies.add(donor);
        donor.setCreatedBloodRequests(this);
        return this;
    }

    public BloodRequest removeCreatedBy(Donor donor) {
        this.createdBies.remove(donor);
        donor.setCreatedBloodRequests(null);
        return this;
    }

    public Set<Donor> getCanceledBies() {
        return this.canceledBies;
    }

    public void setCanceledBies(Set<Donor> donors) {
        if (this.canceledBies != null) {
            this.canceledBies.forEach(i -> i.setCanceledBloodRequests(null));
        }
        if (donors != null) {
            donors.forEach(i -> i.setCanceledBloodRequests(this));
        }
        this.canceledBies = donors;
    }

    public BloodRequest canceledBies(Set<Donor> donors) {
        this.setCanceledBies(donors);
        return this;
    }

    public BloodRequest addCanceledBy(Donor donor) {
        this.canceledBies.add(donor);
        donor.setCanceledBloodRequests(this);
        return this;
    }

    public BloodRequest removeCanceledBy(Donor donor) {
        this.canceledBies.remove(donor);
        donor.setCanceledBloodRequests(null);
        return this;
    }

    public DonationCenter getDonationCenter() {
        return this.donationCenter;
    }

    public void setDonationCenter(DonationCenter donationCenter) {
        this.donationCenter = donationCenter;
    }

    public BloodRequest donationCenter(DonationCenter donationCenter) {
        this.setDonationCenter(donationCenter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BloodRequest)) {
            return false;
        }
        return getId() != null && getId().equals(((BloodRequest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodRequest{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", bloodType='" + getBloodType() + "'" +
            ", resusType='" + getResusType() + "'" +
            ", details='" + getDetails() + "'" +
            ", wolofAudioDetails='" + getWolofAudioDetails() + "'" +
            "}";
    }
}
