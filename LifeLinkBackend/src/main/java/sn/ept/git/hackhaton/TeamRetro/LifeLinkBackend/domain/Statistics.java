package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Statistics.
 */
@Entity
@Table(name = "statistics")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Statistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "number_of_life_saved")
    private Integer numberOfLifeSaved;

    @Column(name = "target_blood_bags")
    private Integer targetBloodBags;

    @Column(name = "number_of_donors")
    private Integer numberOfDonors;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Statistics id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfLifeSaved() {
        return this.numberOfLifeSaved;
    }

    public Statistics numberOfLifeSaved(Integer numberOfLifeSaved) {
        this.setNumberOfLifeSaved(numberOfLifeSaved);
        return this;
    }

    public void setNumberOfLifeSaved(Integer numberOfLifeSaved) {
        this.numberOfLifeSaved = numberOfLifeSaved;
    }

    public Integer getTargetBloodBags() {
        return this.targetBloodBags;
    }

    public Statistics targetBloodBags(Integer targetBloodBags) {
        this.setTargetBloodBags(targetBloodBags);
        return this;
    }

    public void setTargetBloodBags(Integer targetBloodBags) {
        this.targetBloodBags = targetBloodBags;
    }

    public Integer getNumberOfDonors() {
        return this.numberOfDonors;
    }

    public Statistics numberOfDonors(Integer numberOfDonors) {
        this.setNumberOfDonors(numberOfDonors);
        return this;
    }

    public void setNumberOfDonors(Integer numberOfDonors) {
        this.numberOfDonors = numberOfDonors;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Statistics)) {
            return false;
        }
        return getId() != null && getId().equals(((Statistics) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Statistics{" +
            "id=" + getId() +
            ", numberOfLifeSaved=" + getNumberOfLifeSaved() +
            ", targetBloodBags=" + getTargetBloodBags() +
            ", numberOfDonors=" + getNumberOfDonors() +
            "}";
    }
}
