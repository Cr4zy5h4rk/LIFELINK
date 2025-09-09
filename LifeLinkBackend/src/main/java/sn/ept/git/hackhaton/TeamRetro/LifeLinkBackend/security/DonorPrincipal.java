package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DonorPrincipal implements UserDetails {

    private final String id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Collection<? extends GrantedAuthority> authorities;

    public DonorPrincipal(
            String id,
            String email,
            String firstName,
            String lastName,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
    }

    public static DonorPrincipal create(Donor donor) {
        // Note: If you have roles in your Donor entity, map them to authorities here
        // For example:
        // List<GrantedAuthority> authorities = donor.getRoles().stream()
        //         .map(role -> new SimpleGrantedAuthority(role.getName()))
        //         .collect(Collectors.toList());

        // If no roles, use a default role
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new DonorPrincipal(
                donor.getId(),
                donor.getEmail(),
                donor.getFirstName(),
                donor.getLastName(),
                authorities
        );
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        // OAuth2 authentication doesn't use passwords directly
        return null;
    }

    @Override
    public String getUsername() {
        // Using email as the username
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DonorPrincipal that = (DonorPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}