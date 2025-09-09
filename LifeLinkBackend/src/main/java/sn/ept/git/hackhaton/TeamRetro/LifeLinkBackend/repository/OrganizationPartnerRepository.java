package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.OrganizationPartner;

/**
 * Spring Data JPA repository for the OrganizationPartner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationPartnerRepository extends JpaRepository<OrganizationPartner, Long> {}
