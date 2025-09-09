package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.EligibilityStatus;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.ShareInfoRequestDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.ShareInfoSelectionDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.ShareInfoRequest}.
 */
public interface ShareInfoRequestService {

    /**
     * Save a shareInfoRequest.
     *
     * @param shareInfoRequestDTO the entity to save.
     * @return the persisted entity.
     */
    ShareInfoRequestDTO save(ShareInfoRequestDTO shareInfoRequestDTO);

    /**
     * Updates a shareInfoRequest.
     *
     * @param id the id of the entity to update.
     * @param shareInfoRequestDTO the entity to update.
     * @return the persisted entity.
     */
    ShareInfoRequestDTO update(Long id, ShareInfoRequestDTO shareInfoRequestDTO);

    /**
     * Get all the shareInfoRequests.
     *
     * @return the list of entities.
     */
    List<ShareInfoRequestDTO> findAll();

    /**
     * Get the "id" shareInfoRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShareInfoRequestDTO> findOne(Long id);

    /**
     * Delete the "id" shareInfoRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Find all shareInfoRequests by blood donation campaign id.
     *
     * @param campaignId the id of the blood donation campaign.
     * @return the list of entities.
     */
    List<ShareInfoRequestDTO> findByCampaignId(Long campaignId);

    /**
     * Find all shareInfoRequests by donation center id.
     *
     * @param centerId the id of the donation center.
     * @return the list of entities.
     */
    List<ShareInfoRequestDTO> findByCenterId(Long centerId);

    /**
     * Find all pending (not yet accepted/rejected) shareInfoRequests.
     *
     * @return the list of entities.
     */
    List<ShareInfoRequestDTO> findPendingRequests();

    List<ShareInfoRequestDTO> findRejectedRequests();

    /**
     * Accept a shareInfoRequest.
     *
     * @param id the id of the entity to accept.
     * @return the updated entity.
     */
    ShareInfoRequestDTO acceptRequest(Long id);

    /**
     * Reject a shareInfoRequest.
     *
     * @param id the id of the entity to reject.
     * @return the updated entity.
     */
    ShareInfoRequestDTO rejectRequest(Long id);

//    ShareInfoRequestDTO acceptRequestWithSelection(Long id, ShareInfoSelectionDTO selectionDTO);

    /**
     * Create a shareInfoRequest with phone number and campaign or center ID.
     * Either campaignId or centerId must be provided, but not both.
     *
     * @param phoneNumber the donor's phone number
     * @param campaignId the blood donation campaign ID (can be null)
     * @param centerId the donation center ID (can be null)
     * @return the persisted entity.
     * @throws IllegalArgumentException if both campaignId and centerId are null or both are provided
     */
    ShareInfoRequestDTO createRequest(String phoneNumber, Long campaignId, Long centerId, ShareInfoSelectionDTO selectionDTO);


}