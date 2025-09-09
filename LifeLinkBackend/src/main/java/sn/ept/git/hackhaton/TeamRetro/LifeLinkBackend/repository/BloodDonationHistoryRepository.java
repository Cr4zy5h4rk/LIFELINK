package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationHistory;

/**
 * Spring Data JPA repository for the BloodDonationHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BloodDonationHistoryRepository extends JpaRepository<BloodDonationHistory, Long> {}
