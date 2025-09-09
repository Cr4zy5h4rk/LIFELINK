package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.ShareInfoRequest;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.EligibilityStatus;

// Classe DTO pour l'éligibilité
public class EligibilityStatusDTO {
    private EligibilityStatus eligibilityStatus;

    public EligibilityStatus getEligibilityStatus() {
        return eligibilityStatus;
    }

    public void setEligibilityStatus(EligibilityStatus eligibilityStatus) {
        this.eligibilityStatus = eligibilityStatus;
    }
}
