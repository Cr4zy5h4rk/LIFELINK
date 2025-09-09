package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import java.util.List;
import java.util.Optional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.OrganizationPartnerDTO;

/**
 * Service Interface for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.OrganizationPartner}.
 */
public interface OrganizationPartnerService {
    /**
     * Save a organizationPartner.
     *
     * @param organizationPartnerDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationPartnerDTO save(OrganizationPartnerDTO organizationPartnerDTO);

    /**
     * Updates a organizationPartner.
     *
     * @param organizationPartnerDTO the entity to update.
     * @return the persisted entity.
     */
    OrganizationPartnerDTO update(OrganizationPartnerDTO organizationPartnerDTO);

    /**
     * Partially updates a organizationPartner.
     *
     * @param organizationPartnerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganizationPartnerDTO> partialUpdate(OrganizationPartnerDTO organizationPartnerDTO);

    /**
     * Get all the organizationPartners.
     *
     * @return the list of entities.
     */
    List<OrganizationPartnerDTO> findAll();

    /**
     * Get all the OrganizationPartnerDTO where DonationCenter is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OrganizationPartnerDTO> findAllWhereDonationCenterIsNull();

    /**
     * Get the "id" organizationPartner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationPartnerDTO> findOne(Long id);

    /**
     * Delete the "id" organizationPartner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
