package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.EmergencyDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.EmergencyService;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing blood donation emergencies.
 */
@RestController
@CrossOrigin(origins = "*")
public class EmergencyController {

    private final Logger log = LoggerFactory.getLogger(EmergencyController.class);
    private final EmergencyService emergencyService;

    public EmergencyController(EmergencyService emergencyService) {
        this.emergencyService = emergencyService;
    }

    /**
     * GET /emergencies : get all emergencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of emergencies in body
     */
    @GetMapping("/emergencies")
    public ResponseEntity<List<EmergencyDTO>> getAllEmergencies() {
        log.debug("REST request to get all Emergencies");
        List<EmergencyDTO> emergencies = emergencyService.findAll();
        return new ResponseEntity<>(emergencies, HttpStatus.OK);
    }

    @GetMapping("pending/emergencies")
    public ResponseEntity<List<EmergencyDTO>> getAllPendingEmergencies() {
        log.debug("REST request to get all pending Emergencies");
        List<EmergencyDTO> emergencies = emergencyService.findAllPending();
        return new ResponseEntity<>(emergencies, HttpStatus.OK);
    }

    /**
     * GET /emergencies/:id : get the "id" emergency.
     *
     * @param id the id of the emergency to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emergency, or with status 404 (Not Found)
     */
    @GetMapping("/emergencies/{id}")
    public ResponseEntity<EmergencyDTO> getEmergency(@PathVariable String id) {
        log.debug("REST request to get Emergency : {}", id);
        try {
            Long emergencyId = Long.parseLong(id);
            Optional<EmergencyDTO> emergency = emergencyService.findOne(emergencyId);
            return emergency.map(response -> ResponseEntity.ok().body(response))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (NumberFormatException e) {
            log.error("Invalid emergency ID format: {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST /emergencies : Create a new emergency.
     *
     * @param emergencyDTO the emergency to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emergency
     */
    @PostMapping("/emergencies")
    public ResponseEntity<EmergencyDTO> createEmergency(@RequestBody EmergencyDTO emergencyDTO) {
        log.debug("REST request to save Emergency : {}", emergencyDTO);
        EmergencyDTO result = emergencyService.save(emergencyDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * PUT /emergencies/:id : Updates an existing emergency.
     *
     * @param id the id of the emergency to update
     * @param emergencyDTO the emergency to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emergency
     */
    @PutMapping("/emergencies/{id}")
    public ResponseEntity<EmergencyDTO> updateEmergency(
            @PathVariable String id,
            @RequestBody EmergencyDTO emergencyDTO) {

        log.debug("REST request to update Emergency : {}, {}", id, emergencyDTO);
        try {
            Long emergencyId = Long.parseLong(id);
            emergencyDTO.setId(emergencyId);
            EmergencyDTO result = emergencyService.update(emergencyDTO);
            return ResponseEntity.ok().body(result);
        } catch (NumberFormatException e) {
            log.error("Invalid emergency ID format: {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE /emergencies/:id : delete the "id" emergency.
     *
     * @param id the id of the emergency to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/emergencies/{id}")
    public ResponseEntity<Void> deleteEmergency(@PathVariable String id) {
        log.debug("REST request to delete Emergency : {}", id);
        try {
            Long emergencyId = Long.parseLong(id);
            emergencyService.delete(emergencyId);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException e) {
            log.error("Invalid emergency ID format: {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}