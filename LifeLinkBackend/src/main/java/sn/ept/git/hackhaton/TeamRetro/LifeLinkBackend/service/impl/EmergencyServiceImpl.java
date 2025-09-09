package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodRequest;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.BloodRequestStatus;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.EmergencyDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.BloodRequestRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.EmergencyService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.EmergencyMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BloodRequest} as emergencies for the Flutter application.
 */
@Service
@Transactional
public class EmergencyServiceImpl implements EmergencyService {

    private final Logger log = LoggerFactory.getLogger(EmergencyServiceImpl.class);

    private final BloodRequestRepository bloodRequestRepository;
    private final EmergencyMapper emergencyMapper;

    public EmergencyServiceImpl(
            BloodRequestRepository bloodRequestRepository,
            EmergencyMapper emergencyMapper) {
        this.bloodRequestRepository = bloodRequestRepository;
        this.emergencyMapper = emergencyMapper;
    }


    /**
     * Save an emergency.
     *
     * @param emergencyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmergencyDTO save(EmergencyDTO emergencyDTO) {
        log.debug("Request to save Emergency : {}", emergencyDTO);

        BloodRequest bloodRequest = emergencyMapper.toEntity(emergencyDTO);
        bloodRequest = bloodRequestRepository.save(bloodRequest);
        return emergencyMapper.toDto(bloodRequest);
    }

    /**
     * Update an emergency.
     *
     * @param emergencyDTO the entity to update.
     * @return the persisted entity.
     */
    @Override
    public EmergencyDTO update(EmergencyDTO emergencyDTO) {
        log.debug("Request to update Emergency : {}", emergencyDTO);

        if (emergencyDTO.getId() == null) {
            throw new IllegalArgumentException("Emergency ID cannot be null for update operation");
        }

        // Check if the entity exists
        Optional<BloodRequest> existingRequest = bloodRequestRepository.findById(emergencyDTO.getId());
        if (existingRequest.isEmpty()) {
            throw new IllegalArgumentException("Emergency with ID " + emergencyDTO.getId() + " not found");
        }

        BloodRequest bloodRequest = emergencyMapper.toEntity(emergencyDTO);
        bloodRequest = bloodRequestRepository.save(bloodRequest);
        return emergencyMapper.toDto(bloodRequest);
    }

    /**
     * Get all the emergencies.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmergencyDTO> findAll() {
        log.debug("Request to get all Emergencies");
        return bloodRequestRepository.findAll()
                .stream()
                .map(emergencyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmergencyDTO> findAllPending() {
        log.debug("Request to get all pending Emergencies");
        return bloodRequestRepository.findByStatusOrderByCreatedAtDesc(BloodRequestStatus.PENDING)
                .stream()
                .map(emergencyMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les 10 dernières urgences en statut PENDING
     * @return Liste des 10 dernières urgences en attente
     */
    public List<EmergencyDTO> getLatestPendingEmergencies() {
        return bloodRequestRepository.findTop10ByStatusOrderByCreatedAtDesc(BloodRequestStatus.PENDING)
                .stream()
                .map(emergencyMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get one emergency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmergencyDTO> findOne(Long id) {
        log.debug("Request to get Emergency : {}", id);
        return bloodRequestRepository.findById(id)
                .map(emergencyMapper::toDto);
    }

    /**
     * Delete the emergency by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Emergency : {}", id);
        bloodRequestRepository.deleteById(id);
    }
}