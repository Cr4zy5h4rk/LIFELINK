package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.CampaignStatus;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.CampaignDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.BloodDonationCampaignService;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing campaigns.
 */
@RestController
@CrossOrigin(origins = "*")
public class CampaignController {

    private final BloodDonationCampaignService campaignService;

    @Autowired
    public CampaignController(BloodDonationCampaignService campaignService) {
        this.campaignService = campaignService;
    }

    /**
     * GET /campaigns : get all campaigns.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of campaigns in body
     */
    @GetMapping("/campaigns")
    public ResponseEntity<List<CampaignDTO>> getAllCampaigns() {
        List<CampaignDTO> campaigns = campaignService.findAll();
        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    /**
     * GET /campaigns/:id : get the "id" campaign.
     *
     * @param id the id of the campaign to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the campaign, or with status 404 (Not Found)
     */
    @GetMapping("/campaigns/{id}")
    public ResponseEntity<CampaignDTO> getCampaign(@PathVariable String id) {
        Optional<CampaignDTO> campaign = campaignService.findOne(Long.parseLong(id));
        return campaign.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST /campaigns : Create a new campaign.
     *
     * @param campaignDTO the campaign to create
     * @return the ResponseEntity with status 201 (Created) and with body the new campaign
     */
    @PostMapping("/campaigns")
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignDTO campaignDTO) {
        CampaignDTO result = campaignService.save(campaignDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * PUT /campaigns/:id : Updates an existing campaign.
     *
     * @param id the id of the campaign to update
     * @param campaignDTO the campaign to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated campaign
     */
    @PutMapping("/campaigns/{id}")
    public ResponseEntity<CampaignDTO> updateCampaign(
            @PathVariable String id,
            @RequestBody CampaignDTO campaignDTO) {

        campaignDTO.setId(Long.parseLong(id));
        CampaignDTO result = campaignService.update(campaignDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * DELETE /campaigns/:id : delete the "id" campaign.
     *
     * @param id the id of the campaign to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/campaigns/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable String id) {
        campaignService.delete(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /campaigns/status/:status : get all campaigns with a specific status.
     *
     * @param status the status of the campaigns to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of campaigns in body
     */
    @GetMapping("/campaigns/status/{status}")
    public ResponseEntity<List<CampaignDTO>> getCampaignsByStatus(@PathVariable String status) {
        CampaignStatus campaignStatus = CampaignStatus.valueOf(status.toUpperCase());
        List<CampaignDTO> campaigns = campaignService.findByStatus(campaignStatus);
        return ResponseEntity.ok().body(campaigns);
    }

    /**
     * GET /campaigns/region/:region : get all campaigns in a specific region.
     *
     * @param region the region of the campaigns to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of campaigns in body
     */
    @GetMapping("/campaigns/region/{region}")
    public ResponseEntity<List<CampaignDTO>> getCampaignsByRegion(@PathVariable String region) {
        List<CampaignDTO> campaigns = campaignService.findByRegion(region);
        return ResponseEntity.ok().body(campaigns);
    }

    /**
     * GET /campaigns/active : get all active campaigns (current date is between start and end date).
     *
     * @return the ResponseEntity with status 200 (OK) and the list of active campaigns in body
     */
    @GetMapping("/campaigns/active")
    public ResponseEntity<List<CampaignDTO>> getActiveCampaigns() {
        List<CampaignDTO> campaigns = campaignService.findActiveCampaigns();
        return ResponseEntity.ok().body(campaigns);
    }
}