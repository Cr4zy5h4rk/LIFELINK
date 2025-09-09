package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Role;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.RoleDTO;

/**
 * Mapper for the entity {@link Role} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
    @Mapping(target = "donors", source = "donors", qualifiedByName = "donorIdSet")
    RoleDTO toDto(Role s);

    @Mapping(target = "donors", ignore = true)
    @Mapping(target = "removeDonor", ignore = true)
    Role toEntity(RoleDTO roleDTO);

    @Named("donorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonorDTO toDtoDonorId(Donor donor);

    @Named("donorIdSet")
    default Set<DonorDTO> toDtoDonorIdSet(Set<Donor> donor) {
        return donor.stream().map(this::toDtoDonorId).collect(Collectors.toSet());
    }
}
