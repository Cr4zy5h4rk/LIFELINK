package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import java.util.Objects;

public class ShareInfoSelectionDTO {
    // Infos personnelles du donneur
    private boolean shareDateNaissance;
    private boolean shareAddress;
    private boolean shareEmail;

    // Infos médicales
    private boolean shareInfosMedicales;
    private boolean shareVaccinations;
    private boolean shareConsultations;
    private boolean shareAppareilsMedicaux;
    private boolean shareDonSanguin;
    private boolean shareAlertes;
    private boolean shareContactsUrgence;
    private boolean shareAssurance;
    private boolean shareModeDeVie;

    public boolean isShareDateNaissance() {
        return shareDateNaissance;
    }

    public void setShareDateNaissance(boolean shareDateNaissance) {
        this.shareDateNaissance = shareDateNaissance;
    }

    public boolean isShareAddress() {
        return shareAddress;
    }

    public void setShareAddress(boolean shareAddress) {
        this.shareAddress = shareAddress;
    }

    public boolean isShareEmail() {
        return shareEmail;
    }

    public void setShareEmail(boolean shareEmail) {
        this.shareEmail = shareEmail;
    }

    public boolean isShareInfosMedicales() {
        return shareInfosMedicales;
    }

    public void setShareInfosMedicales(boolean shareInfosMedicales) {
        this.shareInfosMedicales = shareInfosMedicales;
    }

    public boolean isShareVaccinations() {
        return shareVaccinations;
    }

    public void setShareVaccinations(boolean shareVaccinations) {
        this.shareVaccinations = shareVaccinations;
    }

    public boolean isShareConsultations() {
        return shareConsultations;
    }

    public void setShareConsultations(boolean shareConsultations) {
        this.shareConsultations = shareConsultations;
    }

    public boolean isShareAppareilsMedicaux() {
        return shareAppareilsMedicaux;
    }

    public void setShareAppareilsMedicaux(boolean shareAppareilsMedicaux) {
        this.shareAppareilsMedicaux = shareAppareilsMedicaux;
    }

    public boolean isShareDonSanguin() {
        return shareDonSanguin;
    }

    public void setShareDonSanguin(boolean shareDonSanguin) {
        this.shareDonSanguin = shareDonSanguin;
    }

    public boolean isShareAlertes() {
        return shareAlertes;
    }

    public void setShareAlertes(boolean shareAlertes) {
        this.shareAlertes = shareAlertes;
    }

    public boolean isShareContactsUrgence() {
        return shareContactsUrgence;
    }

    public void setShareContactsUrgence(boolean shareContactsUrgence) {
        this.shareContactsUrgence = shareContactsUrgence;
    }

    public boolean isShareAssurance() {
        return shareAssurance;
    }

    public void setShareAssurance(boolean shareAssurance) {
        this.shareAssurance = shareAssurance;
    }

    public boolean isShareModeDeVie() {
        return shareModeDeVie;
    }

    public void setShareModeDeVie(boolean shareModeDeVie) {
        this.shareModeDeVie = shareModeDeVie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShareInfoSelectionDTO that = (ShareInfoSelectionDTO) o;
        return shareDateNaissance == that.shareDateNaissance && shareAddress == that.shareAddress && shareEmail == that.shareEmail && shareInfosMedicales == that.shareInfosMedicales && shareVaccinations == that.shareVaccinations && shareConsultations == that.shareConsultations && shareAppareilsMedicaux == that.shareAppareilsMedicaux && shareDonSanguin == that.shareDonSanguin && shareAlertes == that.shareAlertes && shareContactsUrgence == that.shareContactsUrgence && shareAssurance == that.shareAssurance && shareModeDeVie == that.shareModeDeVie;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shareDateNaissance, shareAddress, shareEmail, shareInfosMedicales, shareVaccinations, shareConsultations, shareAppareilsMedicaux, shareDonSanguin, shareAlertes, shareContactsUrgence, shareAssurance, shareModeDeVie);
    }

    @Override
    public String toString() {
        return "ShareInfoSelectionDTO{" +
                "shareDateNaissance=" + shareDateNaissance +
                ", shareAddress=" + shareAddress +
                ", shareEmail=" + shareEmail +
                ", shareInfosMedicales=" + shareInfosMedicales +
                ", shareVaccinations=" + shareVaccinations +
                ", shareConsultations=" + shareConsultations +
                ", shareAppareilsMedicaux=" + shareAppareilsMedicaux +
                ", shareDonSanguin=" + shareDonSanguin +
                ", shareAlertes=" + shareAlertes +
                ", shareContactsUrgence=" + shareContactsUrgence +
                ", shareAssurance=" + shareAssurance +
                ", shareModeDeVie=" + shareModeDeVie +
                '}';
    }
}