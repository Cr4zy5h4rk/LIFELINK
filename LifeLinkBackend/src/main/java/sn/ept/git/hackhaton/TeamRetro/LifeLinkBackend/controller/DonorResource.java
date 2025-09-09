package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.DonorRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.DonorService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.EsignetDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.DonorMapperImpl;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "donor")
public class DonorResource {

    private final DonorRepository donorRepository;
    private final DonorService donorService;
    private final DonorMapperImpl donorMapperImpl;

    public DonorResource(
            DonorRepository donorRepository,
            DonorService donorService,
            DonorMapperImpl donorMapperImpl) {
        this.donorRepository = donorRepository;
        this.donorService = donorService;
        this.donorMapperImpl = donorMapperImpl;
    }

    @GetMapping(path = "all")
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Donor> getDonorById(@PathVariable String id) {
        return donorRepository.findById(id);
    }

    @PostMapping("saveDonor")
    public ResponseEntity<DonorDTO> saveDonor(@RequestBody DonorDTO donor) {
        if (donorRepository.findByPhoneNumber(donor.getPhoneNumber()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        donor =  donorService.save(donor);
        return ResponseEntity.ok(donor);
    }

    @PostMapping("saveEsignetDonor")
    public ResponseEntity<DonorDTO> saveEsignetDonor(@RequestBody EsignetDTO donor) {
        if (donorRepository.findByPhoneNumber(donor.getPhone_number()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        DonorDTO user = donorService.saveEsignet(donor);
        return ResponseEntity.ok(user);
    }
}
