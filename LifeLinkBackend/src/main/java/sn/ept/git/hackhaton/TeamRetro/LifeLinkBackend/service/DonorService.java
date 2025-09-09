package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.EsignetDTO;

/**
 * Service Interface for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor}.
 */
public interface DonorService {
    /**
     * Save a donor.
     *
     * @param donorDTO the entity to save.
     * @return the persisted entity.
     */
    DonorDTO save(DonorDTO donorDTO);

    DonorDTO saveEsignet(EsignetDTO donorDTO);

    /**
     * Updates a donor.
     *
     * @param donorDTO the entity to update.
     * @return the persisted entity.
     */
    DonorDTO update(DonorDTO donorDTO);

    /**
     * Partially updates a donor.
     *
     * @param donorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DonorDTO> partialUpdate(DonorDTO donorDTO);

    /**
     * Get all the donors.
     *
     * @return the list of entities.
     */
    List<DonorDTO> findAll();

    /**
     * Get all the donors with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonorDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" donor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonorDTO> findOne(String id);

    /**
     * Delete the "id" donor.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
