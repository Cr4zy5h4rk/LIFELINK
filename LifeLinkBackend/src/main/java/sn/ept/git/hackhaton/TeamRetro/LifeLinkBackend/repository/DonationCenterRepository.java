package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter;

import java.util.Optional;

/**
 * Spring Data JPA repository for the DonationCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonationCenterRepository extends JpaRepository<DonationCenter, Long> {

    /**
     * Find a donation center by its name.
     *
     * @param name the name of the donation center
     * @return an Optional containing the found donation center or empty if not found
     */
    Optional<DonationCenter> findByStructureName(String name);
}
