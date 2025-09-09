package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Region;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.RegionStatistics;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.RegionDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.RegionStatisticsDTO;

/**
 * Mapper for the entity {@link RegionStatistics} and its DTO {@link RegionStatisticsDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionStatisticsMapper extends EntityMapper<RegionStatisticsDTO, RegionStatistics> {
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    RegionStatisticsDTO toDto(RegionStatistics s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);
}
