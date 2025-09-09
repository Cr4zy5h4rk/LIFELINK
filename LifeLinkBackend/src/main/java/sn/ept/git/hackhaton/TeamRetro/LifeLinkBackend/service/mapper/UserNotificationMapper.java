package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Notification;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.UserNotification;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.NotificationDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.UserNotificationDTO;

/**
 * Mapper for the entity {@link UserNotification} and its DTO {@link UserNotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserNotificationMapper extends EntityMapper<UserNotificationDTO, UserNotification> {
    @Mapping(target = "donor", source = "donor", qualifiedByName = "donorId")
    @Mapping(target = "notification", source = "notification", qualifiedByName = "notificationId")
    UserNotificationDTO toDto(UserNotification s);

    @Named("donorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonorDTO toDtoDonorId(Donor donor);

    @Named("notificationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NotificationDTO toDtoNotificationId(Notification notification);
}
