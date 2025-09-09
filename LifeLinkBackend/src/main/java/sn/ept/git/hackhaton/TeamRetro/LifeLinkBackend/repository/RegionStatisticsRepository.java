package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.RegionStatistics;

/**
 * Spring Data JPA repository for the RegionStatistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionStatisticsRepository extends JpaRepository<RegionStatistics, Long> {}
