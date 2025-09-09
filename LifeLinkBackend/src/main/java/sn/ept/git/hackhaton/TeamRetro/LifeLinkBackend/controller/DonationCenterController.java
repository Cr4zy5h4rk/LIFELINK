package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.DonationCenterService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonationCenterDTO;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter}.
 */
@RestController
@CrossOrigin(origins = "*")
public class DonationCenterController {

    private final Logger log = LoggerFactory.getLogger(DonationCenterController.class);
    private final DonationCenterService donationCenterService;

    @Autowired
    public DonationCenterController(DonationCenterService donationCenterService) {
        this.donationCenterService = donationCenterService;
    }

    /**
     * {@code POST /donation-centers} : Create a new donation center.
     *
     * @param donationCenterDTO the donation center to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donationCenterDTO.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donation-centers")
    public ResponseEntity<DonationCenterDTO> createDonationCenter(@Valid @RequestBody DonationCenterDTO donationCenterDTO) throws URISyntaxException {
        log.debug("REST request to save DonationCenter : {}", donationCenterDTO);
        if (donationCenterDTO.getId() != null) {
            return ResponseEntity.badRequest().body(null);
        }
        DonationCenterDTO result = donationCenterService.save(donationCenterDTO);
        return ResponseEntity
                .created(new URI("/api/donation-centers/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT /donation-centers/:id} : Updates an existing donation center.
     *
     * @param id the id of the donation center to update.
     * @param donationCenterDTO the donation center to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationCenterDTO.
     */
    @PutMapping("/donation-centers/{id}")
    public ResponseEntity<DonationCenterDTO> updateDonationCenter(
            @PathVariable Long id,
            @Valid @RequestBody DonationCenterDTO donationCenterDTO
    ) {
        log.debug("REST request to update DonationCenter : {}, {}", id, donationCenterDTO);
        if (donationCenterDTO.getId() == null || !id.equals(donationCenterDTO.getId())) {
            return ResponseEntity.badRequest().body(null);
        }

        if (!donationCenterService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        DonationCenterDTO result = donationCenterService.update(donationCenterDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * {@code GET /donation-centers} : get all the donation centers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donation centers in body.
     */
    @GetMapping("/donation-centers")
    public ResponseEntity<List<DonationCenterDTO>> getAllDonationCenters() {
        log.debug("REST request to get all DonationCenters");
        List<DonationCenterDTO> centers = donationCenterService.findAll();
        return ResponseEntity.ok(centers);
    }

    /**
     * {@code GET /donation-centers/:id} : get the "id" donation center.
     *
     * @param id the id of the donation center to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationCenterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donation-centers/{id}")
    public ResponseEntity<DonationCenterDTO> getDonationCenter(@PathVariable Long id) {
        log.debug("REST request to get DonationCenter : {}", id);
        Optional<DonationCenterDTO> donationCenterDTO = donationCenterService.findOne(id);
        return donationCenterDTO.map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE /donation-centers/:id} : delete the "id" donation center.
     *
     * @param id the id of the donation center to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donation-centers/{id}")
    public ResponseEntity<Void> deleteDonationCenter(@PathVariable Long id) {
        log.debug("REST request to delete DonationCenter : {}", id);
        donationCenterService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    /**
//     * {@code GET /donation-centers/search/{keyword}} : search donation centers by name.
//     *
//     * @param keyword the keyword to search in center names.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matching donation centers in body.
//     */
//    @GetMapping("/donation-centers/search/{keyword}")
//    public ResponseEntity<List<DonationCenterDTO>> searchDonationCenters(@PathVariable String keyword) {
//        log.debug("REST request to search DonationCenters with keyword: {}", keyword);
//        List<DonationCenterDTO> centers = donationCenterService.searchByName(keyword);
//        return ResponseEntity.ok(centers);
//    }

    /**
     * {@code GET /donation-centers/by-address/{addressId}} : get donation centers by address id.
     *
     * @param addressId the id of the address to search by.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matching donation centers in body.
     */
//    @GetMapping("/donation-centers/by-address/{addressId}")
//    public ResponseEntity<List<DonationCenterDTO>> getDonationCentersByAddress(@PathVariable Long addressId) {
//        log.debug("REST request to get DonationCenters by address ID: {}", addressId);
//        List<DonationCenterDTO> centers = donationCenterService.findByAddressId(addressId);
//        return ResponseEntity.ok(centers);
//    }
}