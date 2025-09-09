package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodRequest;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodRequestStatus;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.EmergencyDTO;

import java.util.List;

/**
 * Spring Data JPA repository for the BloodRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {


    List<BloodRequest> findTop10ByStatusOrderByCreatedAtDesc(BloodRequestStatus bloodRequestStatus);
    List<BloodRequest> findByStatusOrderByCreatedAtDesc(BloodRequestStatus bloodRequestStatus);

}
