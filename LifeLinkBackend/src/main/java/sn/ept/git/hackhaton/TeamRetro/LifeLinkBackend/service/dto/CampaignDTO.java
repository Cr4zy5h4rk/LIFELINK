package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.CampaignStatus;

/**
 * DTO for transferring campaign data between client and server.
 * This DTO is designed to match the Flutter Campaign model structure.
 */
public class CampaignDTO implements Serializable {

    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String location;
    private String contactPhone;
    private String schedule;
    private CampaignStatus status;

    // Constructors
    public CampaignDTO() {
        // Empty constructor needed for Jackson
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignDTO)) {
            return false;
        }

        return id != null && id.equals(((CampaignDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CampaignDTO{" +
                "id=" + getId() +
                ", title='" + getTitle() + "'" +
                ", description='" + getDescription() + "'" +
                ", imageUrl='" + getImageUrl() + "'" +
                ", startDate='" + getStartDate() + "'" +
                ", endDate='" + getEndDate() + "'" +
                ", location='" + getLocation() + "'" +
                ", contactPhone='" + getContactPhone() + "'" +
                ", schedule='" + getSchedule() + "'" +
                ", status='" + getStatus() + "'" +
                "}";
    }
}