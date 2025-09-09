package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationCampaign;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonorCampaign;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodDonationCampaignDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorCampaignDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorDTO;

/**
 * Mapper for the entity {@link DonorCampaign} and its DTO {@link DonorCampaignDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonorCampaignMapper extends EntityMapper<DonorCampaignDTO, DonorCampaign> {
    @Mapping(target = "donor", source = "donor", qualifiedByName = "donorId")
    @Mapping(target = "bloodDonationCampaign", source = "bloodDonationCampaign", qualifiedByName = "bloodDonationCampaignId")
    DonorCampaignDTO toDto(DonorCampaign s);

    @Named("donorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonorDTO toDtoDonorId(Donor donor);

    @Named("bloodDonationCampaignId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BloodDonationCampaignDTO toDtoBloodDonationCampaignId(BloodDonationCampaign bloodDonationCampaign);
}
