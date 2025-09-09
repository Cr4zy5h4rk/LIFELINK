package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import java.util.List;
import java.util.Optional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorCampaignDTO;

/**
 * Service Interface for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonorCampaign}.
 */
public interface DonorCampaignService {
    /**
     * Save a donorCampaign.
     *
     * @param donorCampaignDTO the entity to save.
     * @return the persisted entity.
     */
    DonorCampaignDTO save(DonorCampaignDTO donorCampaignDTO);

    /**
     * Updates a donorCampaign.
     *
     * @param donorCampaignDTO the entity to update.
     * @return the persisted entity.
     */
    DonorCampaignDTO update(DonorCampaignDTO donorCampaignDTO);

    /**
     * Partially updates a donorCampaign.
     *
     * @param donorCampaignDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DonorCampaignDTO> partialUpdate(DonorCampaignDTO donorCampaignDTO);

    /**
     * Get all the donorCampaigns.
     *
     * @return the list of entities.
     */
    List<DonorCampaignDTO> findAll();

    /**
     * Get the "id" donorCampaign.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonorCampaignDTO> findOne(Long id);

    /**
     * Delete the "id" donorCampaign.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
