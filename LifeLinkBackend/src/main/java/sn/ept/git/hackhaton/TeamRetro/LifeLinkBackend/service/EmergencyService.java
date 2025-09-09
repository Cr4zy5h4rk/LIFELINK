package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.EmergencyDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodRequest}
 * as emergencies for the Flutter application.
 */
public interface EmergencyService {

    /**
     * Save an emergency.
     *
     * @param emergencyDTO the entity to save.
     * @return the persisted entity.
     */
    EmergencyDTO save(EmergencyDTO emergencyDTO);

    /**
     * Update an emergency.
     *
     * @param emergencyDTO the entity to update.
     * @return the persisted entity.
     */
    EmergencyDTO update(EmergencyDTO emergencyDTO);

    /**
     * Get all the emergencies.
     *
     * @return the list of entities.
     */
    List<EmergencyDTO> findAll();

    public List<EmergencyDTO> findAllPending();

    /**
     * Get one emergency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmergencyDTO> findOne(Long id);

    /**
     * Delete the emergency by id.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<EmergencyDTO> getLatestPendingEmergencies();
}