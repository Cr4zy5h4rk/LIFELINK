package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Address;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Region;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.AddressDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.RegionDTO;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    AddressDTO toDto(Address s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);
}
