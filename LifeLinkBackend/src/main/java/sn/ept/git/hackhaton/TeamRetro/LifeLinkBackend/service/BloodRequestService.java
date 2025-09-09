package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import java.util.List;
import java.util.Optional;

import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodRequestCreateDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodRequestDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.EmergencyDTO;

/**
 * Service Interface for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodRequest}.
 */
public interface BloodRequestService {

    /**
     * Save a bloodRequest.
     *
     * @param bloodRequestDTO the entity to save.
     * @return the persisted entity.
     */
    BloodRequestCreateDTO save(BloodRequestCreateDTO bloodRequestDTO);

    /**
     * Updates a bloodRequest.
     *
     * @param bloodRequestDTO the entity to update.
     * @return the persisted entity.
     */
    BloodRequestDTO update(BloodRequestDTO bloodRequestDTO);

    /**
     * Partially updates a bloodRequest.
     *
     * @param bloodRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BloodRequestDTO> partialUpdate(BloodRequestDTO bloodRequestDTO);

    /**
     * Get all the bloodRequests.
     *
     * @return the list of entities.
     */
    List<BloodRequestDTO> findAll();

    /**
     * Get the "id" bloodRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BloodRequestDTO> findOne(Long id);

    /**
     * Delete the "id" bloodRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
