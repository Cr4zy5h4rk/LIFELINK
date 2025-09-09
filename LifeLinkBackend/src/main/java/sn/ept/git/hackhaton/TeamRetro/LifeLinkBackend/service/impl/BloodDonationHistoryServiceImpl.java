package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationHistory;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.BloodDonationHistoryRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.BloodDonationHistoryService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.BloodDonationHistoryDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.BloodDonationHistoryMapper;

/**
 * Service Implementation for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationHistory}.
 */
@Service
@Transactional
public class BloodDonationHistoryServiceImpl implements BloodDonationHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(BloodDonationHistoryServiceImpl.class);

    private final BloodDonationHistoryRepository bloodDonationHistoryRepository;

    private final BloodDonationHistoryMapper bloodDonationHistoryMapper;

    public BloodDonationHistoryServiceImpl(
        BloodDonationHistoryRepository bloodDonationHistoryRepository,
        BloodDonationHistoryMapper bloodDonationHistoryMapper
    ) {
        this.bloodDonationHistoryRepository = bloodDonationHistoryRepository;
        this.bloodDonationHistoryMapper = bloodDonationHistoryMapper;
    }

    @Override
    public BloodDonationHistoryDTO save(BloodDonationHistoryDTO bloodDonationHistoryDTO) {
        LOG.debug("Request to save BloodDonationHistory : {}", bloodDonationHistoryDTO);
        BloodDonationHistory bloodDonationHistory = bloodDonationHistoryMapper.toEntity(bloodDonationHistoryDTO);
        bloodDonationHistory = bloodDonationHistoryRepository.save(bloodDonationHistory);
        return bloodDonationHistoryMapper.toDto(bloodDonationHistory);
    }

    @Override
    public BloodDonationHistoryDTO update(BloodDonationHistoryDTO bloodDonationHistoryDTO) {
        LOG.debug("Request to update BloodDonationHistory : {}", bloodDonationHistoryDTO);
        BloodDonationHistory bloodDonationHistory = bloodDonationHistoryMapper.toEntity(bloodDonationHistoryDTO);
        bloodDonationHistory = bloodDonationHistoryRepository.save(bloodDonationHistory);
        return bloodDonationHistoryMapper.toDto(bloodDonationHistory);
    }

    @Override
    public Optional<BloodDonationHistoryDTO> partialUpdate(BloodDonationHistoryDTO bloodDonationHistoryDTO) {
        LOG.debug("Request to partially update BloodDonationHistory : {}", bloodDonationHistoryDTO);

        return bloodDonationHistoryRepository
            .findById(bloodDonationHistoryDTO.getId())
            .map(existingBloodDonationHistory -> {
                bloodDonationHistoryMapper.partialUpdate(existingBloodDonationHistory, bloodDonationHistoryDTO);

                return existingBloodDonationHistory;
            })
            .map(bloodDonationHistoryRepository::save)
            .map(bloodDonationHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BloodDonationHistoryDTO> findAll() {
        LOG.debug("Request to get all BloodDonationHistories");
        return bloodDonationHistoryRepository
            .findAll()
            .stream()
            .map(bloodDonationHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BloodDonationHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get BloodDonationHistory : {}", id);
        return bloodDonationHistoryRepository.findById(id).map(bloodDonationHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BloodDonationHistory : {}", id);
        bloodDonationHistoryRepository.deleteById(id);
    }
}
