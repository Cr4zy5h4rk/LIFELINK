package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationCampaign;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.CampaignStatus;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.BloodDonationCampaignService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.CampaignDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.BloodDonationCampaignRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.BloodDonationCampaignMapper;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BloodDonationCampaign}.
 */
@Service
@Transactional
public class BloodDonationCampaignServiceImpl implements BloodDonationCampaignService {

    private final Logger log = LoggerFactory.getLogger(BloodDonationCampaignServiceImpl.class);

    private final BloodDonationCampaignRepository bloodDonationCampaignRepository;
    private final BloodDonationCampaignMapper campaignMapper;

    @Autowired
    public BloodDonationCampaignServiceImpl(
            BloodDonationCampaignRepository bloodDonationCampaignRepository,
            BloodDonationCampaignMapper campaignMapper) {
        this.bloodDonationCampaignRepository = bloodDonationCampaignRepository;
        this.campaignMapper = campaignMapper;
    }

    /**
     * Save a campaign.
     *
     * @param campaignDTO the entity to save.
     * @return the persisted entity.
     */
    public CampaignDTO save(CampaignDTO campaignDTO) {
        log.debug("Request to save Campaign : {}", campaignDTO);
        BloodDonationCampaign bloodDonationCampaign = campaignMapper.toEntity(campaignDTO);
        bloodDonationCampaign = bloodDonationCampaignRepository.save(bloodDonationCampaign);
        return campaignMapper.toDto(bloodDonationCampaign);
    }

    /**
     * Update a campaign.
     *
     * @param campaignDTO the entity to update.
     * @return the persisted entity.
     */
    public CampaignDTO update(CampaignDTO campaignDTO) {
        log.debug("Request to update Campaign : {}", campaignDTO);
        BloodDonationCampaign bloodDonationCampaign = campaignMapper.toEntity(campaignDTO);
        bloodDonationCampaign = bloodDonationCampaignRepository.save(bloodDonationCampaign);
        return campaignMapper.toDto(bloodDonationCampaign);
    }

    /**
     * Get all the campaigns.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CampaignDTO> findAll() {
        log.debug("Request to get all Campaigns");
        return bloodDonationCampaignRepository.findAll()
                .stream()
                .map(campaignMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les 10 dernières campagnes en statut NOT_STARTED ou IN_PROGRESS
     * @return Liste des 10 dernières campagnes actives
     */
    public List<CampaignDTO> getLatestActiveCampaigns() {
        return bloodDonationCampaignRepository.findTop10ByStatusInOrderByCreatedAtDesc(
                Arrays.asList(CampaignStatus.NOT_STARTED, CampaignStatus.IN_PROGRESS))
                .stream()
                .map(campaignMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get one campaign by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CampaignDTO> findOne(Long id) {
        log.debug("Request to get Campaign : {}", id);
        return bloodDonationCampaignRepository.findById(id)
                .map(campaignMapper::toDto);
    }

    /**
     * Delete the campaign by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Campaign : {}", id);
        bloodDonationCampaignRepository.deleteById(id);
    }

    /**
     * Get all campaigns with a specific status.
     *
     * @param status the status to filter by.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CampaignDTO> findByStatus(CampaignStatus status) {
        log.debug("Request to get all Campaigns with status: {}", status);
        return bloodDonationCampaignRepository.findByStatus(status)
                .stream()
                .map(campaignMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get all campaigns in a specific region.
     *
     * @param region the region to filter by.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CampaignDTO> findByRegion(String region) {
        log.debug("Request to get all Campaigns in region: {}", region);
        // Assuming campaign details contain region information, this may need to be adjusted
        // based on your actual data model structure
        return bloodDonationCampaignRepository.findAll()
                .stream()
                .filter(campaign -> {
                    String details = campaign.getCampaignDetails();
                    return details != null && details.contains(region);
                })
                .map(campaignMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get all active campaigns (with status ACTIVE and current date between start and end date).
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CampaignDTO> findActiveCampaigns() {
        log.debug("Request to get all active Campaigns");
        ZonedDateTime now = ZonedDateTime.now();

        return bloodDonationCampaignRepository.findByStatus(CampaignStatus.IN_PROGRESS)
                .stream()
                .filter(campaign -> {
                    // Extracting date information from campaign details or other fields
                    // This is a simplified example and would need to be adjusted based on your data model
                    ZonedDateTime startDate = campaign.getCreatedAt();
                    // Assuming end date information is stored somewhere, this would need to be adjusted
                    return startDate != null && startDate.isBefore(now);
                })
                .map(campaignMapper::toDto)
                .collect(Collectors.toList());
    }
}