package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.ShareInfoRequest;

import java.util.List;

/**
 * Spring Data JPA repository for the ShareInfoRequest entity.
 */
@Repository
public interface ShareInfoRequestRepository extends JpaRepository<ShareInfoRequest, Long> {

    /**
     * Find all requests by blood donation campaign id.
     */
    List<ShareInfoRequest> findByBloodDonationCampaignId(Long campaignId);

    /**
     * Find all requests by donation center id.
     */
    List<ShareInfoRequest> findByDonationCenterId(Long centerId);

    /**
     * Find all pending requests (where isAccepted is null).
     */
    List<ShareInfoRequest> findByIsAcceptedIsNull();

    List<ShareInfoRequest> findByIsAcceptedIsFalse();
}