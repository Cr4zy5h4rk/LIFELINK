package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodRequest;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.EmergencyDTO;

/**
 * Mapper interface for converting between {@link BloodRequest} and {@link EmergencyDTO}.
 */
public interface EmergencyMapper {

    /**
     * Convert a BloodRequest entity to an EmergencyDTO.
     *
     * @param bloodRequest the entity to convert
     * @return the DTO
     */
    EmergencyDTO toDto(BloodRequest bloodRequest);

    /**
     * Convert an EmergencyDTO to a BloodRequest entity.
     *
     * @param emergencyDTO the DTO to convert
     * @return the entity
     */
    BloodRequest toEntity(EmergencyDTO emergencyDTO);
}