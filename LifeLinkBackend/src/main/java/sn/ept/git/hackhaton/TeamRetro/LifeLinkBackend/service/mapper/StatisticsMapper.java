package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.springframework.stereotype.Service;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Statistics;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.StatisticsDTO;

/**
 * Mapper for converting between {@link Statistics} and {@link StatisticsDTO}.
 */
@Service
public class StatisticsMapper {

    /**
     * Convert a Statistics entity to a StatisticsDTO.
     *
     * @param statistics the entity to convert
     * @return the DTO
     */
    public StatisticsDTO toDto(Statistics statistics) {
        if (statistics == null) {
            return null;
        }

        StatisticsDTO dto = new StatisticsDTO();

        // Map number of donors
        dto.setDonorsCount(statistics.getNumberOfDonors());

        // Map lives saved
        dto.setLivesSaved(statistics.getNumberOfLifeSaved());

        // Calculate objective percentage
        if (statistics.getTargetBloodBags() != null && statistics.getTargetBloodBags() > 0) {
            // Assuming there's some relationship between donors and blood bags
            // This calculation might need to be adjusted based on your business logic
            double percentage = (double) statistics.getNumberOfDonors() / statistics.getTargetBloodBags() * 100;
            dto.setObjectivePercentage(Math.min(percentage, 100.0)); // Cap at 100%
        } else {
            dto.setObjectivePercentage(0.0);
        }

        return dto;
    }

    /**
     * Update a Statistics entity from a StatisticsDTO.
     *
     * @param entity the entity to update
     * @param dto the source DTO
     * @return the updated entity
     */
    public Statistics updateEntityFromDto(Statistics entity, StatisticsDTO dto) {
        if (entity == null || dto == null) {
            return entity;
        }

        // Update number of donors
        if (dto.getDonorsCount() != null) {
            entity.setNumberOfDonors(dto.getDonorsCount());
        }

        // Update lives saved
        if (dto.getLivesSaved() != null) {
            entity.setNumberOfLifeSaved(dto.getLivesSaved());
        }

        // We don't update targetBloodBags from the DTO since it doesn't contain this field
        // If needed, this would be handled separately

        return entity;
    }
}