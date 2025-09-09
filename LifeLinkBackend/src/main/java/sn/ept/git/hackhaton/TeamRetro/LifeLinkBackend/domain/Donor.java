package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import java.io.Serializable;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodType;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.Gender;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ResusType;

/**
 * A Donor.
 */
@Entity
@Table(name = "donor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Donor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "picture", length = 1000)
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birth_date")
    private ZonedDateTime birthDate;

    @Column(name = "password")
    private String password;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "size")
    private Double size;

    @Column(name = "fidelity_points")
    private Integer fidelityPoints;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    private BloodType bloodType;

    @Enumerated(EnumType.STRING)
    @Column(name = "resus_type")
    private ResusType resusType;

    @Column(name = "receive_otp_for_blood_request")
    private Boolean receiveOtpForBloodRequest = false;

    @Column(name = "last_donation_date")
    private ZonedDateTime lastDonationDate;

    // Unique JSON field to store all medical information
    @Column(name = "medical_data", columnDefinition = "TEXT")
    private String medicalData;

    // Original relationships
    @JsonIgnoreProperties(value = { "region", "donor", "donationCenters" }, allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Address address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "rel_donor__favorites",
            joinColumns = @JoinColumn(name = "donor_id"),
            inverseJoinColumns = @JoinColumn(name = "favorites_id")
    )
    @JsonIgnoreProperties(value = { "donor", "favoritedBies" }, allowSetters = true)
    private Set<Article> favorites = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_donor__roles", joinColumns = @JoinColumn(name = "donor_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
    @JsonIgnoreProperties(value = { "donors" }, allowSetters = true)
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
            value = { "organizationPartner", "donors", "address", "bloodRequests", "bloodDonationHistories" },
            allowSetters = true
    )
    private DonationCenter medicalStaff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "createdBies", "canceledBies", "donationCenter" }, allowSetters = true)
    private BloodRequest createdBloodRequests;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "createdBies", "canceledBies", "donationCenter" }, allowSetters = true)
    private BloodRequest canceledBloodRequests;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "donors", "donationCenter" }, allowSetters = true)
    private OrganizationPartner employees;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "donor")
    @JsonIgnoreProperties(value = { "donor", "bloodDonationCampaign", "donationCenter" }, allowSetters = true)
    private Set<BloodDonationHistory> bloodDonationHistories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "donor")
    @JsonIgnoreProperties(value = { "donor", "favoritedBies" }, allowSetters = true)
    private Set<Article> createdBies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "donor")
    @JsonIgnoreProperties(value = { "donor", "bloodDonationCampaign" }, allowSetters = true)
    private Set<DonorCampaign> donorCampaigns = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "donor")
    @JsonIgnoreProperties(value = { "donor", "notification" }, allowSetters = true)
    private Set<UserNotification> userNotifications = new HashSet<>();

    // Getters and setters for medical data
    public String getMedicalData() {
        return medicalData;
    }

    public void setMedicalData(String medicalData) {
        this.medicalData = medicalData;
    }

    public Donor medicalData(String medicalData) {
        this.setMedicalData(medicalData);
        return this;
    }

    // Helper methods for working with JSON
    public JsonNode getMedicalDataAsJson() {
        return stringToJson(this.medicalData);
    }

    public void setMedicalDataFromJson(JsonNode jsonNode) {
        this.medicalData = jsonNodeToString(jsonNode);
    }

    // Method to get a specific field from the medical data
    @Transient
    public JsonNode getMedicalField(String fieldName) {
        JsonNode root = getMedicalDataAsJson();
        if (root != null && root.has(fieldName)) {
            return root.get(fieldName);
        }
        return null;
    }

    // Utility methods for JSON conversion
    private JsonNode stringToJson(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(jsonString);
        } catch (IOException e) {
            throw new RuntimeException("Error converting string to JSON", e);
        }
    }

    private String jsonNodeToString(JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(jsonNode);
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to string", e);
        }
    }

    // Convenience methods for accessing medical information
    @Transient
    public JsonNode getInfosMedicales() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("infosMedicales") : null;
    }

    @Transient
    public JsonNode getVaccinations() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("vaccinations") : null;
    }

    @Transient
    public JsonNode getConsultations() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("consultations") : null;
    }

    @Transient
    public JsonNode getContactsUrgence() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("contactsUrgence") : null;
    }

    @Transient
    public JsonNode getAssurance() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("assurance") : null;
    }

    @Transient
    public JsonNode getModeDeVie() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("modeDeVie") : null;
    }

    @Transient
    public JsonNode getAppareilsMedicaux() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("appareilsMedicaux") : null;
    }

    @Transient
    public JsonNode getDonOrganes() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("donOrganes") : null;
    }

    @Transient
    public JsonNode getDonSanguin() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("donSanguin") : null;
    }

    @Transient
    public JsonNode getJournauxAcces() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("journauxAcces") : null;
    }

    @Transient
    public JsonNode getConsentements() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("consentements") : null;
    }

    @Transient
    public JsonNode getAlertes() {
        JsonNode root = getMedicalDataAsJson();
        return root != null ? root.get("alertes") : null;
    }

    // Original getters and setters from the Donor class
    public String getId() {
        return this.id;
    }

    public Donor id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Donor firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Donor lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Donor phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Donor gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ZonedDateTime getBirthDate() {
        return this.birthDate;
    }

    public Donor birthDate(ZonedDateTime birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return this.email;
    }

    public Donor email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Donor password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Integer getFidelityPoints() {
        return this.fidelityPoints;
    }

    public Donor fidelityPoints(Integer fidelityPoints) {
        this.setFidelityPoints(fidelityPoints);
        return this;
    }

    public void setFidelityPoints(Integer fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }

    public BloodType getBloodType() {
        return this.bloodType;
    }

    public Donor bloodType(BloodType bloodType) {
        this.setBloodType(bloodType);
        return this;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public ResusType getResusType() {
        return this.resusType;
    }

    public Donor resusType(ResusType resusType) {
        this.setResusType(resusType);
        return this;
    }

    public void setResusType(ResusType resusType) {
        this.resusType = resusType;
    }

    public Boolean getReceiveOtpForBloodRequest() {
        return this.receiveOtpForBloodRequest;
    }

    public Donor receiveOtpForBloodRequest(Boolean receiveOtpForBloodRequest) {
        this.setReceiveOtpForBloodRequest(receiveOtpForBloodRequest);
        return this;
    }

    public void setReceiveOtpForBloodRequest(Boolean receiveOtpForBloodRequest) {
        this.receiveOtpForBloodRequest = receiveOtpForBloodRequest;
    }

    public ZonedDateTime getLastDonationDate() {
        return this.lastDonationDate;
    }

    public Donor lastDonationDate(ZonedDateTime lastDonationDate) {
        this.setLastDonationDate(lastDonationDate);
        return this;
    }

    public void setLastDonationDate(ZonedDateTime lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    // Rest of original relationship getters and setters
    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Donor address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Set<Article> getFavorites() {
        return this.favorites;
    }

    public void setFavorites(Set<Article> articles) {
        this.favorites = articles;
    }

    public Donor favorites(Set<Article> articles) {
        this.setFavorites(articles);
        return this;
    }

    public Donor addFavorites(Article article) {
        this.favorites.add(article);
        return this;
    }

    public Donor removeFavorites(Article article) {
        this.favorites.remove(article);
        return this;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Donor roles(Set<Role> roles) {
        this.setRoles(roles);
        return this;
    }

    public Donor addRoles(Role role) {
        this.roles.add(role);
        return this;
    }

    public Donor removeRoles(Role role) {
        this.roles.remove(role);
        return this;
    }

    public DonationCenter getMedicalStaff() {
        return this.medicalStaff;
    }

    public void setMedicalStaff(DonationCenter donationCenter) {
        this.medicalStaff = donationCenter;
    }

    public Donor medicalStaff(DonationCenter donationCenter) {
        this.setMedicalStaff(donationCenter);
        return this;
    }

    public BloodRequest getCreatedBloodRequests() {
        return this.createdBloodRequests;
    }

    public void setCreatedBloodRequests(BloodRequest bloodRequest) {
        this.createdBloodRequests = bloodRequest;
    }

    public Donor createdBloodRequests(BloodRequest bloodRequest) {
        this.setCreatedBloodRequests(bloodRequest);
        return this;
    }

    public BloodRequest getCanceledBloodRequests() {
        return this.canceledBloodRequests;
    }

    public void setCanceledBloodRequests(BloodRequest bloodRequest) {
        this.canceledBloodRequests = bloodRequest;
    }

    public Donor canceledBloodRequests(BloodRequest bloodRequest) {
        this.setCanceledBloodRequests(bloodRequest);
        return this;
    }

    public OrganizationPartner getEmployees() {
        return this.employees;
    }

    public void setEmployees(OrganizationPartner organizationPartner) {
        this.employees = organizationPartner;
    }

    public Donor employees(OrganizationPartner organizationPartner) {
        this.setEmployees(organizationPartner);
        return this;
    }

    public Set<BloodDonationHistory> getBloodDonationHistories() {
        return this.bloodDonationHistories;
    }

    public void setBloodDonationHistories(Set<BloodDonationHistory> bloodDonationHistories) {
        if (this.bloodDonationHistories != null) {
            this.bloodDonationHistories.forEach(i -> i.setDonor(null));
        }
        if (bloodDonationHistories != null) {
            bloodDonationHistories.forEach(i -> i.setDonor(this));
        }
        this.bloodDonationHistories = bloodDonationHistories;
    }

    public Donor bloodDonationHistories(Set<BloodDonationHistory> bloodDonationHistories) {
        this.setBloodDonationHistories(bloodDonationHistories);
        return this;
    }

    public Donor addBloodDonationHistory(BloodDonationHistory bloodDonationHistory) {
        this.bloodDonationHistories.add(bloodDonationHistory);
        bloodDonationHistory.setDonor(this);
        return this;
    }

    public Donor removeBloodDonationHistory(BloodDonationHistory bloodDonationHistory) {
        this.bloodDonationHistories.remove(bloodDonationHistory);
        bloodDonationHistory.setDonor(null);
        return this;
    }

    public Set<Article> getCreatedBies() {
        return this.createdBies;
    }

    public void setCreatedBies(Set<Article> articles) {
        if (this.createdBies != null) {
            this.createdBies.forEach(i -> i.setDonor(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setDonor(this));
        }
        this.createdBies = articles;
    }

    public Donor createdBies(Set<Article> articles) {
        this.setCreatedBies(articles);
        return this;
    }

    public Donor addCreatedBy(Article article) {
        this.createdBies.add(article);
        article.setDonor(this);
        return this;
    }

    public Donor removeCreatedBy(Article article) {
        this.createdBies.remove(article);
        article.setDonor(null);
        return this;
    }

    public Set<DonorCampaign> getDonorCampaigns() {
        return this.donorCampaigns;
    }

    public void setDonorCampaigns(Set<DonorCampaign> donorCampaigns) {
        if (this.donorCampaigns != null) {
            this.donorCampaigns.forEach(i -> i.setDonor(null));
        }
        if (donorCampaigns != null) {
            donorCampaigns.forEach(i -> i.setDonor(this));
        }
        this.donorCampaigns = donorCampaigns;
    }

    public Donor donorCampaigns(Set<DonorCampaign> donorCampaigns) {
        this.setDonorCampaigns(donorCampaigns);
        return this;
    }

    public Donor addDonorCampaign(DonorCampaign donorCampaign) {
        this.donorCampaigns.add(donorCampaign);
        donorCampaign.setDonor(this);
        return this;
    }

    public Donor removeDonorCampaign(DonorCampaign donorCampaign) {
        this.donorCampaigns.remove(donorCampaign);
        donorCampaign.setDonor(null);
        return this;
    }

    public Set<UserNotification> getUserNotifications() {
        return this.userNotifications;
    }

    public void setUserNotifications(Set<UserNotification> userNotifications) {
        if (this.userNotifications != null) {
            this.userNotifications.forEach(i -> i.setDonor(null));
        }
        if (userNotifications != null) {
            userNotifications.forEach(i -> i.setDonor(this));
        }
        this.userNotifications = userNotifications;
    }

    public Donor userNotifications(Set<UserNotification> userNotifications) {
        this.setUserNotifications(userNotifications);
        return this;
    }

    public Donor addUserNotification(UserNotification userNotification) {
        this.userNotifications.add(userNotification);
        userNotification.setDonor(this);
        return this;
    }

    public Donor removeUserNotification(UserNotification userNotification) {
        this.userNotifications.remove(userNotification);
        userNotification.setDonor(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Donor)) {
            return false;
        }
        return getId() != null && getId().equals(((Donor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Donor{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + "'" +
                ", lastName='" + getLastName() + "'" +
                ", phoneNumber='" + getPhoneNumber() + "'" +
                ", gender='" + getGender() + "'" +
                ", birthDate='" + getBirthDate() + "'" +
                ", email='" + getEmail() + "'" +
                ", password='" + getPassword() + "'" +
                ", fidelityPoints=" + getFidelityPoints() +
                ", bloodType='" + getBloodType() + "'" +
                ", resusType='" + getResusType() + "'" +
                ", receiveOtpForBloodRequest='" + getReceiveOtpForBloodRequest() + "'" +
                ", lastDonationDate='" + getLastDonationDate() + "'" +
                ", medicalData='" + (medicalData != null ? "[Present]" : "[Not Present]") + "'" +
                "}";
    }
}