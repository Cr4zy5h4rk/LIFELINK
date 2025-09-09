package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Statistics;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.StatisticsService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.StatisticsDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.StatisticsRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.StatisticsMapper;

import java.time.Year;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Statistics}.
 */
@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    private final Logger log = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    private final StatisticsRepository statisticsRepository;
    private final StatisticsMapper statisticsMapper;

    public StatisticsServiceImpl(
            StatisticsRepository statisticsRepository,
            StatisticsMapper statisticsMapper) {
        this.statisticsRepository = statisticsRepository;
        this.statisticsMapper = statisticsMapper;
    }

    /**
     * Get the current statistics.
     * Returns the statistics for the current year, or the latest available if none exists for the current year.
     *
     * @return the statistics
     */
    @Transactional(readOnly = true)
    public StatisticsDTO getCurrentStatistics() {
        log.debug("Request to get current statistics");

        // Try to get statistics for the current year
        int currentYear = Year.now().getValue();
        Optional<Statistics> currentYearStats = statisticsRepository.findById((long) currentYear);

        if (currentYearStats.isPresent()) {
            return statisticsMapper.toDto(currentYearStats.get());
        }

        // If no statistics for current year, get the latest available
        Optional<Statistics> latestStats = statisticsRepository.findLatest();

        return latestStats.map(statisticsMapper::toDto)
                .orElse(new StatisticsDTO(0, 0.0, 0)); // Default values if no statistics found
    }

    /**
     * Update the statistics.
     *
     * @param statisticsDTO the statistics to update
     * @return the updated statistics
     */
    public StatisticsDTO updateStatistics(StatisticsDTO statisticsDTO) {
        log.debug("Request to update statistics: {}", statisticsDTO);

        // Get the current year for the ID
        int currentYear = Year.now().getValue();

        // Try to find existing statistics for the current year
        Optional<Statistics> existingStats = statisticsRepository.findById((long) currentYear);

        Statistics statistics;
        if (existingStats.isPresent()) {
            // Update existing statistics
            statistics = existingStats.get();
            statistics = statisticsMapper.updateEntityFromDto(statistics, statisticsDTO);
        } else {
            // Create new statistics for the current year
            statistics = new Statistics();
            statistics.setId((long) currentYear);
            statistics = statisticsMapper.updateEntityFromDto(statistics, statisticsDTO);

            // Set default target if not already set
            if (statistics.getTargetBloodBags() == null) {
                statistics.setTargetBloodBags(100000); // Default target, adjust as needed
            }
        }

        // Save the statistics
        statistics = statisticsRepository.save(statistics);

        return statisticsMapper.toDto(statistics);
    }
}