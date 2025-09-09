package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationCampaign;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationHistory;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodDonationCampaignDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodDonationHistoryDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonationCenterDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorDTO;

/**
 * Mapper for the entity {@link BloodDonationHistory} and its DTO {@link BloodDonationHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface BloodDonationHistoryMapper extends EntityMapper<BloodDonationHistoryDTO, BloodDonationHistory> {
    @Mapping(target = "donor", source = "donor", qualifiedByName = "donorId")
    @Mapping(target = "bloodDonationCampaign", source = "bloodDonationCampaign", qualifiedByName = "bloodDonationCampaignId")
    @Mapping(target = "donationCenter", source = "donationCenter", qualifiedByName = "donationCenterId")
    BloodDonationHistoryDTO toDto(BloodDonationHistory s);

    @Named("donorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonorDTO toDtoDonorId(Donor donor);

    @Named("bloodDonationCampaignId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BloodDonationCampaignDTO toDtoBloodDonationCampaignId(BloodDonationCampaign bloodDonationCampaign);

    @Named("donationCenterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonationCenterDTO toDtoDonationCenterId(DonationCenter donationCenter);
}
