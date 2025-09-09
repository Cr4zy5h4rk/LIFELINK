package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Statistics;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Statistics entity.
 */
@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    /**
     * Find the latest statistics (highest ID, assuming ID is year-based).
     *
     * @return the latest statistics
     */
    @Query("SELECT s FROM Statistics s ORDER BY s.id DESC")
    Optional<Statistics> findLatest();

    /**
     * Find statistics for the current year.
     *
     * @param year the current year
     * @return the statistics for the current year
     */
    Optional<Statistics> findById(Long year);
}