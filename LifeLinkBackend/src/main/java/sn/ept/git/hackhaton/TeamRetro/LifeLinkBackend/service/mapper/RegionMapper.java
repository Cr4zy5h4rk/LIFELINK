package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Region;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.RegionDTO;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {}
