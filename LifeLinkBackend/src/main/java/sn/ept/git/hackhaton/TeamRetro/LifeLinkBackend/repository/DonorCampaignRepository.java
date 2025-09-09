package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonorCampaign;

/**
 * Spring Data JPA repository for the DonorCampaign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonorCampaignRepository extends JpaRepository<DonorCampaign, Long> {}
