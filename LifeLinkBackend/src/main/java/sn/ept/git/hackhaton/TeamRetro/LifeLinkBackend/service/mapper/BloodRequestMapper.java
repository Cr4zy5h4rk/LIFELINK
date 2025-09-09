package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodRequest;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodRequestCreateDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodRequestDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonationCenterDTO;

/**
 * Mapper for the entity {@link BloodRequest} and its DTO {@link BloodRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface BloodRequestMapper extends EntityMapper<BloodRequestDTO, BloodRequest> {
    @Mapping(target = "donationCenter", source = "donationCenter", qualifiedByName = "donationCenterId")
    BloodRequestDTO toDto(BloodRequest s);


    @Named("donationCenterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonationCenterDTO toDtoDonationCenterId(DonationCenter donationCenter);

    @Mapping(target = "donationCenter", source = "donationCenterId", qualifiedByName = "donationCenterFromId")
    BloodRequest toEntity(BloodRequestCreateDTO dto);


    @Named("donationCenterFromId")
    default DonationCenter donationCenterFromId(Long id) {
        if (id == null) {
            return null;
        }
        DonationCenter donationCenter = new DonationCenter();
        donationCenter.setId(id);
        return donationCenter;
    }

    @Mapping(target = "donationCenterId", source = "donationCenter.id")
    BloodRequestCreateDTO toCreateDto(BloodRequest entity);


}
