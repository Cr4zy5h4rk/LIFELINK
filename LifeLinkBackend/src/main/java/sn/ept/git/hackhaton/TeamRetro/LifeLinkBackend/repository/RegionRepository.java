package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Region;

/**
 * Spring Data JPA repository for the Region entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {}
