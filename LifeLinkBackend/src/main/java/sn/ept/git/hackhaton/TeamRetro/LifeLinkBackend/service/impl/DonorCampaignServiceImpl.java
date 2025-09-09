package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonorCampaign;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.DonorCampaignRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.DonorCampaignService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorCampaignDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.DonorCampaignMapper;

/**
 * Service Implementation for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.DonorCampaign}.
 */
@Service
@Transactional
public class DonorCampaignServiceImpl implements DonorCampaignService {

    private static final Logger LOG = LoggerFactory.getLogger(DonorCampaignServiceImpl.class);

    private final DonorCampaignRepository donorCampaignRepository;

    private final DonorCampaignMapper donorCampaignMapper;

    public DonorCampaignServiceImpl(DonorCampaignRepository donorCampaignRepository, DonorCampaignMapper donorCampaignMapper) {
        this.donorCampaignRepository = donorCampaignRepository;
        this.donorCampaignMapper = donorCampaignMapper;
    }

    @Override
    public DonorCampaignDTO save(DonorCampaignDTO donorCampaignDTO) {
        LOG.debug("Request to save DonorCampaign : {}", donorCampaignDTO);
        DonorCampaign donorCampaign = donorCampaignMapper.toEntity(donorCampaignDTO);
        donorCampaign = donorCampaignRepository.save(donorCampaign);
        return donorCampaignMapper.toDto(donorCampaign);
    }

    @Override
    public DonorCampaignDTO update(DonorCampaignDTO donorCampaignDTO) {
        LOG.debug("Request to update DonorCampaign : {}", donorCampaignDTO);
        DonorCampaign donorCampaign = donorCampaignMapper.toEntity(donorCampaignDTO);
        donorCampaign = donorCampaignRepository.save(donorCampaign);
        return donorCampaignMapper.toDto(donorCampaign);
    }

    @Override
    public Optional<DonorCampaignDTO> partialUpdate(DonorCampaignDTO donorCampaignDTO) {
        LOG.debug("Request to partially update DonorCampaign : {}", donorCampaignDTO);

        return donorCampaignRepository
            .findById(donorCampaignDTO.getId())
            .map(existingDonorCampaign -> {
                donorCampaignMapper.partialUpdate(existingDonorCampaign, donorCampaignDTO);

                return existingDonorCampaign;
            })
            .map(donorCampaignRepository::save)
            .map(donorCampaignMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonorCampaignDTO> findAll() {
        LOG.debug("Request to get all DonorCampaigns");
        return donorCampaignRepository.findAll().stream().map(donorCampaignMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonorCampaignDTO> findOne(Long id) {
        LOG.debug("Request to get DonorCampaign : {}", id);
        return donorCampaignRepository.findById(id).map(donorCampaignMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete DonorCampaign : {}", id);
        donorCampaignRepository.deleteById(id);
    }
}
