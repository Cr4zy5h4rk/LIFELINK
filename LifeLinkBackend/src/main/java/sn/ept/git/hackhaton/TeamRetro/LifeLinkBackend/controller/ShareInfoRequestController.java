package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.ShareInfoRequest;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.ShareInfoRequestDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.ShareInfoRequestService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.ShareInfoSelectionDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.ShareInfoRequest}.
 */
@RestController
@CrossOrigin(origins = "*")
public class ShareInfoRequestController {

    private final Logger log = LoggerFactory.getLogger(ShareInfoRequestController.class);
    private final ShareInfoRequestService shareInfoRequestService;

    public ShareInfoRequestController(ShareInfoRequestService shareInfoRequestService) {
        this.shareInfoRequestService = shareInfoRequestService;
    }

    /**
     * {@code POST /share-info-requests} : Create a new shareInfoRequest.
     *
     * @param shareInfoRequestDTO the shareInfoRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shareInfoRequestDTO.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/share-info-requests")
    public ResponseEntity<ShareInfoRequestDTO> createShareInfoRequest(@RequestBody ShareInfoRequestDTO shareInfoRequestDTO) throws URISyntaxException {
        log.debug("REST request to save ShareInfoRequest : {}", shareInfoRequestDTO);

        ShareInfoRequestDTO result = shareInfoRequestService.save(shareInfoRequestDTO);
        return ResponseEntity
                .created(new URI("/api/share-info-requests/" + result.getBloodDonationCampaign()))  // Utiliser une autre propriété car pas d'ID
                .body(result);
    }

    /**
     * {@code PUT /share-info-requests/:id} : Updates an existing shareInfoRequest.
     *
     * @param id the id of the shareInfoRequestDTO to update.
     * @param shareInfoRequestDTO the shareInfoRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shareInfoRequestDTO.
     */
    @PutMapping("/share-info-requests/{id}")
    public ResponseEntity<ShareInfoRequestDTO> updateShareInfoRequest(
            @PathVariable Long id,
            @RequestBody ShareInfoRequestDTO shareInfoRequestDTO
    ) {
        log.debug("REST request to update ShareInfoRequest : {}, {}", id, shareInfoRequestDTO);

        if (!shareInfoRequestService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ShareInfoRequestDTO result = shareInfoRequestService.update(id, shareInfoRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET /share-info-requests} : get all the shareInfoRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shareInfoRequests in body.
     */
    @GetMapping("/share-info-requests")
    public ResponseEntity<List<ShareInfoRequestDTO>> getAllShareInfoRequests() {
        log.debug("REST request to get all ShareInfoRequests");
        List<ShareInfoRequestDTO> result = shareInfoRequestService.findAll();
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET /share-info-requests/:id} : get the "id" shareInfoRequest.
     *
     * @param id the id of the shareInfoRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shareInfoRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/share-info-requests/{id}")
    public ResponseEntity<ShareInfoRequestDTO> getShareInfoRequest(@PathVariable Long id) {
        log.debug("REST request to get ShareInfoRequest : {}", id);
        Optional<ShareInfoRequestDTO> result = shareInfoRequestService.findOne(id);
        return result.map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE /share-info-requests/:id} : delete the "id" shareInfoRequest.
     *
     * @param id the id of the shareInfoRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/share-info-requests/{id}")
    public ResponseEntity<Void> deleteShareInfoRequest(@PathVariable Long id) {
        log.debug("REST request to delete ShareInfoRequest : {}", id);
        shareInfoRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * {@code GET /share-info-requests/campaign/:campaignId} : get all shareInfoRequests by campaign id.
     *
     * @param campaignId the id of the campaign.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shareInfoRequests in body.
     */
    @GetMapping("/share-info-requests/campaign/{campaignId}")
    public ResponseEntity<List<ShareInfoRequestDTO>> getShareInfoRequestsByCampaign(@PathVariable Long campaignId) {
        log.debug("REST request to get ShareInfoRequests by campaign : {}", campaignId);
        List<ShareInfoRequestDTO> result = shareInfoRequestService.findByCampaignId(campaignId);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET /share-info-requests/center/:centerId} : get all shareInfoRequests by center id.
     *
     * @param centerId the id of the donation center.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shareInfoRequests in body.
     */
    @GetMapping("/share-info-requests/center/{centerId}")
    public ResponseEntity<List<ShareInfoRequestDTO>> getShareInfoRequestsByCenter(@PathVariable Long centerId) {
        log.debug("REST request to get ShareInfoRequests by center : {}", centerId);
        List<ShareInfoRequestDTO> result = shareInfoRequestService.findByCenterId(centerId);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET /share-info-requests/pending} : get all pending shareInfoRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pending shareInfoRequests in body.
     */
    @GetMapping("/share-info-requests/pending")
    public ResponseEntity<List<ShareInfoRequestDTO>> getPendingShareInfoRequests() {
        log.debug("REST request to get pending ShareInfoRequests");
        List<ShareInfoRequestDTO> result = shareInfoRequestService.findPendingRequests();
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET /share-info-requests/rejected} : get all rejected shareInfoRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rejected shareInfoRequests in body.
     */
    @GetMapping("/share-info-requests/rejected")
    public ResponseEntity<List<ShareInfoRequestDTO>> getRejectedShareInfoRequests() {
        log.debug("REST request to get rejected ShareInfoRequests");
        List<ShareInfoRequestDTO> result = shareInfoRequestService.findRejectedRequests();
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT /share-info-requests/:id/accept} : Accept a shareInfoRequest.
     *
     * @param id the id of the shareInfoRequest to accept.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shareInfoRequestDTO.
     */
    @PutMapping("/share-info-requests/{id}/accept")
    public ResponseEntity<ShareInfoRequestDTO> acceptShareInfoRequest(
            @PathVariable Long id,
            @RequestBody ShareInfoSelectionDTO selectionDTO
    ) {
        log.debug("REST request to accept ShareInfoRequest : {}", id);
        if (!shareInfoRequestService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ShareInfoRequestDTO result = shareInfoRequestService.acceptRequest(id);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT /share-info-requests/:id/reject} : Reject a shareInfoRequest.
     *
     * @param id the id of the shareInfoRequest to reject.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shareInfoRequestDTO.
     */
    @PutMapping("/share-info-requests/{id}/reject")
    public ResponseEntity<ShareInfoRequestDTO> rejectShareInfoRequest(@PathVariable Long id) {
        log.debug("REST request to reject ShareInfoRequest : {}", id);
        if (!shareInfoRequestService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ShareInfoRequestDTO result = shareInfoRequestService.rejectRequest(id);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST /share-info-requests/phone} : Create a new shareInfoRequest with phone number.
     *
     * @param phoneNumber the donor's phone number
     * @param campaignId the campaign ID (optional)
     * @param centerId the center ID (optional)
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shareInfoRequestDTO.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/share-info-requests/phone")
    public ResponseEntity<ShareInfoRequestDTO> createShareInfoRequestWithPhone(
            @RequestParam String phoneNumber,
            @RequestParam(required = false) Long campaignId,
            @RequestParam(required = false) Long centerId,
            @RequestBody ShareInfoSelectionDTO selectionDTO) throws URISyntaxException {

        log.debug("REST request to create ShareInfoRequest with phone: {}, campaignId: {}, centerId: {}",
                phoneNumber, campaignId, centerId);

        try {
            ShareInfoRequestDTO result = shareInfoRequestService.createRequest(phoneNumber, campaignId, centerId, selectionDTO);
            return ResponseEntity
                    .created(new URI("/api/share-info-requests/phone"))
                    .body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

    }

}