package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationCampaign;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.ShareInfoRequest;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.ShareInfoRequestDTO;

/**
 * Mapper for the entity {@link ShareInfoRequest} and its DTO {@link ShareInfoRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShareInfoRequestMapper {

    @Mapping(target = "bloodDonationCampaign", source = "bloodDonationCampaign", qualifiedByName = "campaignId")
    @Mapping(target = "donationCenter", source = "donationCenter", qualifiedByName = "centerId")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(source = "sharedInfoSelection", target = "sharedInfoSelection")
    @Mapping(source = "sharedInfoUser", target = "sharedInfoUser")
        // Si le PhoneNumber n'existe pas dans l'entité, nous ne pouvons pas le mapper directement
    ShareInfoRequestDTO toDto(ShareInfoRequest shareInfoRequest);

    @Mapping(target = "bloodDonationCampaign", source = "bloodDonationCampaign", qualifiedByName = "campaignEntity")
    @Mapping(target = "donationCenter", source = "donationCenter", qualifiedByName = "centerEntity")
    ShareInfoRequest toEntity(ShareInfoRequestDTO shareInfoRequestDTO);

    @Named("campaignId")
    default Long campaignId(BloodDonationCampaign campaign) {
        return campaign != null ? campaign.getId() : null;
    }

    @Named("centerId")
    default Long centerId(DonationCenter center) {
        return center != null ? center.getId() : null;
    }

    @Named("campaignEntity")
    default BloodDonationCampaign campaignEntity(Long id) {
        if (id == null) {
            return null;
        }
        BloodDonationCampaign campaign = new BloodDonationCampaign();
        campaign.setId(id);
        return campaign;
    }

    @Named("centerEntity")
    default DonationCenter centerEntity(Long id) {
        if (id == null) {
            return null;
        }
        DonationCenter center = new DonationCenter();
        center.setId(id);
        return center;
    }
}