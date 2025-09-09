package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.Gender;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.DonorRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.DonorService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.EsignetDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.DonorMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.security.DonorPrincipal;



@Service
@Transactional
public class DonorServiceImpl implements DonorService {

    private static final Logger LOG = LoggerFactory.getLogger(DonorServiceImpl.class);

    private final DonorRepository donorRepository;

    private final DonorMapper donorMapper;

    public DonorServiceImpl(DonorRepository donorRepository, DonorMapper donorMapper) {
        this.donorRepository = donorRepository;
        this.donorMapper = donorMapper;
    }

    @Override
    public DonorDTO saveEsignet(EsignetDTO donorDTO) {
        DonorDTO donor = new DonorDTO();
        donor.setPhoneNumber(donorDTO.getPhone_number());
        donor.setFirstName(donorDTO.getName().split(" ")[0]);

        // Vérifier si le nom contient un prénom et un nom de famille
        String[] nameParts = donorDTO.getName().split(" ");
        donor.setLastName(nameParts.length > 1 ? nameParts[1] : "");

        donor.setEmail(donorDTO.getEmail());

        // Conversion de la date de naissance
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.parse(donorDTO.getBirthdate(), formatter);

        // Ajouter un fuseau horaire (ex: "Africa/Dakar")
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of("Africa/Dakar"));
        donor.setBirthDate(zonedDateTime);

        // Conversion du genre
        donor.setGender(donorDTO.getGender().equalsIgnoreCase("Female") ? Gender.FEMALE : Gender.MALE);

        // Mapping et sauvegarde
        Donor donorTemp = donorMapper.toEntity(donor);
        donorRepository.save(donorTemp);

        return donorMapper.toDto(donorTemp);
    }


    @Override
    public DonorDTO save(DonorDTO donorDTO) {
        Optional<Donor> existingDonorOpt = donorRepository.findByEmail(donorDTO.getEmail());
        Donor donor;
        if (existingDonorOpt.isPresent()) {
            donor = existingDonorOpt.get();
            donorMapper.partialUpdate(donor, donorDTO);
        } else {
            donor = donorMapper.toEntity(donorDTO);
        }
        donor = donorRepository.save(donor);
        return donorMapper.toDto(donor);
    }

    @Override
    public DonorDTO update(DonorDTO donorDTO) {
        LOG.debug("Request to update Donor : {}", donorDTO);
        Donor donor = donorMapper.toEntity(donorDTO);
        donor = donorRepository.save(donor);
        return donorMapper.toDto(donor);
    }

    @Override
    public Optional<DonorDTO> partialUpdate(DonorDTO donorDTO) {
        LOG.debug("Request to partially update Donor : {}", donorDTO);

        return donorRepository
            .findById(donorDTO.getId())
            .map(existingDonor -> {
                donorMapper.partialUpdate(existingDonor, donorDTO);

                return existingDonor;
            })
            .map(donorRepository::save)
            .map(donorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonorDTO> findAll() {
        LOG.debug("Request to get all Donors");
        return donorRepository.findAll().stream().map(donorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<DonorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return donorRepository.findAllWithEagerRelationships(pageable).map(donorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonorDTO> findOne(String id) {
        LOG.debug("Request to get Donor : {}", id);
        return donorRepository.findOneWithEagerRelationships(id).map(donorMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Donor : {}", id);
        donorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Donor> getCurrentDonor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof DonorPrincipal) {
            DonorPrincipal principal = (DonorPrincipal) authentication.getPrincipal();
            return donorRepository.findById(principal.getId());
        }
        return Optional.empty();
    }
}
