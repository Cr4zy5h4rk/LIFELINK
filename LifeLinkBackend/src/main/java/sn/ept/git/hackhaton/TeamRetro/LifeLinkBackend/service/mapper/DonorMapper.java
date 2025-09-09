package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Address;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Article;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodRequest;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.OrganizationPartner;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Role;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.AddressDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.ArticleDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodRequestDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonationCenterDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.OrganizationPartnerDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.RoleDTO;

/**
 * Mapper for the entity {@link Donor} and its DTO {@link DonorDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonorMapper extends EntityMapper<DonorDTO, Donor> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target="medicalData", source = "medicalData")
    @Mapping(target = "favorites", source = "favorites", qualifiedByName = "articleIdSet")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleIdSet")
    @Mapping(target = "medicalStaff", source = "medicalStaff", qualifiedByName = "donationCenterId")
    @Mapping(target = "createdBloodRequests", source = "createdBloodRequests", qualifiedByName = "bloodRequestId")
    @Mapping(target = "canceledBloodRequests", source = "canceledBloodRequests", qualifiedByName = "bloodRequestId")
    @Mapping(target = "employees", source = "employees", qualifiedByName = "organizationPartnerId")
    DonorDTO toDto(Donor s);

    @Mapping(target = "removeFavorites", ignore = true)
    @Mapping(target = "removeRoles", ignore = true)
    Donor toEntity(DonorDTO donorDTO);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("articleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArticleDTO toDtoArticleId(Article article);

    @Named("articleIdSet")
    default Set<ArticleDTO> toDtoArticleIdSet(Set<Article> article) {
        return article.stream().map(this::toDtoArticleId).collect(Collectors.toSet());
    }

    @Named("roleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoleDTO toDtoRoleId(Role role);

    @Named("roleIdSet")
    default Set<RoleDTO> toDtoRoleIdSet(Set<Role> role) {
        return role.stream().map(this::toDtoRoleId).collect(Collectors.toSet());
    }

    @Named("donationCenterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonationCenterDTO toDtoDonationCenterId(DonationCenter donationCenter);

    @Named("bloodRequestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BloodRequestDTO toDtoBloodRequestId(BloodRequest bloodRequest);

    @Named("organizationPartnerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationPartnerDTO toDtoOrganizationPartnerId(OrganizationPartner organizationPartner);
}
