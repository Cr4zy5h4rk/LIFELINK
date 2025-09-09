package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.DonorRepository;

@Service
public class DonorDetailsService implements UserDetailsService {

    private final DonorRepository donorRepository;

    public DonorDetailsService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Load donor by email
        Donor donor = donorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return DonorPrincipal.create(donor);
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(String id) {
        // Load donor by ID
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return DonorPrincipal.create(donor);
    }
}