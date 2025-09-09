package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DonationCenter.
 */
@Entity
@Table(name = "donation_center")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonationCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "structure_name", nullable = false)
    private String structureName;

    @Column(name = "contact")
    private String contact;


    @Column(name = "center_details",length = 300)
    private String centerDetails;


    @Column(name = "wolof_audio_details",length = 512)
    private String wolofAudioDetails;

    @JsonIgnoreProperties(value = { "donors", "donationCenter" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private OrganizationPartner organizationPartner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalStaff")
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
    private Set<Donor> donors = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties(value = { "region", "donor", "donationCenters" }, allowSetters = true)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "donationCenter")
    @JsonIgnoreProperties(value = { "createdBies", "canceledBies", "donationCenter" }, allowSetters = true)
    private Set<BloodRequest> bloodRequests = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "donationCenter")
    @JsonIgnoreProperties(value = { "donor", "bloodDonationCampaign", "donationCenter" }, allowSetters = true)
    private Set<BloodDonationHistory> bloodDonationHistories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DonationCenter id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStructureName() {
        return this.structureName;
    }

    public DonationCenter structureName(String structureName) {
        this.setStructureName(structureName);
        return this;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getContact() {
        return this.contact;
    }

    public DonationCenter contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCenterDetails() {
        return this.centerDetails;
    }

    public DonationCenter centerDetails(String centerDetails) {
        this.setCenterDetails(centerDetails);
        return this;
    }

    public void setCenterDetails(String centerDetails) {
        this.centerDetails = centerDetails;
    }

    public String getWolofAudioDetails() {
        return this.wolofAudioDetails;
    }

    public DonationCenter wolofAudioDetails(String wolofAudioDetails) {
        this.setWolofAudioDetails(wolofAudioDetails);
        return this;
    }

    public void setWolofAudioDetails(String wolofAudioDetails) {
        this.wolofAudioDetails = wolofAudioDetails;
    }

    public OrganizationPartner getOrganizationPartner() {
        return this.organizationPartner;
    }

    public void setOrganizationPartner(OrganizationPartner organizationPartner) {
        this.organizationPartner = organizationPartner;
    }

    public DonationCenter organizationPartner(OrganizationPartner organizationPartner) {
        this.setOrganizationPartner(organizationPartner);
        return this;
    }

    public Set<Donor> getDonors() {
        return this.donors;
    }

    public void setDonors(Set<Donor> donors) {
        if (this.donors != null) {
            this.donors.forEach(i -> i.setMedicalStaff(null));
        }
        if (donors != null) {
            donors.forEach(i -> i.setMedicalStaff(this));
        }
        this.donors = donors;
    }

    public DonationCenter donors(Set<Donor> donors) {
        this.setDonors(donors);
        return this;
    }

    public DonationCenter addDonor(Donor donor) {
        this.donors.add(donor);
        donor.setMedicalStaff(this);
        return this;
    }

    public DonationCenter removeDonor(Donor donor) {
        this.donors.remove(donor);
        donor.setMedicalStaff(null);
        return this;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DonationCenter address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Set<BloodRequest> getBloodRequests() {
        return this.bloodRequests;
    }

    public void setBloodRequests(Set<BloodRequest> bloodRequests) {
        if (this.bloodRequests != null) {
            this.bloodRequests.forEach(i -> i.setDonationCenter(null));
        }
        if (bloodRequests != null) {
            bloodRequests.forEach(i -> i.setDonationCenter(this));
        }
        this.bloodRequests = bloodRequests;
    }

    public DonationCenter bloodRequests(Set<BloodRequest> bloodRequests) {
        this.setBloodRequests(bloodRequests);
        return this;
    }

    public DonationCenter addBloodRequest(BloodRequest bloodRequest) {
        this.bloodRequests.add(bloodRequest);
        bloodRequest.setDonationCenter(this);
        return this;
    }

    public DonationCenter removeBloodRequest(BloodRequest bloodRequest) {
        this.bloodRequests.remove(bloodRequest);
        bloodRequest.setDonationCenter(null);
        return this;
    }

    public Set<BloodDonationHistory> getBloodDonationHistories() {
        return this.bloodDonationHistories;
    }

    public void setBloodDonationHistories(Set<BloodDonationHistory> bloodDonationHistories) {
        if (this.bloodDonationHistories != null) {
            this.bloodDonationHistories.forEach(i -> i.setDonationCenter(null));
        }
        if (bloodDonationHistories != null) {
            bloodDonationHistories.forEach(i -> i.setDonationCenter(this));
        }
        this.bloodDonationHistories = bloodDonationHistories;
    }

    public DonationCenter bloodDonationHistories(Set<BloodDonationHistory> bloodDonationHistories) {
        this.setBloodDonationHistories(bloodDonationHistories);
        return this;
    }

    public DonationCenter addBloodDonationHistory(BloodDonationHistory bloodDonationHistory) {
        this.bloodDonationHistories.add(bloodDonationHistory);
        bloodDonationHistory.setDonationCenter(this);
        return this;
    }

    public DonationCenter removeBloodDonationHistory(BloodDonationHistory bloodDonationHistory) {
        this.bloodDonationHistories.remove(bloodDonationHistory);
        bloodDonationHistory.setDonationCenter(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationCenter)) {
            return false;
        }
        return getId() != null && getId().equals(((DonationCenter) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationCenter{" +
            "id=" + getId() +
            ", structureName='" + getStructureName() + "'" +
            ", contact='" + getContact() + "'" +
            ", centerDetails='" + getCenterDetails() + "'" +
            ", wolofAudioDetails='" + getWolofAudioDetails() + "'" +
            "}";
    }
}
