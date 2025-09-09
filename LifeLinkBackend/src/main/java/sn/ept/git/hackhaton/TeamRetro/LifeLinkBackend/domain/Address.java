package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "longitude",nullable = false)
    private Double longitude;

    @Column(name = "latitude",nullable = false)
    private Double latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "regionStatistics", "addresses" }, allowSetters = true)
    private Region region;

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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    private List<Donor> donor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    @JsonIgnoreProperties(
        value = { "organizationPartner", "donors", "address", "bloodRequests", "bloodDonationHistories" },
        allowSetters = true
    )
    private Set<DonationCenter> donationCenters = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public Address address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Address longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Address latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public List<Donor> getDonor() {
        return donor;
    }

    public void setDonor(List<Donor> donor) {
        this.donor = donor;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Address region(Region region) {
        this.setRegion(region);
        return this;
    }


    public Set<DonationCenter> getDonationCenters() {
        return this.donationCenters;
    }

    public void setDonationCenters(Set<DonationCenter> donationCenters) {
        if (this.donationCenters != null) {
            this.donationCenters.forEach(i -> i.setAddress(null));
        }
        if (donationCenters != null) {
            donationCenters.forEach(i -> i.setAddress(this));
        }
        this.donationCenters = donationCenters;
    }

    public Address donationCenters(Set<DonationCenter> donationCenters) {
        this.setDonationCenters(donationCenters);
        return this;
    }

    public Address addDonationCenter(DonationCenter donationCenter) {
        this.donationCenters.add(donationCenter);
        donationCenter.setAddress(this);
        return this;
    }

    public Address removeDonationCenter(DonationCenter donationCenter) {
        this.donationCenters.remove(donationCenter);
        donationCenter.setAddress(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return getId() != null && getId().equals(((Address) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            "}";
    }
}
