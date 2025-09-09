package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DonorRepositoryWithBagRelationshipsImpl implements DonorRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String DONORS_PARAMETER = "donors";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Donor> fetchBagRelationships(Optional<Donor> donor) {
        return donor.map(this::fetchFavorites).map(this::fetchRoles);
    }

    @Override
    public Page<Donor> fetchBagRelationships(Page<Donor> donors) {
        return new PageImpl<>(fetchBagRelationships(donors.getContent()), donors.getPageable(), donors.getTotalElements());
    }

    @Override
    public List<Donor> fetchBagRelationships(List<Donor> donors) {
        return Optional.of(donors).map(this::fetchFavorites).map(this::fetchRoles).orElse(Collections.emptyList());
    }

    Donor fetchFavorites(Donor result) {
        return entityManager
            .createQuery("select donor from Donor donor left join fetch donor.favorites where donor.id = :id", Donor.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Donor> fetchFavorites(List<Donor> donors) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, donors.size()).forEach(index -> order.put(donors.get(index).getId(), index));
        List<Donor> result = entityManager
            .createQuery("select donor from Donor donor left join fetch donor.favorites where donor in :donors", Donor.class)
            .setParameter(DONORS_PARAMETER, donors)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Donor fetchRoles(Donor result) {
        return entityManager
            .createQuery("select donor from Donor donor left join fetch donor.roles where donor.id = :id", Donor.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Donor> fetchRoles(List<Donor> donors) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, donors.size()).forEach(index -> order.put(donors.get(index).getId(), index));
        List<Donor> result = entityManager
            .createQuery("select donor from Donor donor left join fetch donor.roles where donor in :donors", Donor.class)
            .setParameter(DONORS_PARAMETER, donors)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
