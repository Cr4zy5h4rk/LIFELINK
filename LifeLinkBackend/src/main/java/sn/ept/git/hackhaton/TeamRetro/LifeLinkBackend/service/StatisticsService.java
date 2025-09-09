package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.StatisticsDTO;

/**
 * Service Interface for managing statistics.
 */
public interface StatisticsService {

    /**
     * Get the current statistics.
     * Returns the statistics for the current year, or the latest available if none exists for the current year.
     *
     * @return the statistics
     */
    StatisticsDTO getCurrentStatistics();

    /**
     * Update the statistics.
     *
     * @param statisticsDTO the statistics to update
     * @return the updated statistics
     */
    StatisticsDTO updateStatistics(StatisticsDTO statisticsDTO);
}