package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ShareInfoRequest.
 */
@Entity
@Table(name = "share_info_request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShareInfoRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shareInfosequenceGenerator")
    @SequenceGenerator(name = "shareInfosequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "request_date")
    private ZonedDateTime requestDate = ZonedDateTime.now();

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "acceptance_date")
    private ZonedDateTime acceptanceDate;

    @Column(name = "is_accepted")
    private Boolean isAccepted = false;

    @Column(name = "shared_info_selection", columnDefinition = "TEXT")
    private String sharedInfoSelection;

    @Column(name = "shared_info_user", columnDefinition = "TEXT")
    private String sharedInfoUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private BloodDonationCampaign bloodDonationCampaign;

    @ManyToOne(fetch = FetchType.LAZY)
    private DonationCenter donationCenter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(ZonedDateTime requestDate) {
        this.requestDate = requestDate;
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

    public BloodDonationCampaign getBloodDonationCampaign() {
        return bloodDonationCampaign;
    }

    public void setBloodDonationCampaign(BloodDonationCampaign bloodDonationCampaign) {
        this.bloodDonationCampaign = bloodDonationCampaign;
    }

    public DonationCenter getDonationCenter() {
        return donationCenter;
    }

    public void setDonationCenter(DonationCenter donationCenter) {
        this.donationCenter = donationCenter;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShareInfoRequest that = (ShareInfoRequest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(requestDate, that.requestDate) &&
                Objects.equals(acceptanceDate, that.acceptanceDate) &&
                Objects.equals(isAccepted, that.isAccepted) &&
                Objects.equals(sharedInfoSelection, that.sharedInfoSelection) &&
                Objects.equals(bloodDonationCampaign, that.bloodDonationCampaign) &&
                Objects.equals(donationCenter, that.donationCenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestDate, acceptanceDate, isAccepted, sharedInfoSelection, bloodDonationCampaign, donationCenter);
    }

    @Override
    public String toString() {
        return "ShareInfoRequest{" +
                "id=" + id +
                ", requestDate=" + requestDate +
                ", acceptanceDate=" + acceptanceDate +
                ", isAccepted=" + isAccepted +
                ", sharedInfoSelection='" + (sharedInfoSelection != null ? "[Data Present]" : "null") + "'" +
                ", bloodDonationCampaign=" + bloodDonationCampaign +
                ", donationCenter=" + donationCenter +
                '}';
    }
}