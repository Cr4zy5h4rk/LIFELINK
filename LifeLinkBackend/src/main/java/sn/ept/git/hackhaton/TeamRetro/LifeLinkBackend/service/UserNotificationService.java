package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import java.util.List;
import java.util.Optional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.UserNotificationDTO;

/**
 * Service Interface for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.UserNotification}.
 */
public interface UserNotificationService {
    /**
     * Save a userNotification.
     *
     * @param userNotificationDTO the entity to save.
     * @return the persisted entity.
     */
    UserNotificationDTO save(UserNotificationDTO userNotificationDTO);

    /**
     * Updates a userNotification.
     *
     * @param userNotificationDTO the entity to update.
     * @return the persisted entity.
     */
    UserNotificationDTO update(UserNotificationDTO userNotificationDTO);

    /**
     * Partially updates a userNotification.
     *
     * @param userNotificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserNotificationDTO> partialUpdate(UserNotificationDTO userNotificationDTO);

    /**
     * Get all the userNotifications.
     *
     * @return the list of entities.
     */
    List<UserNotificationDTO> findAll();

    /**
     * Get the "id" userNotification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserNotificationDTO> findOne(Long id);

    /**
     * Delete the "id" userNotification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
