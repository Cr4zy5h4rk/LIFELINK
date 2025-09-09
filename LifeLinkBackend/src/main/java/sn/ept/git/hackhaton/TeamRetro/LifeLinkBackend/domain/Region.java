package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Region.
 */
@Entity
@Table(name = "region")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    @JsonIgnoreProperties(value = { "region" }, allowSetters = true)
    private Set<RegionStatistics> regionStatistics = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    @JsonIgnoreProperties(value = { "region", "donor", "donationCenters" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Region id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Region name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RegionStatistics> getRegionStatistics() {
        return this.regionStatistics;
    }

    public void setRegionStatistics(Set<RegionStatistics> regionStatistics) {
        if (this.regionStatistics != null) {
            this.regionStatistics.forEach(i -> i.setRegion(null));
        }
        if (regionStatistics != null) {
            regionStatistics.forEach(i -> i.setRegion(this));
        }
        this.regionStatistics = regionStatistics;
    }

    public Region regionStatistics(Set<RegionStatistics> regionStatistics) {
        this.setRegionStatistics(regionStatistics);
        return this;
    }

    public Region addRegionStatistics(RegionStatistics regionStatistics) {
        this.regionStatistics.add(regionStatistics);
        regionStatistics.setRegion(this);
        return this;
    }

    public Region removeRegionStatistics(RegionStatistics regionStatistics) {
        this.regionStatistics.remove(regionStatistics);
        regionStatistics.setRegion(null);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setRegion(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setRegion(this));
        }
        this.addresses = addresses;
    }

    public Region addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Region addAddress(Address address) {
        this.addresses.add(address);
        address.setRegion(this);
        return this;
    }

    public Region removeAddress(Address address) {
        this.addresses.remove(address);
        address.setRegion(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Region)) {
            return false;
        }
        return getId() != null && getId().equals(((Region) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
