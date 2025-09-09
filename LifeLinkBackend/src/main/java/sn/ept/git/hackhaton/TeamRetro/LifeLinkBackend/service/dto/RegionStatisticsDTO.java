package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import java.io.Serializable;
import java.util.Objects;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.StockStatus;

/**
 * A DTO for the {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.RegionStatistics} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegionStatisticsDTO implements Serializable {

    private Long id;

    private StockStatus stockStatus;

    private RegionDTO region;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StockStatus getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(StockStatus stockStatus) {
        this.stockStatus = stockStatus;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegionStatisticsDTO)) {
            return false;
        }

        RegionStatisticsDTO regionStatisticsDTO = (RegionStatisticsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, regionStatisticsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegionStatisticsDTO{" +
            "id=" + getId() +
            ", stockStatus='" + getStockStatus() + "'" +
            ", region=" + getRegion() +
            "}";
    }
}
