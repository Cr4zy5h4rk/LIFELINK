package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationCampaign;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.CampaignStatus;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.CampaignDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Campaigns.
 */
public interface BloodDonationCampaignService {

    List<CampaignDTO> getLatestActiveCampaigns();

    /**
     * Save a campaign.
     *
     * @param campaignDTO the entity to save.
     * @return the persisted entity.
     */
    CampaignDTO save(CampaignDTO campaignDTO);

    /**
     * Update a campaign.
     *
     * @param campaignDTO the entity to update.
     * @return the persisted entity.
     */
    CampaignDTO update(CampaignDTO campaignDTO);

    /**
     * Get all the campaigns.
     *
     * @return the list of entities.
     */
    List<CampaignDTO> findAll();

    /**
     * Get one campaign by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignDTO> findOne(Long id);

    /**
     * Delete the campaign by id.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get all campaigns with a specific status.
     *
     * @param status the status to filter by.
     * @return the list of entities.
     */
    List<CampaignDTO> findByStatus(CampaignStatus status);

    /**
     * Get all campaigns in a specific region.
     *
     * @param region the region to filter by.
     * @return the list of entities.
     */
    List<CampaignDTO> findByRegion(String region);

    /**
     * Get all active campaigns (current date is between start and end date).
     *
     * @return the list of active campaigns.
     */
    List<CampaignDTO> findActiveCampaigns();
}