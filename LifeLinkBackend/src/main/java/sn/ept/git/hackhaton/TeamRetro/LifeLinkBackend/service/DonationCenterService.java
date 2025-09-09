package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import java.util.List;
import java.util.Optional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonationCenterDTO;

/**
 * Service Interface for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter}.
 */
public interface DonationCenterService {
    /**
     * Save a donationCenter.
     *
     * @param donationCenterDTO the entity to save.
     * @return the persisted entity.
     */
    DonationCenterDTO save(DonationCenterDTO donationCenterDTO);

    /**
     * Updates a donationCenter.
     *
     * @param donationCenterDTO the entity to update.
     * @return the persisted entity.
     */
    DonationCenterDTO update(DonationCenterDTO donationCenterDTO);

    /**
     * Partially updates a donationCenter.
     *
     * @param donationCenterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DonationCenterDTO> partialUpdate(DonationCenterDTO donationCenterDTO);

    /**
     * Get all the donationCenters.
     *
     * @return the list of entities.
     */
    List<DonationCenterDTO> findAll();

    /**
     * Get the "id" donationCenter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonationCenterDTO> findOne(Long id);

    /**
     * Delete the "id" donationCenter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
