package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Address;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.OrganizationPartner;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.AddressDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonationCenterDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.OrganizationPartnerDTO;

/**
 * Mapper for the entity {@link DonationCenter} and its DTO {@link DonationCenterDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonationCenterMapper extends EntityMapper<DonationCenterDTO, DonationCenter> {
    @Mapping(target = "organizationPartner", source = "organizationPartner", qualifiedByName = "organizationPartnerId")
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    DonationCenterDTO toDto(DonationCenter s);

    @Named("organizationPartnerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationPartnerDTO toDtoOrganizationPartnerId(OrganizationPartner organizationPartner);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);
}
