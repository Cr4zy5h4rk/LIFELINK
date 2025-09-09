package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.UserNotification;

/**
 * Spring Data JPA repository for the UserNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {}
