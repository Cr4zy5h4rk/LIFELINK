package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * DTO for {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.ShareInfoRequest}
 */
public class ShareInfoRequestDTO implements Serializable {

    private Long id;
    private ZonedDateTime requestDate;
    private ZonedDateTime acceptanceDate;
    private Boolean isAccepted;
    private Long bloodDonationCampaign;
    private Long donationCenter;
    private String phoneNumber ;
    private String sharedInfoSelection;
    private String sharedInfoUser;

    // Constructeurs
    public ShareInfoRequestDTO() {
    }

    public ShareInfoRequestDTO(ZonedDateTime requestDate, ZonedDateTime acceptanceDate,
                               Boolean isAccepted, Long bloodDonationCampaign,
                               Long donationCenter, String phoneNumber, Long id) {
        this.requestDate = requestDate;
        this.acceptanceDate = acceptanceDate;
        this.isAccepted = isAccepted;
        this.bloodDonationCampaign = bloodDonationCampaign;
        this.donationCenter = donationCenter;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public ZonedDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(ZonedDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getSharedInfoSelection() {
        return sharedInfoSelection;
    }
    public void setSharedInfoSelection(String sharedInfoSelection) {
        this.sharedInfoSelection = sharedInfoSelection;
    }

    public String getSharedInfoUser() {
        return sharedInfoUser;
    }

    public void setSharedInfoUser(String sharedInfoUser) {
        this.sharedInfoUser = sharedInfoUser;
    }

    public ZonedDateTime getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(ZonedDateTime acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Long getBloodDonationCampaign() {
        return bloodDonationCampaign;
    }

    public void setBloodDonationCampaign(Long bloodDonationCampaign) {
        this.bloodDonationCampaign = bloodDonationCampaign;
    }

    public Long getDonationCenter() {
        return donationCenter;
    }

    public void setDonationCenter(Long donationCenter) {
        this.donationCenter = donationCenter;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}