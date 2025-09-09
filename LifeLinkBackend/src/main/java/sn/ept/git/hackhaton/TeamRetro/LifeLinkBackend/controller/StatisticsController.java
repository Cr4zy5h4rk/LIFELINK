package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.StatisticsDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.StatisticsService;

/**
 * REST controller for managing statistics.
 */
@RestController
@CrossOrigin(origins = "*")
public class StatisticsController {

    private final Logger log = LoggerFactory.getLogger(StatisticsController.class);
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * GET /statistics : get the current statistics.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<StatisticsDTO> getStatistics() {
        log.debug("REST request to get statistics");
        StatisticsDTO statistics = statisticsService.getCurrentStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * PUT /statistics : update the statistics.
     *
     * @param statisticsDTO the statistics to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated statistics
     */
    @PutMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatisticsDTO> updateStatistics(@RequestBody StatisticsDTO statisticsDTO) {
        log.debug("REST request to update statistics : {}", statisticsDTO);
        StatisticsDTO result = statisticsService.updateStatistics(statisticsDTO);
        return ResponseEntity.ok(result);
    }
}