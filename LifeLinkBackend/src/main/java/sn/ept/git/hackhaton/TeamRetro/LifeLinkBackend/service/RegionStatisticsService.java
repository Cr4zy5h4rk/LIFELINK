package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import java.util.List;
import java.util.Optional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.RegionStatisticsDTO;

/**
 * Service Interface for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.RegionStatistics}.
 */
public interface RegionStatisticsService {
    /**
     * Save a regionStatistics.
     *
     * @param regionStatisticsDTO the entity to save.
     * @return the persisted entity.
     */
    RegionStatisticsDTO save(RegionStatisticsDTO regionStatisticsDTO);

    /**
     * Updates a regionStatistics.
     *
     * @param regionStatisticsDTO the entity to update.
     * @return the persisted entity.
     */
    RegionStatisticsDTO update(RegionStatisticsDTO regionStatisticsDTO);

    /**
     * Partially updates a regionStatistics.
     *
     * @param regionStatisticsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegionStatisticsDTO> partialUpdate(RegionStatisticsDTO regionStatisticsDTO);

    /**
     * Get all the regionStatistics.
     *
     * @return the list of entities.
     */
    List<RegionStatisticsDTO> findAll();

    /**
     * Get the "id" regionStatistics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegionStatisticsDTO> findOne(Long id);

    /**
     * Delete the "id" regionStatistics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
