package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.UserNotification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserNotificationDTO implements Serializable {

    private Long id;

    private Boolean isRead;

    private ZonedDateTime sentAt;

    private DonorDTO donor;

    private NotificationDTO notification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public ZonedDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(ZonedDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public DonorDTO getDonor() {
        return donor;
    }

    public void setDonor(DonorDTO donor) {
        this.donor = donor;
    }

    public NotificationDTO getNotification() {
        return notification;
    }

    public void setNotification(NotificationDTO notification) {
        this.notification = notification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserNotificationDTO)) {
            return false;
        }

        UserNotificationDTO userNotificationDTO = (UserNotificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userNotificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserNotificationDTO{" +
            "id=" + getId() +
            ", isRead='" + getIsRead() + "'" +
            ", sentAt='" + getSentAt() + "'" +
            ", donor=" + getDonor() +
            ", notification=" + getNotification() +
            "}";
    }
}
