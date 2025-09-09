package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonationCenterDTO implements Serializable {

    private Long id;

    @NotNull
    @JsonProperty("hospital")
    private String structureName;

    private String contact;

    @Lob
    private String centerDetails;

    @Lob
    private String wolofAudioDetails;

    private OrganizationPartnerDTO organizationPartner;

    private AddressDTO address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCenterDetails() {
        return centerDetails;
    }

    public void setCenterDetails(String centerDetails) {
        this.centerDetails = centerDetails;
    }

    public String getWolofAudioDetails() {
        return wolofAudioDetails;
    }

    public void setWolofAudioDetails(String wolofAudioDetails) {
        this.wolofAudioDetails = wolofAudioDetails;
    }

    public OrganizationPartnerDTO getOrganizationPartner() {
        return organizationPartner;
    }

    public void setOrganizationPartner(OrganizationPartnerDTO organizationPartner) {
        this.organizationPartner = organizationPartner;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationCenterDTO)) {
            return false;
        }

        DonationCenterDTO donationCenterDTO = (DonationCenterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donationCenterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationCenterDTO{" +
            "id=" + getId() +
            ", structureName='" + getStructureName() + "'" +
            ", contact='" + getContact() + "'" +
            ", centerDetails='" + getCenterDetails() + "'" +
            ", wolofAudioDetails='" + getWolofAudioDetails() + "'" +
            ", organizationPartner=" + getOrganizationPartner() +
            ", address=" + getAddress() +
            "}";
    }
}
