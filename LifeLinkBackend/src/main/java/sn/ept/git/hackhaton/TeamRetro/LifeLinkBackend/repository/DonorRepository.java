package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;

/**
 * Spring Data JPA repository for the Donor entity.
 *
 * When extending this class, extend DonorRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DonorRepository extends DonorRepositoryWithBagRelationships, JpaRepository<Donor, String> {
    default Optional<Donor> findOneWithEagerRelationships(String id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Donor> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Donor> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    Optional<Donor> findByPhoneNumber(String phoneNumber);

    Optional<Donor> findByEmail(String email);
}
