package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * DTO for transferring emergency/blood request data between client and server.
 * This DTO is designed to match the Flutter Emergency model structure.
 */
public class EmergencyDTO implements Serializable {

    private Long id;
    private String title;
    private String hospital;
    private ZonedDateTime date;
    private String bloodType;
    private String wolofAudioUrls;
    private Double latitude;
    private Double longitude;


    // Constructors
    public EmergencyDTO() {
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

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getWolofAudioUrls() {
        return wolofAudioUrls;
    }

    public void setWolofAudioUrls(String wolofAudioUrls) {
        this.wolofAudioUrls = wolofAudioUrls;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmergencyDTO)) {
            return false;
        }

        return id != null && id.equals(((EmergencyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmergencyDTO{" +
                "id=" + getId() +
                ", title='" + getTitle() + "'" +
                ", hospital='" + getHospital() + "'" +
                ", date='" + getDate() + "'" +
                ", bloodType='" + getBloodType() + "'" +
                ", wolofAudioUrls='" + getWolofAudioUrls() + "'" +
                "}";
    }
}