package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodRequest;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.BloodRequestRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.BloodRequestService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodRequestCreateDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodRequestDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.BloodRequestMapper;

/**
 * Service Implementation for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodRequest}.
 */
@Service
@Transactional
public class BloodRequestServiceImpl implements BloodRequestService {

    private static final Logger LOG = LoggerFactory.getLogger(BloodRequestServiceImpl.class);

    private final BloodRequestRepository bloodRequestRepository;

    private final BloodRequestMapper bloodRequestMapper;

    public BloodRequestServiceImpl(BloodRequestRepository bloodRequestRepository, BloodRequestMapper bloodRequestMapper) {
        this.bloodRequestRepository = bloodRequestRepository;
        this.bloodRequestMapper = bloodRequestMapper;
    }

    @Override
    public BloodRequestCreateDTO save(BloodRequestCreateDTO bloodRequestDTO) {
        LOG.debug("Request to save BloodRequest : {}", bloodRequestDTO);
        BloodRequest bloodRequest = bloodRequestMapper.toEntity(bloodRequestDTO);
        bloodRequest = bloodRequestRepository.save(bloodRequest);
        return bloodRequestMapper.toCreateDto(bloodRequest);
    }

    @Override
    public BloodRequestDTO update(BloodRequestDTO bloodRequestDTO) {
        LOG.debug("Request to update BloodRequest : {}", bloodRequestDTO);
        BloodRequest bloodRequest = bloodRequestMapper.toEntity(bloodRequestDTO);
        bloodRequest = bloodRequestRepository.save(bloodRequest);
        return bloodRequestMapper.toDto(bloodRequest);
    }

    @Override
    public Optional<BloodRequestDTO> partialUpdate(BloodRequestDTO bloodRequestDTO) {
        LOG.debug("Request to partially update BloodRequest : {}", bloodRequestDTO);

        return bloodRequestRepository
            .findById(bloodRequestDTO.getId())
            .map(existingBloodRequest -> {
                bloodRequestMapper.partialUpdate(existingBloodRequest, bloodRequestDTO);

                return existingBloodRequest;
            })
            .map(bloodRequestRepository::save)
            .map(bloodRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BloodRequestDTO> findAll() {
        LOG.debug("Request to get all BloodRequests");
        return bloodRequestRepository.findAll().stream().map(bloodRequestMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BloodRequestDTO> findOne(Long id) {
        LOG.debug("Request to get BloodRequest : {}", id);
        return bloodRequestRepository.findById(id).map(bloodRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BloodRequest : {}", id);
        bloodRequestRepository.deleteById(id);
    }
}
