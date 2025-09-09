package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for transferring statistics data between client and server.
 * This DTO is designed to match the Flutter Statistics model structure.
 */
public class StatisticsDTO implements Serializable {

    private Integer donorsCount;
    private Double objectivePercentage;
    private Integer livesSaved;

    // Constructors
    public StatisticsDTO() {
        // Empty constructor needed for Jackson
    }

    public StatisticsDTO(Integer donorsCount, Double objectivePercentage, Integer livesSaved) {
        this.donorsCount = donorsCount;
        this.objectivePercentage = objectivePercentage;
        this.livesSaved = livesSaved;
    }

    // Getters and setters
    public Integer getDonorsCount() {
        return donorsCount;
    }

    public void setDonorsCount(Integer donorsCount) {
        this.donorsCount = donorsCount;
    }

    public Double getObjectivePercentage() {
        return objectivePercentage;
    }

    public void setObjectivePercentage(Double objectivePercentage) {
        this.objectivePercentage = objectivePercentage;
    }

    public Integer getLivesSaved() {
        return livesSaved;
    }

    public void setLivesSaved(Integer livesSaved) {
        this.livesSaved = livesSaved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatisticsDTO that)) {
            return false;
        }

        if (!Objects.equals(donorsCount, that.donorsCount)) {
            return false;
        }
        if (!Objects.equals(objectivePercentage, that.objectivePercentage)) {
            return false;
        }
        return Objects.equals(livesSaved, that.livesSaved);
    }

    @Override
    public int hashCode() {
        int result = donorsCount != null ? donorsCount.hashCode() : 0;
        result = 31 * result + (objectivePercentage != null ? objectivePercentage.hashCode() : 0);
        result = 31 * result + (livesSaved != null ? livesSaved.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StatisticsDTO{" +
                "donorsCount=" + donorsCount +
                ", objectivePercentage=" + objectivePercentage +
                ", livesSaved=" + livesSaved +
                '}';
    }
}