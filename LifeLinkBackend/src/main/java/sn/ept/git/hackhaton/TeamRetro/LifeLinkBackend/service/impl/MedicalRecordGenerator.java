package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class MedicalRecordGenerator {

    private static final List<String> MEDICAL_RECORD_TEMPLATES = Arrays.asList(
            // Template 1: Carnet de santé standard
            "{\n" +
                    "  \"infosMedicales\": {\n" +
                    "    \"groupeSanguin\": \"A+\",\n" +
                    "    \"allergies\": [\"Pénicilline\", \"Arachides\"],\n" +
                    "    \"antecedentsMedicaux\": [\"Hypertension\", \"Diabète\"],\n" +
                    "    \"medicamentsActuels\": [\"Metformine\", \"Lisinopril\"],\n" +
                    "    \"conditionsChronic\": [\"Diabète\", \"Hypertension\"]\n" +
                    "  },\n" +
                    "  \"vaccinations\": [\n" +
                    "    {\n" +
                    "      \"vaccin\": \"COVID-19\",\n" +
                    "      \"date\": \"15/06/2021\",\n" +
                    "      \"dose\": \"2\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"vaccin\": \"Grippe\",\n" +
                    "      \"date\": \"10/10/2023\",\n" +
                    "      \"dose\": \"1\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"consultations\": [\n" +
                    "    {\n" +
                    "      \"date\": \"01/10/2023\",\n" +
                    "      \"medecin\": \"Dr. Martin\",\n" +
                    "      \"diagnostic\": \"Hypertension\",\n" +
                    "      \"traitement\": \"Lisinopril 10mg par jour\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"date\": \"15/11/2023\",\n" +
                    "      \"medecin\": \"Dr. Petit\",\n" +
                    "      \"diagnostic\": \"Diabète de type 2\",\n" +
                    "      \"traitement\": \"Metformine 500mg deux fois par jour\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"contactsUrgence\": [\n" +
                    "    {\n" +
                    "      \"nom\": \"Aminata Seye\",\n" +
                    "      \"relation\": \"Épouse\",\n" +
                    "      \"telephone\": \"+221774567548\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"assurance\": {\n" +
                    "    \"fournisseur\": \"Assurance XYZ\",\n" +
                    "    \"numeroPolicee\": \"123456789\",\n" +
                    "    \"couverture\": \"Complète\"\n" +
                    "  },\n" +
                    "  \"modeDeVie\": {\n" +
                    "    \"tabagisme\": \"Non-fumeur\",\n" +
                    "    \"alcool\": \"Non\",\n" +
                    "    \"activitePhysique\": \"Modérée\",\n" +
                    "    \"regime\": \"Végétarien\"\n" +
                    "  },\n" +
                    "  \"appareilsMedicaux\": [\n" +
                    "    {\n" +
                    "      \"appareil\": \"Stimulateur cardiaque\",\n" +
                    "      \"dateImplantation\": \"10/05/2020\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"donOrganes\": {\n" +
                    "    \"statut\": \"Non-consentement\"\n" +
                    "  },\n" +
                    "  \"donSanguin\": {\n" +
                    "    \"eligibilite\": \"Éligible\",\n" +
                    "    \"historiqueDons\": [\n" +
                    "      {\n" +
                    "        \"date\": \"01/10/2023\",\n" +
                    "        \"volume_ml\": 450,\n" +
                    "        \"centre\": \"Banque de Sang de Dakar\",\n" +
                    "        \"statut\": \"Testé et Approuvé\",\n" +
                    "        \"tests\": [\n" +
                    "          {\n" +
                    "            \"test\": \"VIH\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"test\": \"Hépatite B\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"reactionsPostDon\": \"Aucune\",\n" +
                    "        \"conseils\": \"Repos et hydratation\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"journauxAcces\": [\n" +
                    "    {\n" +
                    "      \"horodatage\": \"05/10/2023 10:00:00\",\n" +
                    "      \"accessiblePar\": \"Dr. Martin\",\n" +
                    "      \"objectif\": \"Consultation\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"consentements\": [\n" +
                    "    {\n" +
                    "      \"type\": \"Partage de données\",\n" +
                    "      \"statut\": \"Accordé\",\n" +
                    "      \"date\": \"01/01/2023\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"type\": \"Don de sang\",\n" +
                    "      \"statut\": \"Accordé\",\n" +
                    "      \"date\": \"01/01/2023\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"alertes\": [\n" +
                    "    {\n" +
                    "      \"type\": \"Allergie\",\n" +
                    "      \"description\": \"Allergie sévère à la Pénicilline\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}",

            // Template 2: Carnet de santé jeune donneur
            "{\n" +
                    "  \"infosMedicales\": {\n" +
                    "    \"groupeSanguin\": \"O-\",\n" +
                    "    \"allergies\": [\"Pollen\"],\n" +
                    "    \"antecedentsMedicaux\": [\"Asthme léger\"],\n" +
                    "    \"medicamentsActuels\": [\"Inhalateur d'urgence\"],\n" +
                    "    \"conditionsChronic\": [\"Asthme\"]\n" +
                    "  },\n" +
                    "  \"vaccinations\": [\n" +
                    "    {\n" +
                    "      \"vaccin\": \"COVID-19\",\n" +
                    "      \"date\": \"20/07/2021\",\n" +
                    "      \"dose\": \"3\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"vaccin\": \"Hépatite B\",\n" +
                    "      \"date\": \"15/03/2022\",\n" +
                    "      \"dose\": \"Complète\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"consultations\": [\n" +
                    "    {\n" +
                    "      \"date\": \"10/02/2023\",\n" +
                    "      \"medecin\": \"Dr. Ndiaye\",\n" +
                    "      \"diagnostic\": \"Contrôle asthme\",\n" +
                    "      \"traitement\": \"Poursuivre traitement actuel\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"date\": \"05/09/2023\",\n" +
                    "      \"medecin\": \"Dr. Diouf\",\n" +
                    "      \"diagnostic\": \"Bilan de santé annuel\",\n" +
                    "      \"traitement\": \"RAS\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"contactsUrgence\": [\n" +
                    "    {\n" +
                    "      \"nom\": \"Moussa Diop\",\n" +
                    "      \"relation\": \"Frère\",\n" +
                    "      \"telephone\": \"+221781234567\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"assurance\": {\n" +
                    "    \"fournisseur\": \"Assurance Santé Universelle\",\n" +
                    "    \"numeroPolicee\": \"789456123\",\n" +
                    "    \"couverture\": \"Basique\"\n" +
                    "  },\n" +
                    "  \"modeDeVie\": {\n" +
                    "    \"tabagisme\": \"Non-fumeur\",\n" +
                    "    \"alcool\": \"Occasionnel\",\n" +
                    "    \"activitePhysique\": \"Intense\",\n" +
                    "    \"regime\": \"Équilibré\"\n" +
                    "  },\n" +
                    "  \"appareilsMedicaux\": [],\n" +
                    "  \"donOrganes\": {\n" +
                    "    \"statut\": \"Consentement\"\n" +
                    "  },\n" +
                    "  \"donSanguin\": {\n" +
                    "    \"eligibilite\": \"Éligible\",\n" +
                    "    \"historiqueDons\": [\n" +
                    "      {\n" +
                    "        \"date\": \"15/08/2023\",\n" +
                    "        \"volume_ml\": 450,\n" +
                    "        \"centre\": \"Centre National de Transfusion Sanguine\",\n" +
                    "        \"statut\": \"Testé et Approuvé\",\n" +
                    "        \"tests\": [\n" +
                    "          {\n" +
                    "            \"test\": \"VIH\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"test\": \"Hépatite B\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"test\": \"Paludisme\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"reactionsPostDon\": \"Légère fatigue\",\n" +
                    "        \"conseils\": \"Repos et hydratation accrue\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"date\": \"20/12/2023\",\n" +
                    "        \"volume_ml\": 450,\n" +
                    "        \"centre\": \"Clinique mobile de don\",\n" +
                    "        \"statut\": \"Testé et Approuvé\",\n" +
                    "        \"tests\": [\n" +
                    "          {\n" +
                    "            \"test\": \"VIH\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"test\": \"Hépatite B\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"reactionsPostDon\": \"Aucune\",\n" +
                    "        \"conseils\": \"Continuer activités normales\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"journauxAcces\": [\n" +
                    "    {\n" +
                    "      \"horodatage\": \"20/12/2023 14:30:00\",\n" +
                    "      \"accessiblePar\": \"Infirmière Fatou Sow\",\n" +
                    "      \"objectif\": \"Don de sang\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"consentements\": [\n" +
                    "    {\n" +
                    "      \"type\": \"Partage de données\",\n" +
                    "      \"statut\": \"Accordé\",\n" +
                    "      \"date\": \"01/01/2023\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"type\": \"Don de sang\",\n" +
                    "      \"statut\": \"Accordé\",\n" +
                    "      \"date\": \"01/01/2023\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"type\": \"Don d'organes\",\n" +
                    "      \"statut\": \"Accordé\",\n" +
                    "      \"date\": \"01/01/2023\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"alertes\": []\n" +
                    "}",

            // Template 3: Carnet de santé donneur expérimenté
            "{\n" +
                    "  \"infosMedicales\": {\n" +
                    "    \"groupeSanguin\": \"B+\",\n" +
                    "    \"allergies\": [],\n" +
                    "    \"antecedentsMedicaux\": [\"Appendicectomie (2015)\"],\n" +
                    "    \"medicamentsActuels\": [],\n" +
                    "    \"conditionsChronic\": []\n" +
                    "  },\n" +
                    "  \"vaccinations\": [\n" +
                    "    {\n" +
                    "      \"vaccin\": \"COVID-19\",\n" +
                    "      \"date\": \"05/05/2021\",\n" +
                    "      \"dose\": \"2\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"vaccin\": \"Tétanos\",\n" +
                    "      \"date\": \"12/06/2023\",\n" +
                    "      \"dose\": \"Rappel\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"vaccin\": \"Fièvre jaune\",\n" +
                    "      \"date\": \"20/04/2018\",\n" +
                    "      \"dose\": \"1\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"consultations\": [\n" +
                    "    {\n" +
                    "      \"date\": \"12/06/2023\",\n" +
                    "      \"medecin\": \"Dr. Faye\",\n" +
                    "      \"diagnostic\": \"Bilan de santé général\",\n" +
                    "      \"traitement\": \"Rappel vaccin tétanos\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"contactsUrgence\": [\n" +
                    "    {\n" +
                    "      \"nom\": \"Fatou Ndiaye\",\n" +
                    "      \"relation\": \"Conjointe\",\n" +
                    "      \"telephone\": \"+221762345678\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"nom\": \"Cheikh Diallo\",\n" +
                    "      \"relation\": \"Ami\",\n" +
                    "      \"telephone\": \"+221778901234\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"assurance\": {\n" +
                    "    \"fournisseur\": \"SoliSanté\",\n" +
                    "    \"numeroPolicee\": \"SOL-456789\",\n" +
                    "    \"couverture\": \"Premium\"\n" +
                    "  },\n" +
                    "  \"modeDeVie\": {\n" +
                    "    \"tabagisme\": \"Ancien fumeur (arrêt il y a 5 ans)\",\n" +
                    "    \"alcool\": \"Modéré\",\n" +
                    "    \"activitePhysique\": \"Régulière\",\n" +
                    "    \"regime\": \"Normal\"\n" +
                    "  },\n" +
                    "  \"appareilsMedicaux\": [],\n" +
                    "  \"donOrganes\": {\n" +
                    "    \"statut\": \"Consentement\"\n" +
                    "  },\n" +
                    "  \"donSanguin\": {\n" +
                    "    \"eligibilite\": \"Éligible\",\n" +
                    "    \"historiqueDons\": [\n" +
                    "      {\n" +
                    "        \"date\": \"10/03/2022\",\n" +
                    "        \"volume_ml\": 450,\n" +
                    "        \"centre\": \"Hôpital Principal de Dakar\",\n" +
                    "        \"statut\": \"Testé et Approuvé\",\n" +
                    "        \"tests\": [\n" +
                    "          {\n" +
                    "            \"test\": \"VIH\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"test\": \"Hépatite B\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"reactionsPostDon\": \"Aucune\",\n" +
                    "        \"conseils\": \"RAS\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"date\": \"15/06/2022\",\n" +
                    "        \"volume_ml\": 450,\n" +
                    "        \"centre\": \"Centre National de Transfusion Sanguine\",\n" +
                    "        \"statut\": \"Testé et Approuvé\",\n" +
                    "        \"tests\": [\n" +
                    "          {\n" +
                    "            \"test\": \"VIH\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"test\": \"Hépatite B\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"reactionsPostDon\": \"Aucune\",\n" +
                    "        \"conseils\": \"RAS\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"date\": \"20/09/2022\",\n" +
                    "        \"volume_ml\": 450,\n" +
                    "        \"centre\": \"Clinique mobile de don\",\n" +
                    "        \"statut\": \"Testé et Approuvé\",\n" +
                    "        \"tests\": [\n" +
                    "          {\n" +
                    "            \"test\": \"VIH\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"test\": \"Hépatite B\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"reactionsPostDon\": \"Aucune\",\n" +
                    "        \"conseils\": \"RAS\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"date\": \"25/12/2022\",\n" +
                    "        \"volume_ml\": 450,\n" +
                    "        \"centre\": \"Hôpital Principal de Dakar\",\n" +
                    "        \"statut\": \"Testé et Approuvé\",\n" +
                    "        \"tests\": [\n" +
                    "          {\n" +
                    "            \"test\": \"VIH\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"test\": \"Hépatite B\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"reactionsPostDon\": \"Aucune\",\n" +
                    "        \"conseils\": \"RAS\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"date\": \"30/03/2023\",\n" +
                    "        \"volume_ml\": 450,\n" +
                    "        \"centre\": \"Centre National de Transfusion Sanguine\",\n" +
                    "        \"statut\": \"Testé et Approuvé\",\n" +
                    "        \"tests\": [\n" +
                    "          {\n" +
                    "            \"test\": \"VIH\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"test\": \"Hépatite B\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"reactionsPostDon\": \"Aucune\",\n" +
                    "        \"conseils\": \"RAS\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"date\": \"05/07/2023\",\n" +
                    "        \"volume_ml\": 450,\n" +
                    "        \"centre\": \"Clinique mobile de don\",\n" +
                    "        \"statut\": \"Testé et Approuvé\",\n" +
                    "        \"tests\": [\n" +
                    "          {\n" +
                    "            \"test\": \"VIH\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"test\": \"Hépatite B\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"reactionsPostDon\": \"Aucune\",\n" +
                    "        \"conseils\": \"RAS\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"date\": \"10/11/2023\",\n" +
                    "        \"volume_ml\": 450,\n" +
                    "        \"centre\": \"Hôpital Principal de Dakar\",\n" +
                    "        \"statut\": \"Testé et Approuvé\",\n" +
                    "        \"tests\": [\n" +
                    "          {\n" +
                    "            \"test\": \"VIH\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"test\": \"Hépatite B\",\n" +
                    "            \"resultat\": \"Négatif\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"reactionsPostDon\": \"Aucune\",\n" +
                    "        \"conseils\": \"RAS\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"journauxAcces\": [\n" +
                    "    {\n" +
                    "      \"horodatage\": \"10/11/2023 11:15:00\",\n" +
                    "      \"accessiblePar\": \"Dr. Sall\",\n" +
                    "      \"objectif\": \"Don de sang\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"horodatage\": \"12/06/2023 09:30:00\",\n" +
                    "      \"accessiblePar\": \"Dr. Faye\",\n" +
                    "      \"objectif\": \"Consultation de routine\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"consentements\": [\n" +
                    "    {\n" +
                    "      \"type\": \"Partage de données\",\n" +
                    "      \"statut\": \"Accordé\",\n" +
                    "      \"date\": \"01/01/2023\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"type\": \"Don de sang\",\n" +
                    "      \"statut\": \"Accordé\",\n" +
                    "      \"date\": \"01/01/2022\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"type\": \"Don d'organes\",\n" +
                    "      \"statut\": \"Accordé\",\n" +
                    "      \"date\": \"01/01/2022\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"type\": \"Participation recherche médicale\",\n" +
                    "      \"statut\": \"Accordé\",\n" +
                    "      \"date\": \"10/06/2023\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"alertes\": []\n" +
                    "}"
    );

    /**
     * Génère aléatoirement un carnet médical parmi les 3 modèles disponibles
     *
     * @return String - Le carnet médical au format JSON
     */
    public String generateRandomMedicalRecord() {
        Random random = new Random();
        int templateIndex = random.nextInt(MEDICAL_RECORD_TEMPLATES.size());
        return MEDICAL_RECORD_TEMPLATES.get(templateIndex);
    }

    /**
     * Met à jour le groupe sanguin dans le carnet médical pour correspondre au BloodType et ResusType du donneur
     *
     * @param medicalRecord Le carnet médical au format JSON
     * @param bloodType Le groupe sanguin (A, B, AB, O)
     * @param resusType Le rhésus (+, -)
     * @return String - Le carnet médical mis à jour
     */
    public String updateBloodType(String medicalRecord, String bloodType, String resusType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var jsonNode = objectMapper.readTree(medicalRecord);

            // Mettre à jour le groupe sanguin dans le carnet médical
            String fullBloodType = bloodType + resusType;
            ((com.fasterxml.jackson.databind.node.ObjectNode) jsonNode.get("infosMedicales"))
                    .put("groupeSanguin", fullBloodType);

            return objectMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            // En cas d'erreur, renvoyer le carnet médical original
            return medicalRecord;
        }
    }
}