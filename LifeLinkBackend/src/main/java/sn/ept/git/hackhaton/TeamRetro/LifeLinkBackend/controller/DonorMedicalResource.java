package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.DonorRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl.DonorMedicalService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller pour gérer les données médicales des donneurs.
 */
@RestController
public class DonorMedicalResource {

    private final Logger log = LoggerFactory.getLogger(DonorMedicalResource.class);
    private final DonorMedicalService donorMedicalService;
    private final ObjectMapper objectMapper;
    private final DonorRepository donorRepository;

    public DonorMedicalResource(DonorMedicalService donorMedicalService, DonorRepository donorRepository) {
        this.donorMedicalService = donorMedicalService;
        this.objectMapper = new ObjectMapper();
        this.donorRepository = donorRepository;
    }

    /**
     * {@code GET /donors/:id/medical-data} : récupérer toutes les données médicales d'un donneur.
     *
     * @param id l'ID du donneur.
     * @return le {@link ResponseEntity} avec status {@code 200 (OK)} et les données médicales.
     */
    @GetMapping("/donors/{id}/medical-data")
    public ResponseEntity<JsonNode> getMedicalData(@PathVariable String id) {
        log.debug("REST request to get medical data for Donor : {}", id);
        JsonNode medicalData = donorMedicalService.getMedicalData(id);
        return ResponseEntity.ok().body(medicalData);
    }

    /**
     * {@code PUT /donors/:id/medical-data} : mettre à jour toutes les données médicales d'un donneur.
     *
     * @param id l'ID du donneur.
     * @param medicalData les nouvelles données médicales.
     * @return le {@link ResponseEntity} avec status {@code 200 (OK)}.
     */
    @PutMapping("/donors/{id}/medical-data")
    public ResponseEntity<Void> updateMedicalData(
            @PathVariable String id,
            @RequestBody String medicalData
    ) {
        log.debug("REST request to update medical data for Donor : {}", id);
        donorMedicalService.updateMedicalData(id, medicalData);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code GET /donors/:id/medical-data/:section} : récupérer une section spécifique des données médicales.
     *
     * @param id l'ID du donneur.
     * @param section la section à récupérer.
     * @return le {@link ResponseEntity} avec status {@code 200 (OK)} et la section demandée.
     */
    @GetMapping("/donors/{id}/medical-data/{section}")
    public ResponseEntity<JsonNode> getMedicalSection(
            @PathVariable String id,
            @PathVariable String section
    ) {
        log.debug("REST request to get medical section {} for Donor : {}", section, id);
        JsonNode sectionData = donorMedicalService.getMedicalSection(id, section);
        return ResponseEntity.ok().body(sectionData);
    }

    /**
     * {@code PUT /donors/:id/medical-data/:section} : mettre à jour une section spécifique des données médicales.
     *
     * @param id l'ID du donneur.
     * @param section la section à mettre à jour.
     * @param sectionData les nouvelles données pour cette section.
     * @return le {@link ResponseEntity} avec status {@code 200 (OK)}.
     */
    @PutMapping("/donors/{id}/medical-data/{section}")
    public ResponseEntity<Void> updateMedicalSection(
            @PathVariable String id,
            @PathVariable String section,
            @RequestBody JsonNode sectionData
    ) {
        log.debug("REST request to update medical section {} for Donor : {}", section, id);
        donorMedicalService.updateMedicalSection(id, section, sectionData);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code POST /donors/:id/vaccinations} : ajouter une nouvelle vaccination.
     *
     * @param id l'ID du donneur.
     * @param vaccination les données de la vaccination.
     * @return le {@link ResponseEntity} avec status {@code 201 (Created)}.
     * @throws URISyntaxException si l'URI de location est incorrecte.
     */
    @PostMapping("/donors/{id}/vaccinations")
    public ResponseEntity<Void> addVaccination(
            @PathVariable String id,
            @RequestBody VaccinationDTO vaccination
    ) throws URISyntaxException {
        log.debug("REST request to add vaccination for Donor : {}", id);
        donorMedicalService.addVaccination(id, vaccination.getVaccin(), vaccination.getDate(), vaccination.getDose());
        return ResponseEntity.created(new URI("/api/donors/" + id + "/vaccinations")).build();
    }

    @PostMapping("/donors/{id}/medical-data")
    public ResponseEntity<Void> initMedicalData(
            @PathVariable String id,
            @RequestBody String medicalData
    ) throws URISyntaxException {
        log.debug("REST request to initialize medical data for Donor : {}", id);
        donorMedicalService.initializeMedicalData(id, medicalData);
        return ResponseEntity.created(new URI("/api/donors/" + id + "/medical-data")).build();
    }

