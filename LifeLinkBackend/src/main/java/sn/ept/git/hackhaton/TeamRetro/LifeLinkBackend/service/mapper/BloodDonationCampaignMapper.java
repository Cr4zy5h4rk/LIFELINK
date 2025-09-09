package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.springframework.stereotype.Service;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationCampaign;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.CampaignDTO;

import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mapper for converting between {@link BloodDonationCampaign} and {@link CampaignDTO}.
 */
@Service
public class BloodDonationCampaignMapper {

    /**
     * Convert a BloodDonationCampaign entity to a CampaignDTO.
     *
     * @param bloodDonationCampaign the entity to convert
     * @return the DTO
     */
    public CampaignDTO toDto(BloodDonationCampaign bloodDonationCampaign) {
        if (bloodDonationCampaign == null) {
            return null;
        }

        CampaignDTO campaignDTO = new CampaignDTO();

        // Set basic fields
        campaignDTO.setId(bloodDonationCampaign.getId());
        campaignDTO.setContactPhone(bloodDonationCampaign.getContact());
        campaignDTO.setImageUrl(bloodDonationCampaign.getImage());
        campaignDTO.setStatus(bloodDonationCampaign.getStatus());

        String location = String.format(
                "%s, %s",
                bloodDonationCampaign.getAddress().getRegion().getName(),
                bloodDonationCampaign.getAddress().getAddress()
        );

        campaignDTO.setLocation(location);
        campaignDTO.setSchedule(bloodDonationCampaign.getCampaignDetails());
        campaignDTO.setDescription(bloodDonationCampaign.getWolofAudioDescription());
        campaignDTO.setTitle("Campage de collecte de sang");


        // Set dates
        campaignDTO.setStartDate(bloodDonationCampaign.getStartAt());
        campaignDTO.setEndDate(bloodDonationCampaign.getEndAt());

        return campaignDTO;
    }

    /**
     * Convert a CampaignDTO to a BloodDonationCampaign entity.
     *
     * @param campaignDTO the DTO to convert
     * @return the entity
     */
    public BloodDonationCampaign toEntity(CampaignDTO campaignDTO) {
        if (campaignDTO == null) {
            return null;
        }

        BloodDonationCampaign bloodDonationCampaign = new BloodDonationCampaign();

        // Set ID if provided
        if (campaignDTO.getId() != null) {
            bloodDonationCampaign.setId(campaignDTO.getId());
        }else{
            bloodDonationCampaign.setCreatedAt(ZonedDateTime.now());
        }


        // Set basic fields
        bloodDonationCampaign.setContact(campaignDTO.getContactPhone());
        bloodDonationCampaign.setImage(campaignDTO.getImageUrl());
        bloodDonationCampaign.setStatus(campaignDTO.getStatus());

        bloodDonationCampaign.setCampaignDetails(campaignDTO.getSchedule());

        bloodDonationCampaign.setEndAt(campaignDTO.getEndDate());
        bloodDonationCampaign.setStartAt(campaignDTO.getStartDate());
        bloodDonationCampaign.setWolofAudioDescription(campaignDTO.getDescription());

        return bloodDonationCampaign;
    }


}