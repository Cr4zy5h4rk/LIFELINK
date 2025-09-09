package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A OrganizationPartner.
 */
@Entity
@Table(name = "organization_partner")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationPartner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image")
    private String image;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employees")
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

    @JsonIgnoreProperties(
        value = { "organizationPartner", "donors", "address", "bloodRequests", "bloodDonationHistories" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "organizationPartner")
    private DonationCenter donationCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrganizationPartner id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public OrganizationPartner name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return this.image;
    }

    public OrganizationPartner image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Donor> getDonors() {
        return this.donors;
    }

    public void setDonors(Set<Donor> donors) {
        if (this.donors != null) {
            this.donors.forEach(i -> i.setEmployees(null));
        }
        if (donors != null) {
            donors.forEach(i -> i.setEmployees(this));
        }
        this.donors = donors;
    }

    public OrganizationPartner donors(Set<Donor> donors) {
        this.setDonors(donors);
        return this;
    }

    public OrganizationPartner addDonor(Donor donor) {
        this.donors.add(donor);
        donor.setEmployees(this);
        return this;
    }

    public OrganizationPartner removeDonor(Donor donor) {
        this.donors.remove(donor);
        donor.setEmployees(null);
        return this;
    }

    public DonationCenter getDonationCenter() {
        return this.donationCenter;
    }

    public void setDonationCenter(DonationCenter donationCenter) {
        if (this.donationCenter != null) {
            this.donationCenter.setOrganizationPartner(null);
        }
        if (donationCenter != null) {
            donationCenter.setOrganizationPartner(this);
        }
        this.donationCenter = donationCenter;
    }

    public OrganizationPartner donationCenter(DonationCenter donationCenter) {
        this.setDonationCenter(donationCenter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationPartner)) {
            return false;
        }
        return getId() != null && getId().equals(((OrganizationPartner) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationPartner{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
