package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.OrganizationPartner} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationPartnerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationPartnerDTO)) {
            return false;
        }

        OrganizationPartnerDTO organizationPartnerDTO = (OrganizationPartnerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationPartnerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationPartnerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
