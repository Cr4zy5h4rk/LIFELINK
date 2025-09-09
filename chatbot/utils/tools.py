from langchain.tools import tool
from loguru import logger



@tool
def compatibilite_groupes(groupe_donneur: str):
    """Indique qui peut recevoir du sang d'un certain groupe sanguin.

    Args:
        groupe_donneur: Le groupe sanguin du donneur (A+, A-, B+, B-, AB+, AB-, O+, O-)
    """
    logger.info(f"Recherche de compatibilité pour groupe={groupe_donneur}")

    # Normalisation du groupe sanguin
    groupe_donneur = groupe_donneur.upper().replace(" ", "")

    # Dictionnaire simple de compatibilité
    compatibilite = {
        "O-": "Peut donner à tous les groupes (O-, O+, A-, A+, B-, B+, AB-, AB+)",
        "O+": "Peut donner à O+, A+, B+, AB+",
        "A-": "Peut donner à A-, A+, AB-, AB+",
        "A+": "Peut donner à A+, AB+",
        "B-": "Peut donner à B-, B+, AB-, AB+",
        "B+": "Peut donner à B+, AB+",
        "AB-": "Peut donner à AB-, AB+",
        "AB+": "Peut donner uniquement à AB+"
    }

    return compatibilite.get(groupe_donneur, "Groupe sanguin non reconnu. Utilisez A+, A-, B+, B-, AB+, AB-, O+, O-")


@tool
def centre_don_info(region: str = None):
    """
    Fournit des informations sur les centres de don de sang disponibles au Sénégal.

    Si une région ou ville est précisée (ex: 'Dakar', 'Thiès'), retourne le nom,
    l'adresse et les horaires d'ouverture du centre correspondant.
    Si aucune région n'est précisée, retourne la liste des régions disponibles.

    Args:
        region: Nom de la région ou ville (ex: 'Dakar', 'Thiès'). Ce paramètre est optionnel.
    """
    logger.info(f"Recherche de centres pour région={region}")

    # Dictionnaire simple des centres
    centres = {
        "dakar": "Centre National de Transfusion Sanguine de Dakar, Avenue Cheikh Anta Diop, ouvert Lun-Ven 8h-17h, Sam 9h-15h",
        "thies": "Centre de Don de Sang de Thiès, Quartier 10ème, ouvert Lun-Ven 8h30-16h30"
    }

    if not region:
        return "Centres disponibles: Dakar, Thiès"

    region_norm = region.lower().replace(" ", "")
    return centres.get(region_norm, f"Aucun centre trouvé à {region}. Centres disponibles: Dakar, Thiès")