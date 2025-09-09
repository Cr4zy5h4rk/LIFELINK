package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.springframework.stereotype.Service;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodRequest;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodRequestStatus;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodType;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ResusType;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.EmergencyDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.DonationCenterRepository;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of the EmergencyMapper interface for converting between {@link BloodRequest} and {@link EmergencyDTO}.
 */
@Service
public class EmergencyMapperImpl implements EmergencyMapper {

    private final DonationCenterRepository donationCenterRepository;

    public EmergencyMapperImpl(DonationCenterRepository donationCenterRepository) {
        this.donationCenterRepository = donationCenterRepository;
    }

    /**
     * Convert a BloodRequest entity to an EmergencyDTO.
     *
     * @param bloodRequest the entity to convert
     * @return the DTO
     */
    @Override
    public EmergencyDTO toDto(BloodRequest bloodRequest) {
        if (bloodRequest == null) {
            return null;
        }

        EmergencyDTO emergencyDTO = new EmergencyDTO();

        // Set ID
        emergencyDTO.setId(bloodRequest.getId());

        // Extract title from details
        String title = extractTitleFromDetails(bloodRequest.getDetails());
        emergencyDTO.setTitle(title);

        // Set hospital from donation center
        if (bloodRequest.getDonationCenter() != null) {
            emergencyDTO.setHospital(bloodRequest.getDonationCenter().getStructureName());
            emergencyDTO.setLatitude(bloodRequest.getDonationCenter().getAddress().getLatitude());
            emergencyDTO.setLongitude(bloodRequest.getDonationCenter().getAddress().getLongitude());
        } else {
            emergencyDTO.setHospital("Hôpital non spécifié");
        }

        // Set date (using createdAt)
        emergencyDTO.setDate(bloodRequest.getCreatedAt());

        // Set blood type (combine BloodType and ResusType)
        String bloodTypeStr = "";
        if (bloodRequest.getBloodType() != null) {
            bloodTypeStr = bloodRequest.getBloodType().name();
            if (bloodRequest.getResusType() != null) {
                bloodTypeStr += bloodRequest.getResusType() == ResusType.POSITIVE ? "+" : "-";
            }
        }
        emergencyDTO.setBloodType(bloodTypeStr);

        // Set Wolof audio URL
        emergencyDTO.setWolofAudioUrls(bloodRequest.getWolofAudioDetails());

        return emergencyDTO;
    }

    /**
     * Convert an EmergencyDTO to a BloodRequest entity.
     *
     * @param emergencyDTO the DTO to convert
     * @return the entity
     */
    @Override
    public BloodRequest toEntity(EmergencyDTO emergencyDTO) {
        if (emergencyDTO == null) {
            return null;
        }

        BloodRequest bloodRequest = new BloodRequest();

        // Set ID if provided
        if (emergencyDTO.getId() != null) {
            bloodRequest.setId(emergencyDTO.getId());
        }

        // Set created date
        bloodRequest.setCreatedAt(emergencyDTO.getDate() != null ?
                emergencyDTO.getDate() : ZonedDateTime.now());

        // Set status (default to PENDING)
        bloodRequest.setStatus(BloodRequestStatus.PENDING);

        // Parse blood type
        if (emergencyDTO.getBloodType() != null && !emergencyDTO.getBloodType().isEmpty()) {
            String bloodTypeStr = emergencyDTO.getBloodType();

            // Extract the base blood type (A, B, AB, O)
            String baseType = bloodTypeStr.replaceAll("[+-]", "");
            try {
                bloodRequest.setBloodType(BloodType.valueOf(baseType));
            } catch (IllegalArgumentException e) {
                // Default to O if invalid
                bloodRequest.setBloodType(BloodType.O);
            }

            // Extract the resus factor (+ or -)
            if (bloodTypeStr.endsWith("+")) {
                bloodRequest.setResusType(ResusType.POSITIVE);
            } else if (bloodTypeStr.endsWith("-")) {
                bloodRequest.setResusType(ResusType.NEGATIVE);
            } else {
                // Default to POSITIVE if not specified
                bloodRequest.setResusType(ResusType.POSITIVE);
            }
        } else {
            // Default values if blood type is not provided
            bloodRequest.setBloodType(BloodType.O);
            bloodRequest.setResusType(ResusType.POSITIVE);
        }

        // Construct details from title and other fields
        String details = constructDetails(emergencyDTO);
        bloodRequest.setDetails(details);

        // Set Wolof audio URL
        if (emergencyDTO.getWolofAudioUrls() != null && !emergencyDTO.getWolofAudioUrls().isEmpty()) {
            bloodRequest.setWolofAudioDetails(emergencyDTO.getWolofAudioUrls());
        } else {
            // Generate default URL if not provided
            bloodRequest.setWolofAudioDetails("https://lifelink.sn/audio/emergency_" +
                    (emergencyDTO.getId() != null ? emergencyDTO.getId() : "new") + "_wolof.mp3");
        }

        // Link to donation center based on hospital name
        if (emergencyDTO.getHospital() != null && !emergencyDTO.getHospital().isEmpty()) {
            // Try to find donation center by name
            Optional<DonationCenter> center = donationCenterRepository.findByStructureName(emergencyDTO.getHospital());
            center.ifPresent(bloodRequest::setDonationCenter);
        }

        return bloodRequest;
    }

    /**
     * Extract title from the details string.
     *
     * @param details the details string
     * @return the extracted title or a default title
     */
    private String extractTitleFromDetails(String details) {
        if (details == null || details.isEmpty()) {
            return "Urgence de don de sang";
        }

        // Try to extract title if it follows "Title: " pattern
        Pattern titlePattern = Pattern.compile("Title:\\s*([^|]+)");
        Matcher titleMatcher = titlePattern.matcher(details);

        if (titleMatcher.find()) {
            return titleMatcher.group(1).trim();
        }

        // If no title pattern, just return the first part of the details (up to 50 chars)
        int endIndex = Math.min(details.length(), 50);
        String shortDetails = details.substring(0, endIndex);
        if (endIndex < details.length()) {
            shortDetails += "...";
        }

        return shortDetails;
    }

    /**
     * Construct details string from EmergencyDTO fields.
     *
     * @param emergencyDTO the DTO with emergency information
     * @return a formatted details string
     */
    private String constructDetails(EmergencyDTO emergencyDTO) {
        StringBuilder sb = new StringBuilder();

        // Add title
        if (emergencyDTO.getTitle() != null && !emergencyDTO.getTitle().isEmpty()) {
            sb.append("Title: ").append(emergencyDTO.getTitle());
        } else {
            sb.append("Title: Urgence de don de sang");
        }

        // Add blood type
        if (emergencyDTO.getBloodType() != null && !emergencyDTO.getBloodType().isEmpty()) {
            sb.append(" | Blood Type: ").append(emergencyDTO.getBloodType());
        }

        // Add hospital
        if (emergencyDTO.getHospital() != null && !emergencyDTO.getHospital().isEmpty()) {
            sb.append(" | Hospital: ").append(emergencyDTO.getHospital());
        }

        // Add date
        if (emergencyDTO.getDate() != null) {
            sb.append(" | Date: ").append(emergencyDTO.getDate());
        }

        return sb.toString();
    }
}