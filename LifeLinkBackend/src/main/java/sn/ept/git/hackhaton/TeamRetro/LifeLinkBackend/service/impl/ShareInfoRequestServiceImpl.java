package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.ShareInfoRequest;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.EligibilityStatus;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.BloodDonationCampaignRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.DonationCenterRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.DonorRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.ShareInfoRequestDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.ShareInfoRequestRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.ShareInfoRequestService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.ShareInfoSelectionDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.ShareInfoRequestMapper;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ShareInfoRequest}.
 */
@Service
@Transactional
public class ShareInfoRequestServiceImpl implements ShareInfoRequestService {

    private final Logger log = LoggerFactory.getLogger(ShareInfoRequestServiceImpl.class);

    private final ShareInfoRequestRepository shareInfoRequestRepository;
    private final ShareInfoRequestMapper shareInfoRequestMapper;
    private final DonorRepository donorRepository;
    private final DonationCenterRepository donationCenterRepository;
    private final BloodDonationCampaignRepository bloodDonationCampaignRepository;

    public ShareInfoRequestServiceImpl(DonorRepository donorRepository,
                                       ShareInfoRequestRepository shareInfoRequestRepository,
                                       ShareInfoRequestMapper shareInfoRequestMapper,
                                        DonationCenterRepository donationCenterRepository,
                                        BloodDonationCampaignRepository bloodDonationCampaignRepository
                                       ) {
        this.shareInfoRequestRepository = shareInfoRequestRepository;
        this.shareInfoRequestMapper = shareInfoRequestMapper;
        this.donorRepository = donorRepository;
        this.donationCenterRepository = donationCenterRepository;
        this.bloodDonationCampaignRepository = bloodDonationCampaignRepository;
    }

    @Override
    public ShareInfoRequestDTO save(ShareInfoRequestDTO shareInfoRequestDTO) {
        log.debug("Request to save ShareInfoRequest : {}", shareInfoRequestDTO);

        ShareInfoRequest userRequest = new ShareInfoRequest();
        userRequest.setPhoneNumber(shareInfoRequestDTO.getPhoneNumber());
        userRequest.setBloodDonationCampaign(
                shareInfoRequestDTO.getBloodDonationCampaign() != null ?
                        bloodDonationCampaignRepository.findById(shareInfoRequestDTO.getBloodDonationCampaign())
                                .orElseThrow(() -> new RuntimeException("Blood Donation Campaign not found with ID: " + shareInfoRequestDTO.getBloodDonationCampaign())) : null);
        userRequest.setDonationCenter(
                shareInfoRequestDTO.getDonationCenter() != null ?
                        donationCenterRepository.findById(shareInfoRequestDTO.getDonationCenter())
                                .orElseThrow(() -> new RuntimeException("Donation Center not found with ID: " + shareInfoRequestDTO.getDonationCenter())) : null);
        userRequest.setSharedInfoSelection(shareInfoRequestDTO.getSharedInfoSelection());
        userRequest.setAccepted(null);


        shareInfoRequestRepository.save(userRequest);

        ShareInfoRequestDTO result = shareInfoRequestMapper.toDto(userRequest);

        return result;
    }

