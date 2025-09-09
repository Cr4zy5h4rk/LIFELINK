package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.OrganizationPartner;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.OrganizationPartnerRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.OrganizationPartnerService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.OrganizationPartnerDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.OrganizationPartnerMapper;

/**
 * Service Implementation for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.OrganizationPartner}.
 */
@Service
@Transactional
public class OrganizationPartnerServiceImpl implements OrganizationPartnerService {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationPartnerServiceImpl.class);

    private final OrganizationPartnerRepository organizationPartnerRepository;

    private final OrganizationPartnerMapper organizationPartnerMapper;

    public OrganizationPartnerServiceImpl(
        OrganizationPartnerRepository organizationPartnerRepository,
        OrganizationPartnerMapper organizationPartnerMapper
    ) {
        this.organizationPartnerRepository = organizationPartnerRepository;
        this.organizationPartnerMapper = organizationPartnerMapper;
    }

    @Override
    public OrganizationPartnerDTO save(OrganizationPartnerDTO organizationPartnerDTO) {
        LOG.debug("Request to save OrganizationPartner : {}", organizationPartnerDTO);
        OrganizationPartner organizationPartner = organizationPartnerMapper.toEntity(organizationPartnerDTO);
        organizationPartner = organizationPartnerRepository.save(organizationPartner);
        return organizationPartnerMapper.toDto(organizationPartner);
    }

    @Override
    public OrganizationPartnerDTO update(OrganizationPartnerDTO organizationPartnerDTO) {
        LOG.debug("Request to update OrganizationPartner : {}", organizationPartnerDTO);
        OrganizationPartner organizationPartner = organizationPartnerMapper.toEntity(organizationPartnerDTO);
        organizationPartner = organizationPartnerRepository.save(organizationPartner);
        return organizationPartnerMapper.toDto(organizationPartner);
    }

    @Override
    public Optional<OrganizationPartnerDTO> partialUpdate(OrganizationPartnerDTO organizationPartnerDTO) {
        LOG.debug("Request to partially update OrganizationPartner : {}", organizationPartnerDTO);

        return organizationPartnerRepository
            .findById(organizationPartnerDTO.getId())
            .map(existingOrganizationPartner -> {
                organizationPartnerMapper.partialUpdate(existingOrganizationPartner, organizationPartnerDTO);

                return existingOrganizationPartner;
            })
            .map(organizationPartnerRepository::save)
            .map(organizationPartnerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationPartnerDTO> findAll() {
        LOG.debug("Request to get all OrganizationPartners");
        return organizationPartnerRepository
            .findAll()
            .stream()
            .map(organizationPartnerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the organizationPartners where DonationCenter is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrganizationPartnerDTO> findAllWhereDonationCenterIsNull() {
        LOG.debug("Request to get all organizationPartners where DonationCenter is null");
        return StreamSupport.stream(organizationPartnerRepository.findAll().spliterator(), false)
            .filter(organizationPartner -> organizationPartner.getDonationCenter() == null)
            .map(organizationPartnerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationPartnerDTO> findOne(Long id) {
        LOG.debug("Request to get OrganizationPartner : {}", id);
        return organizationPartnerRepository.findById(id).map(organizationPartnerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete OrganizationPartner : {}", id);
        organizationPartnerRepository.deleteById(id);
    }
}
