package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;

public interface DonorRepositoryWithBagRelationships {
    Optional<Donor> fetchBagRelationships(Optional<Donor> donor);

    List<Donor> fetchBagRelationships(List<Donor> donors);

    Page<Donor> fetchBagRelationships(Page<Donor> donors);
}
