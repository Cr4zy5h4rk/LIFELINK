package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import java.util.List;
import java.util.Optional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodDonationHistoryDTO;

/**
 * Service Interface for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationHistory}.
 */
public interface BloodDonationHistoryService {
    /**
     * Save a bloodDonationHistory.
     *
     * @param bloodDonationHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    BloodDonationHistoryDTO save(BloodDonationHistoryDTO bloodDonationHistoryDTO);

    /**
     * Updates a bloodDonationHistory.
     *
     * @param bloodDonationHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    BloodDonationHistoryDTO update(BloodDonationHistoryDTO bloodDonationHistoryDTO);

    /**
     * Partially updates a bloodDonationHistory.
     *
     * @param bloodDonationHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BloodDonationHistoryDTO> partialUpdate(BloodDonationHistoryDTO bloodDonationHistoryDTO);

    /**
     * Get all the bloodDonationHistories.
     *
     * @return the list of entities.
     */
    List<BloodDonationHistoryDTO> findAll();

    /**
     * Get the "id" bloodDonationHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BloodDonationHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" bloodDonationHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
