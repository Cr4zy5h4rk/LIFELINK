package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.DonationCenterRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.DonationCenterService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonationCenterDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.DonationCenterMapper;

/**
 * Service Implementation for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonationCenter}.
 */
@Service
@Transactional
public class DonationCenterServiceImpl implements DonationCenterService {

    private static final Logger LOG = LoggerFactory.getLogger(DonationCenterServiceImpl.class);

    private final DonationCenterRepository donationCenterRepository;

    private final DonationCenterMapper donationCenterMapper;

    public DonationCenterServiceImpl(DonationCenterRepository donationCenterRepository, DonationCenterMapper donationCenterMapper) {
        this.donationCenterRepository = donationCenterRepository;
        this.donationCenterMapper = donationCenterMapper;
    }

    @Override
    public DonationCenterDTO save(DonationCenterDTO donationCenterDTO) {
        LOG.debug("Request to save DonationCenter : {}", donationCenterDTO);
        DonationCenter donationCenter = donationCenterMapper.toEntity(donationCenterDTO);
        donationCenter = donationCenterRepository.save(donationCenter);
        return donationCenterMapper.toDto(donationCenter);
    }

    @Override
    public DonationCenterDTO update(DonationCenterDTO donationCenterDTO) {
        LOG.debug("Request to update DonationCenter : {}", donationCenterDTO);
        DonationCenter donationCenter = donationCenterMapper.toEntity(donationCenterDTO);
        donationCenter = donationCenterRepository.save(donationCenter);
        return donationCenterMapper.toDto(donationCenter);
    }

    @Override
    public Optional<DonationCenterDTO> partialUpdate(DonationCenterDTO donationCenterDTO) {
        LOG.debug("Request to partially update DonationCenter : {}", donationCenterDTO);

        return donationCenterRepository
            .findById(donationCenterDTO.getId())
            .map(existingDonationCenter -> {
                donationCenterMapper.partialUpdate(existingDonationCenter, donationCenterDTO);

                return existingDonationCenter;
            })
            .map(donationCenterRepository::save)
            .map(donationCenterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonationCenterDTO> findAll() {
        LOG.debug("Request to get all DonationCenters");
        return donationCenterRepository
            .findAll()
            .stream()
            .map(donationCenterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonationCenterDTO> findOne(Long id) {
        LOG.debug("Request to get DonationCenter : {}", id);
        return donationCenterRepository.findById(id).map(donationCenterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete DonationCenter : {}", id);
        donationCenterRepository.deleteById(id);
    }
}