    /**
     * {@code POST /donors/:id/consultations} : ajouter une nouvelle consultation.
     *
     * @param id l'ID du donneur.
     * @param consultation les données de la consultation.
     * @return le {@link ResponseEntity} avec status {@code 201 (Created)}.
     * @throws URISyntaxException si l'URI de location est incorrecte.
     */
    @PostMapping("/donors/{id}/consultations")
    public ResponseEntity<Void> addConsultation(
            @PathVariable String id,
            @RequestBody ConsultationDTO consultation
    ) throws URISyntaxException {
        log.debug("REST request to add consultation for Donor : {}", id);
        donorMedicalService.addConsultation(
                id,
                consultation.getDate(),
                consultation.getMedecin(),
                consultation.getDiagnostic(),
                consultation.getTraitement()
        );
        return ResponseEntity.created(new URI("/api/donors/" + id + "/consultations")).build();
    }

    /**
     * {@code POST /donors/:id/contacts-urgence} : ajouter un nouveau contact d'urgence.
     *
     * @param id l'ID du donneur.
     * @param contact les données du contact.
     * @return le {@link ResponseEntity} avec status {@code 201 (Created)}.
     * @throws URISyntaxException si l'URI de location est incorrecte.
     */
    @PostMapping("/donors/{id}/contacts-urgence")
    public ResponseEntity<Void> addContactUrgence(
            @PathVariable String id,
            @RequestBody ContactUrgenceDTO contact
    ) throws URISyntaxException {
        log.debug("REST request to add emergency contact for Donor : {}", id);
        donorMedicalService.addContactUrgence(
                id,
                contact.getNom(),
                contact.getRelation(),
                contact.getTelephone()
        );
        return ResponseEntity.created(new URI("/api/donors/" + id + "/contacts-urgence")).build();
    }

    /**
     * {@code PUT /donors/:id/infos-medicales} : mettre à jour les informations médicales de base.
     *
     * @param id l'ID du donneur.
     * @param infosMedicales les nouvelles informations médicales.
     * @return le {@link ResponseEntity} avec status {@code 200 (OK)}.
     */
    @PutMapping("/donors/{id}/infos-medicales")
    public ResponseEntity<Void> updateInfosMedicales(
            @PathVariable String id,
            @RequestBody InfosMedicalesDTO infosMedicales
    ) {
        log.debug("REST request to update basic medical info for Donor : {}", id);
        donorMedicalService.updateInfosMedicales(
                id,
                infosMedicales.getGroupeSanguin(),
                infosMedicales.getAllergies(),
                infosMedicales.getAntecedentsMedicaux(),
                infosMedicales.getMedicamentsActuels(),
                infosMedicales.getConditionsChronic()
        );
        return ResponseEntity.ok().build();
    }

    /**
     * Classes DTO pour faciliter le transfert des données
     */
    public static class VaccinationDTO {
        private String vaccin;
        private String date;
        private String dose;

        public String getVaccin() { return vaccin; }
        public void setVaccin(String vaccin) { this.vaccin = vaccin; }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public String getDose() { return dose; }
        public void setDose(String dose) { this.dose = dose; }
    }

    public static class ConsultationDTO {
        private String date;
        private String medecin;
        private String diagnostic;
        private String traitement;

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public String getMedecin() { return medecin; }
        public void setMedecin(String medecin) { this.medecin = medecin; }
        public String getDiagnostic() { return diagnostic; }
        public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }
        public String getTraitement() { return traitement; }
        public void setTraitement(String traitement) { this.traitement = traitement; }
    }

    public static class ContactUrgenceDTO {
        private String nom;
        private String relation;
        private String telephone;

        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public String getRelation() { return relation; }
        public void setRelation(String relation) { this.relation = relation; }
        public String getTelephone() { return telephone; }
        public void setTelephone(String telephone) { this.telephone = telephone; }
    }

    public static class InfosMedicalesDTO {
        private String groupeSanguin;
        private String[] allergies;
        private String[] antecedentsMedicaux;
        private String[] medicamentsActuels;
        private String[] conditionsChronic;

        public String getGroupeSanguin() { return groupeSanguin; }
        public void setGroupeSanguin(String groupeSanguin) { this.groupeSanguin = groupeSanguin; }
        public String[] getAllergies() { return allergies; }
        public void setAllergies(String[] allergies) { this.allergies = allergies; }
        public String[] getAntecedentsMedicaux() { return antecedentsMedicaux; }
        public void setAntecedentsMedicaux(String[] antecedentsMedicaux) { this.antecedentsMedicaux = antecedentsMedicaux; }
        public String[] getMedicamentsActuels() { return medicamentsActuels; }
        public void setMedicamentsActuels(String[] medicamentsActuels) { this.medicamentsActuels = medicamentsActuels; }
        public String[] getConditionsChronic() { return conditionsChronic; }
        public void setConditionsChronic(String[] conditionsChronic) { this.conditionsChronic = conditionsChronic; }
    }
}