package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.DonorRepository;

import java.io.IOException;

@Service
public class DonorMedicalService {

    private final DonorRepository donorRepository;
    private final ObjectMapper objectMapper;

    public DonorMedicalService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Récupère les informations médicales complètes d'un donneur
     */
    public JsonNode getMedicalData(String donorId) {
        Donor donor = donorRepository.findById(donorId).orElseThrow(() ->
                new RuntimeException("Donneur non trouvé avec ID: " + donorId));

        return donor.getMedicalDataAsJson();
    }

    /**
     * Met à jour les données médicales complètes d'un donneur
     */
    public Donor updateMedicalData(String donorId, String jsonData) {
        Donor donor = donorRepository.findById(donorId).orElseThrow(() ->
                new RuntimeException("Donneur non trouvé avec ID: " + donorId));

        donor.setMedicalData(jsonData);
        return donorRepository.save(donor);
    }

    /**
     * Récupère une section spécifique des informations médicales
     */
    public JsonNode getMedicalSection(String donorId, String sectionName) {
        Donor donor = donorRepository.findById(donorId).orElseThrow(() ->
                new RuntimeException("Donneur non trouvé avec ID: " + donorId));

        JsonNode medicalData = donor.getMedicalDataAsJson();
        return medicalData != null ? medicalData.get(sectionName) : null;
    }

    /**
     * Met à jour une section spécifique des informations médicales
     */
    public Donor updateMedicalSection(String donorId, String sectionName, JsonNode sectionData) {
        Donor donor = donorRepository.findById(donorId).orElseThrow(() ->
                new RuntimeException("Donneur non trouvé avec ID: " + donorId));

        try {
            JsonNode currentData = donor.getMedicalDataAsJson();
            ObjectNode rootNode;

            if (currentData != null && !currentData.isNull()) {
                rootNode = (ObjectNode) currentData;
            } else {
                rootNode = objectMapper.createObjectNode();
            }

            rootNode.set(sectionName, sectionData);
            donor.setMedicalData(objectMapper.writeValueAsString(rootNode));

            return donorRepository.save(donor);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la mise à jour des données médicales", e);
        }
    }

    /**
     * Ajoute une nouvelle entrée à une liste existante (par exemple, une nouvelle vaccination)
     */
    public Donor addToMedicalList(String donorId, String listPath, JsonNode newItem) {
        Donor donor = donorRepository.findById(donorId).orElseThrow(() ->
                new RuntimeException("Donneur non trouvé avec ID: " + donorId));

        try {
            JsonNode currentData = donor.getMedicalDataAsJson();
            if (currentData == null || currentData.isNull()) {
                throw new RuntimeException("Aucune donnée médicale trouvée pour ce donneur");
            }

            // Parse le chemin (par exemple "vaccinations")
            ObjectNode rootNode = (ObjectNode) currentData;
            JsonNode targetList = rootNode.get(listPath);

            if (targetList == null || !targetList.isArray()) {
                throw new RuntimeException("La liste spécifiée n'existe pas ou n'est pas un tableau");
            }

            // Ajoute l'élément à la liste
            ObjectNode mutableRoot = rootNode.deepCopy();
            com.fasterxml.jackson.databind.node.ArrayNode mutableList =
                    (com.fasterxml.jackson.databind.node.ArrayNode) mutableRoot.get(listPath);
            mutableList.add(newItem);

            // Sauvegarde les modifications
            donor.setMedicalData(objectMapper.writeValueAsString(mutableRoot));
            return donorRepository.save(donor);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'ajout à la liste médicale", e);
        }
    }

    /**
     * Exemple: ajouter une nouvelle vaccination
     */
    public Donor addVaccination(String donorId, String vaccin, String date, String dose) {
        ObjectNode newVaccination = objectMapper.createObjectNode();
        newVaccination.put("vaccin", vaccin);
        newVaccination.put("date", date);
        newVaccination.put("dose", dose);

        return addToMedicalList(donorId, "vaccinations", newVaccination);
    }

    /**
     * Exemple: ajouter une nouvelle consultation
     */
    public Donor addConsultation(String donorId, String date, String medecin, String diagnostic, String traitement) {
        ObjectNode newConsultation = objectMapper.createObjectNode();
        newConsultation.put("date", date);
        newConsultation.put("medecin", medecin);
        newConsultation.put("diagnostic", diagnostic);
        newConsultation.put("traitement", traitement);

        return addToMedicalList(donorId, "consultations", newConsultation);
    }

    /**
     * Exemple: ajouter un contact d'urgence
     */
    public Donor addContactUrgence(String donorId, String nom, String relation, String telephone) {
        ObjectNode newContact = objectMapper.createObjectNode();
        newContact.put("nom", nom);
        newContact.put("relation", relation);
        newContact.put("telephone", telephone);

        return addToMedicalList(donorId, "contactsUrgence", newContact);
    }

    /**
     * Exemple: mettre à jour les infos médicales de base
     */
    public Donor updateInfosMedicales(String donorId, String groupeSanguin, String[] allergies,
                                      String[] antecedentsMedicaux, String[] medicamentsActuels,
                                      String[] conditionsChronic) {
        try {
            ObjectNode infosMedicales = objectMapper.createObjectNode();
            infosMedicales.put("groupeSanguin", groupeSanguin);

            // Conversion des tableaux en arrays JSON
            infosMedicales.set("allergies", objectMapper.valueToTree(allergies));
            infosMedicales.set("antecedentsMedicaux", objectMapper.valueToTree(antecedentsMedicaux));
            infosMedicales.set("medicamentsActuels", objectMapper.valueToTree(medicamentsActuels));
            infosMedicales.set("conditionsChronic", objectMapper.valueToTree(conditionsChronic));

            return updateMedicalSection(donorId, "infosMedicales", infosMedicales);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour des informations médicales", e);
        }
    }

    /**
     * Mise à jour des informations d'assurance
     */
    public Donor updateAssurance(String donorId, String fournisseur, String numeroPolicee, String couverture) {
        ObjectNode assurance = objectMapper.createObjectNode();
        assurance.put("fournisseur", fournisseur);
        assurance.put("numeroPolicee", numeroPolicee);
        assurance.put("couverture", couverture);

        return updateMedicalSection(donorId, "assurance", assurance);
    }

    /**
     * Mise à jour du mode de vie
     */
    public Donor updateModeDeVie(String donorId, String tabagisme, String alcool, String activitePhysique, String regime) {
        ObjectNode modeDeVie = objectMapper.createObjectNode();
        modeDeVie.put("tabagisme", tabagisme);
        modeDeVie.put("alcool", alcool);
        modeDeVie.put("activitePhysique", activitePhysique);
        modeDeVie.put("regime", regime);

        return updateMedicalSection(donorId, "modeDeVie", modeDeVie);
    }

    /**
     * Initialiser les données médicales à partir d'un JSON complet
     */
    public Donor initializeMedicalData(String donorId, String fullJsonData) {
        Donor donor = donorRepository.findById(donorId).orElseThrow(() ->
                new RuntimeException("Donneur non trouvé avec ID: " + donorId));

        donor.setMedicalData(fullJsonData);
        return donorRepository.save(donor);
    }
}