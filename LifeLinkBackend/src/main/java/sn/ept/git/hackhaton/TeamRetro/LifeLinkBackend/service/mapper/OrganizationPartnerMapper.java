package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.OrganizationPartner;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.OrganizationPartnerDTO;

/**
 * Mapper for the entity {@link OrganizationPartner} and its DTO {@link OrganizationPartnerDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationPartnerMapper extends EntityMapper<OrganizationPartnerDTO, OrganizationPartner> {}
