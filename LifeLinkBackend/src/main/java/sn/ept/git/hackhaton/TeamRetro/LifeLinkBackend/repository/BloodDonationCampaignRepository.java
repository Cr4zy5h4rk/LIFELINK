package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.BloodDonationCampaign;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.CampaignStatus;

import java.util.List;

/**
 * Spring Data JPA repository for the BloodDonationCampaign entity.
 */
@Repository
public interface BloodDonationCampaignRepository extends JpaRepository<BloodDonationCampaign, Long> {

    /**
     * Find all campaigns with a specific status.
     *
     * @param status the status to filter by
     * @return the list of campaigns
     */
    List<BloodDonationCampaign> findByStatus(CampaignStatus status);


    /**
     * Trouve les 10 dernières campagnes dont le statut est dans la liste fournie,
     * triées par date de création décroissante
     * @param statuses Liste des statuts à inclure dans la recherche
     * @return Liste des 10 dernières campagnes correspondant aux statuts
     */
    List<BloodDonationCampaign> findTop10ByStatusInOrderByCreatedAtDesc(List<CampaignStatus> statuses);
}