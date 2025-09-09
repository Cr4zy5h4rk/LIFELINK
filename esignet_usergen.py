import requests
import json
import datetime
import time

# URL pour la création d'utilisateurs
URL = "http://localhost:8082/v1/mock-identity-system/identity"

# Liste des IDs et noms d'utilisateurs à créer
users = [
    {
        "id": "8267411576",
        "first_name": "Aminata",
        "last_name": "Lo", 
        "email": "aminataaaa.lo@gmail.com",
        "phone": "+221771234567",
        "gender": "Female"
    },
    {
        "id": "8267411577",
        "first_name": "Moussa",
        "last_name": "Diop",
        "email": "moussa.diop@gmail.com",
        "phone": "+221772345678",
        "gender": "Male"
    },
    {
        "id": "8267411578",
        "first_name": "Fatou",
        "last_name": "Ndiaye",
        "email": "fatou.ndiaye@gmail.com",
        "phone": "+221773456789",
        "gender": "Female"
    }
]

# Fonction pour créer un utilisateur
def create_user(user_data):
    # Obtenir l'horodatage ISO
    iso_timestamp = datetime.datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%S.000Z")
    
    # Créer le corps de la requête
    request_body = {
        "requestTime": iso_timestamp,
        "request": {
            "individualId": user_data["id"],
            "pin": "123456",
            "email": user_data["email"],
            "phone": user_data["phone"],
            "fullName": [
                {
                    "language": "fra",
                    "value": f"{user_data['first_name']} {user_data['last_name']}"
                },
                {
                    "language": "ara",
                    "value": f"{user_data['first_name']} {user_data['last_name']}"
                },
                {
                    "language": "eng",
                    "value": f"{user_data['first_name']} {user_data['last_name']}"
                }
            ],
            "nickName": [
                {
                    "language": "fra",
                    "value": user_data["first_name"]
                },
                {
                    "language": "ara",
                    "value": user_data["first_name"]
                },
                {
                    "language": "eng",
                    "value": user_data["first_name"]
                }
            ],
            "preferredUsername": [
                {
                    "language": "fra",
                    "value": f"{user_data['first_name']}{user_data['last_name']}"
                },
                {
                    "language": "ara",
                    "value": f"{user_data['first_name']}{user_data['last_name']}"
                },
                {
                    "language": "eng",
                    "value": f"{user_data['first_name']}{user_data['last_name']}"
                }
            ],
            "givenName": [
                {
                    "language": "fra",
                    "value": user_data["first_name"]
                },
                {
                    "language": "ara",
                    "value": user_data["first_name"]
                },
                {
                    "language": "eng",
                    "value": user_data["first_name"]
                }
            ],
            "middleName": [
                {
                    "language": "fra",
                    "value": user_data["first_name"]
                },
                {
                    "language": "ara",
                    "value": user_data["first_name"]
                },
                {
                    "language": "eng",
                    "value": user_data["first_name"]
                }
            ],
            "familyName": [
                {
                    "language": "fra",
                    "value": user_data["last_name"]
                },
                {
                    "language": "ara",
                    "value": user_data["last_name"]
                },
                {
                    "language": "eng",
                    "value": user_data["last_name"]
                }
            ],
            "gender": [
                {
                    "language": "eng",
                    "value": user_data["gender"]
                },
                {
                    "language": "fra",
                    "value": "Femme" if user_data["gender"] == "Female" else "Homme"
                },
                {
                    "language": "ara",
                    "value": "أنثى" if user_data["gender"] == "Female" else "ذكر"
                }
            ],
            "dateOfBirth": "2002/05/15",
            "streetAddress": [
                {
                    "language": "eng",
                    "value": "Rue 10, Dakar"
                }
            ],
            "locality": [
                {
                    "language": "eng",
                    "value": "Dakar"
                }
            ],
            "password": f"{user_data['first_name']}@123",
            "preferredLang": "fra",
            "locale": "fr",
            "region": [
                {
                    "language": "eng",
                    "value": "Dakar"
                }
            ],
            "zoneInfo": "Dakar",
            "postalCode": "12500",
            "country": [
                {
                    "language": "fra",
                    "value": "Sénégal"
                },
                {
                    "language": "ara",
                    "value": "السنغال"
                },
                {
                    "language": "eng",
                    "value": "Senegal"
                }
            ],
            "encodedPhoto": "data:image/png;base64,abc123"  # Version simplifiée
        }
    }
    
    # Convertir en JSON
    json_data = json.dumps(request_body)
    
    # Définir les headers
    headers = {
        'Content-Type': 'application/json'
    }
    
    # Envoyer la requête
    try:
        print(f"Création de l'utilisateur {user_data['first_name']} {user_data['last_name']} (ID: {user_data['id']})...")
        response = requests.post(URL, headers=headers, data=json_data)
        
        # Vérifier la réponse
        if response.status_code == 200:
            response_json = response.json()
            if 'response' in response_json and response_json['response'] is not None:
                print(f"✅ Utilisateur créé avec succès: {user_data['first_name']} {user_data['last_name']} (ID: {user_data['id']})")
                print(f"Réponse: {response_json}")
                return True
            else:
                print(f"❌ Erreur lors de la création de l'utilisateur: {response_json}")
                return False
        else:
            print(f"❌ Erreur HTTP {response.status_code}: {response.text}")
            return False
    except Exception as e:
        print(f"❌ Exception lors de la création de l'utilisateur: {str(e)}")
        return False

# Créer les utilisateurs
def create_all_users():
    print("=== Début de la création des utilisateurs MOSIP ===")
    successful_users = 0
    
    for user in users:
        if create_user(user):
            successful_users += 1
        # Pause entre les requêtes pour éviter de surcharger le serveur
        time.sleep(1)
    
    print(f"\n=== Résumé ===")
    print(f"Utilisateurs créés avec succès: {successful_users}/{len(users)}")
    print("=== Fin du processus ===")

# Exécuter le script
if __name__ == "__main__":
    create_all_users()