    @Override
    public ShareInfoRequestDTO update(Long id, ShareInfoRequestDTO shareInfoRequestDTO) {
        log.debug("Request to update ShareInfoRequest : {}, {}", id, shareInfoRequestDTO);

        // Vérifier si l'entité existe
        ShareInfoRequest existingRequest = shareInfoRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShareInfoRequest not found"));

        // Préserver la date de requête originale si non fournie
        if (shareInfoRequestDTO.getRequestDate() == null) {
            shareInfoRequestDTO.setRequestDate(existingRequest.getRequestDate());
        }

        ShareInfoRequest shareInfoRequest = shareInfoRequestMapper.toEntity(shareInfoRequestDTO);
        shareInfoRequest.setId(id);  // ID n'est pas dans le DTO, donc on l'ajoute ici

        shareInfoRequest = shareInfoRequestRepository.save(shareInfoRequest);

        ShareInfoRequestDTO result = shareInfoRequestMapper.toDto(shareInfoRequest);
        // Préserver le numéro de téléphone
        result.setPhoneNumber(shareInfoRequestDTO.getPhoneNumber());

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShareInfoRequestDTO> findAll() {
        log.debug("Request to get all ShareInfoRequests");
        List<ShareInfoRequestDTO> result = shareInfoRequestRepository.findAll().stream()
                .map(shareInfoRequestMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShareInfoRequestDTO> findOne(Long id) {
        log.debug("Request to get ShareInfoRequest : {}", id);
        return shareInfoRequestRepository.findById(id)
                .map(shareInfoRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShareInfoRequest : {}", id);
        shareInfoRequestRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShareInfoRequestDTO> findByCampaignId(Long campaignId) {
        log.debug("Request to get all ShareInfoRequests for campaign : {}", campaignId);
        return shareInfoRequestRepository.findByBloodDonationCampaignId(campaignId)
                .stream()
                .map(shareInfoRequestMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShareInfoRequestDTO> findByCenterId(Long centerId) {
        log.debug("Request to get all ShareInfoRequests for center : {}", centerId);
        return shareInfoRequestRepository.findByDonationCenterId(centerId)
                .stream()
                .map(shareInfoRequestMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShareInfoRequestDTO> findPendingRequests() {
        log.debug("Request to get all pending ShareInfoRequests");
        return shareInfoRequestRepository.findByIsAcceptedIsNull()
                .stream()
                .map(shareInfoRequestMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShareInfoRequestDTO> findRejectedRequests() {
        log.debug("Request to get all pending ShareInfoRequests");
        return shareInfoRequestRepository.findByIsAcceptedIsFalse()
                .stream()
                .map(shareInfoRequestMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public ShareInfoRequestDTO acceptRequest(Long id) {
        log.debug("Request to accept ShareInfoRequest : {}", id);
        ShareInfoRequest shareInfoRequest = shareInfoRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShareInfoRequest not found"));

        shareInfoRequest.setAccepted(true);
        shareInfoRequest.setAcceptanceDate(ZonedDateTime.now());

        shareInfoRequest = shareInfoRequestRepository.save(shareInfoRequest);
        return shareInfoRequestMapper.toDto(shareInfoRequest);
    }

//    public ShareInfoRequestDTO acceptRequestWithSelection(Long id, ShareInfoSelectionDTO selectionDTO) {
//        ShareInfoRequest request = shareInfoRequestRepository.findById(id)
//                .orElseThrow(() -> new IllegalStateException("ShareInfoRequest not found"));
////        request.setAccepted(true);
//        request.setAcceptanceDate(ZonedDateTime.now());
//
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            request.setSharedInfoSelection(objectMapper.writeValueAsString(selectionDTO));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Error saving selection", e);
//        }
//
//        ShareInfoRequest savedRequest = shareInfoRequestRepository.save(request);
//        return shareInfoRequestMapper.toDto(savedRequest);
//    }

    @Override
    public ShareInfoRequestDTO rejectRequest(Long id) {
        log.debug("Request to reject ShareInfoRequest : {}", id);
        ShareInfoRequest shareInfoRequest = shareInfoRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShareInfoRequest not found"));

        shareInfoRequest.setAccepted(false);
        shareInfoRequest.setAcceptanceDate(ZonedDateTime.now());

        shareInfoRequest = shareInfoRequestRepository.save(shareInfoRequest);
        return shareInfoRequestMapper.toDto(shareInfoRequest);
    }

    @Override
    public ShareInfoRequestDTO createRequest(String phoneNumber, Long campaignId, Long centerId, ShareInfoSelectionDTO selectionDTO) {
        log.debug("Request to create ShareInfoRequest with phone: {}, campaignId: {}, centerId: {}",
                phoneNumber, campaignId, centerId);

        // Validation: soit campaignId soit centerId doit être fourni, mais pas les deux
        if ((campaignId == null && centerId == null)) {
            throw new IllegalArgumentException("Exactly one of campaignId or centerId must be provided");
        }

        // Créer le DTO
        ShareInfoRequestDTO requestDTO = new ShareInfoRequestDTO();
        requestDTO.setPhoneNumber(phoneNumber);
        requestDTO.setBloodDonationCampaign(campaignId);
        requestDTO.setDonationCenter(centerId);
        requestDTO.setRequestDate(ZonedDateTime.now());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            requestDTO.setSharedInfoSelection(objectMapper.writeValueAsString(selectionDTO));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error saving selection", e);
        }

        // Sauvegarder via la méthode existante
        return save(requestDTO);
    }
}