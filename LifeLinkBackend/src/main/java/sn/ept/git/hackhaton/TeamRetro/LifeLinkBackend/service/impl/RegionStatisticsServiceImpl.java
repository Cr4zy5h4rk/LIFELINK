package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.RegionStatistics;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.RegionStatisticsRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.RegionStatisticsService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.RegionStatisticsDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.RegionStatisticsMapper;

/**
 * Service Implementation for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.RegionStatistics}.
 */
@Service
@Transactional
public class RegionStatisticsServiceImpl implements RegionStatisticsService {

    private static final Logger LOG = LoggerFactory.getLogger(RegionStatisticsServiceImpl.class);

    private final RegionStatisticsRepository regionStatisticsRepository;

    private final RegionStatisticsMapper regionStatisticsMapper;

    public RegionStatisticsServiceImpl(
        RegionStatisticsRepository regionStatisticsRepository,
        RegionStatisticsMapper regionStatisticsMapper
    ) {
        this.regionStatisticsRepository = regionStatisticsRepository;
        this.regionStatisticsMapper = regionStatisticsMapper;
    }

    @Override
    public RegionStatisticsDTO save(RegionStatisticsDTO regionStatisticsDTO) {
        LOG.debug("Request to save RegionStatistics : {}", regionStatisticsDTO);
        RegionStatistics regionStatistics = regionStatisticsMapper.toEntity(regionStatisticsDTO);
        regionStatistics = regionStatisticsRepository.save(regionStatistics);
        return regionStatisticsMapper.toDto(regionStatistics);
    }

    @Override
    public RegionStatisticsDTO update(RegionStatisticsDTO regionStatisticsDTO) {
        LOG.debug("Request to update RegionStatistics : {}", regionStatisticsDTO);
        RegionStatistics regionStatistics = regionStatisticsMapper.toEntity(regionStatisticsDTO);
        regionStatistics = regionStatisticsRepository.save(regionStatistics);
        return regionStatisticsMapper.toDto(regionStatistics);
    }

    @Override
    public Optional<RegionStatisticsDTO> partialUpdate(RegionStatisticsDTO regionStatisticsDTO) {
        LOG.debug("Request to partially update RegionStatistics : {}", regionStatisticsDTO);

        return regionStatisticsRepository
            .findById(regionStatisticsDTO.getId())
            .map(existingRegionStatistics -> {
                regionStatisticsMapper.partialUpdate(existingRegionStatistics, regionStatisticsDTO);

                return existingRegionStatistics;
            })
            .map(regionStatisticsRepository::save)
            .map(regionStatisticsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegionStatisticsDTO> findAll() {
        LOG.debug("Request to get all RegionStatistics");
        return regionStatisticsRepository
            .findAll()
            .stream()
            .map(regionStatisticsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegionStatisticsDTO> findOne(Long id) {
        LOG.debug("Request to get RegionStatistics : {}", id);
        return regionStatisticsRepository.findById(id).map(regionStatisticsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RegionStatistics : {}", id);
        regionStatisticsRepository.deleteById(id);
    }
}
