import json
import os
import re
import requests
import base64
import time
import sys
from cryptography.hazmat.primitives.asymmetric import rsa
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.backends import default_backend

# Configuration
SPRING_PROPERTIES_PATH = "./LifeLinkBackend/src/main/resources/application.properties"
ANGULAR_ENV_PATH = "./Lifelink_Admin/src/app/Environment.ts"
LOGIN_BRIDGE_PATH = "./LifeLinkBackend/src/main/resources/static/login-bridge.html"
ESIGNET_API_URL = "http://localhost:8088/v1/esignet/client-mgmt/oidc-client"

def int_to_base64url(value):
    """Convertit un entier en représentation base64url sans padding"""
    value_hex = format(value, 'x')
    # Ajouter un 0 au début si la longueur est impaire
    if len(value_hex) % 2 == 1:
        value_hex = '0' + value_hex
    value_bytes = bytes.fromhex(value_hex)
    return base64.urlsafe_b64encode(value_bytes).decode('ascii').rstrip('=')

def generate_rsa_keypair():
    """Génère une paire de clés RSA et les retourne au format JWK"""
    while True:
        # Génération de la paire de clés RSA
        private_key = rsa.generate_private_key(
            public_exponent=65537,
            key_size=2048,
            backend=default_backend()
        )
        
        # Obtention de la clé publique
        public_key = private_key.public_key()
        
        # Extraction des nombres pour le JWK
        private_numbers = private_key.private_numbers()
        public_numbers = public_key.public_numbers()
        
        # Conversion de la clé publique au format PEM pour le CLIENT_ID
        public_key_pem = public_key.public_bytes(
            encoding=serialization.Encoding.PEM,
            format=serialization.PublicFormat.SubjectPublicKeyInfo
        )
        
        # Extraction du contenu Base64 de la clé publique
        public_key_base64 = re.sub(r'-----BEGIN PUBLIC KEY-----|-----END PUBLIC KEY-----|\n', '', public_key_pem.decode('utf-8'))
        
        # Pour les besoins du CLIENT_ID, on prend une partie de la clé publique
        client_id = public_key_base64.replace('/', '_')[2:50]
        
        # Vérifier que le client_id ne contient pas le caractère '+'
        if '+' not in client_id:
            # Création des JWK à partir des nombres extraits
            # Format JWK pour la clé privée avec format Base64URL (sans padding)
            private_key_jwk = {
                "kty": "RSA",
                "n": int_to_base64url(public_numbers.n),
                "e": int_to_base64url(public_numbers.e),
                "d": int_to_base64url(private_numbers.d),
                "p": int_to_base64url(private_numbers.p),
                "q": int_to_base64url(private_numbers.q),
                "dp": int_to_base64url(private_numbers.dmp1),
                "dq": int_to_base64url(private_numbers.dmq1),
                "qi": int_to_base64url(private_numbers.iqmp)
            }
            
            # Format JWK pour la clé publique
            public_key_jwk = {
                "kty": "RSA",
                "n": int_to_base64url(public_numbers.n),
                "e": int_to_base64url(public_numbers.e)
            }
            
            print("JWK privée générée:")
            print(json.dumps(private_key_jwk, indent=2))
            
            print("\nJWK publique générée:")
            print(json.dumps(public_key_jwk, indent=2))
            
            return {
                'private_key_jwk': private_key_jwk,
                'public_key_jwk': public_key_jwk,
                'client_id': client_id
            }
        else:
            print("CLIENT_ID contient un '+', régénération d'une nouvelle paire de clés...")

def register_client_with_esignet(client_id, public_key_jwk):
    """Enregistre le client avec le système eSigNet"""
    
    # Préparation de la requête
    current_time = time.strftime("%Y-%m-%dT%H:%M:%S.000Z", time.gmtime())
    payload = {
        "requestTime": current_time,
        "request": {
            "clientId": client_id,
            "clientName": "LIFELINK",
            "publicKey": public_key_jwk,
            "relyingPartyId": "mock-relying-party-id",
            "userClaims": [
                "name", "email", "gender", "phone_number", "picture", "birthdate"
            ],
            "authContextRefs": [
                "mosip:idp:acr:generated-code",
                "mosip:idp:acr:password",
                "mosip:idp:acr:linked-wallet"
            ],
            "logoUri": "https://i.imgur.com/q7oel6B.png",
            "redirectUris": [
                "http://localhost:4200/callback"
            ],
            "grantTypes": [
                "authorization_code"
            ],
            "clientAuthMethods": [
                "private_key_jwt"
            ]
        }
    }
    
    # Afficher la requête pour le débogage
    print("Payload de la requête:")
    print(json.dumps(payload, indent=2))
    
    # Envoi de la requête
    try:
        response = requests.post(
            ESIGNET_API_URL,
            json=payload,
            headers={'Content-Type': 'application/json'}
        )
        
        print(f"Code de statut de la réponse: {response.status_code}")
        print(f"Contenu de la réponse: {response.text}")
        
        if response.status_code == 200:
            data = response.json()
            print(f"Client enregistré avec succès. Réponse: {data}")
            # Vérifier la structure de la réponse
            if 'response' in data and data['response'] is not None and 'clientId' in data['response']:
                return data['response']['clientId']
            else:
                print("Structure de réponse inattendue ou erreur retournée")
                if 'errors' in data and data['errors']:
                    print(f"Erreurs: {data['errors']}")
                return client_id
        else:
            print(f"Erreur lors de l'enregistrement du client. Code: {response.status_code}")
            print(f"Réponse: {response.text}")
            return client_id
    except Exception as e:
        print(f"Exception lors de l'enregistrement du client: {str(e)}")
        return client_id

def update_spring_properties(client_id, private_key_jwk):
    """Met à jour le fichier application.properties de Spring Boot"""
    
    print(f"Mise à jour du fichier Spring properties: {SPRING_PROPERTIES_PATH}")
    
    # Conversion du JWK en chaîne JSON
    private_key_jwk_str = json.dumps(private_key_jwk, separators=(',', ':'))
    
    # Vérifier que le fichier existe
    if not os.path.exists(SPRING_PROPERTIES_PATH):
        print(f"Erreur: Le fichier {SPRING_PROPERTIES_PATH} n'existe pas.")
        return False
    
    # Lecture du fichier de propriétés
    try:
        with open(SPRING_PROPERTIES_PATH, 'r', encoding='utf-8') as file:
            content = file.read()
        
        # Mise à jour des propriétés
        # Pour CLIENT_ID
        if 'CLIENT_ID=' in content:
            content = re.sub(
                r'CLIENT_ID=.*',
                f'CLIENT_ID={client_id}',
                content
            )
        else:
            # Si la propriété n'existe pas, on l'ajoute à la fin du fichier
            content += f"\nCLIENT_ID={client_id}\n"
        
        # Pour PRIVATE_KEY_JWK
        if 'PRIVATE_KEY_JWK=' in content:
            content = re.sub(
                r'PRIVATE_KEY_JWK=.*',
                f'PRIVATE_KEY_JWK={private_key_jwk_str}',
                content
            )
        else:
            # Si la propriété n'existe pas, on l'ajoute à la fin du fichier
            content += f"PRIVATE_KEY_JWK={private_key_jwk_str}\n"
        
        # Écriture du contenu mis à jour
        with open(SPRING_PROPERTIES_PATH, 'w', encoding='utf-8') as file:
            file.write(content)
            
        print("Fichier application.properties mis à jour avec succès.")
        return True
    except Exception as e:
        print(f"Erreur lors de la mise à jour du fichier application.properties: {str(e)}")
        return False

def update_angular_environment(client_id):
    """Met à jour le fichier environment.ts d'Angular avec débogage amélioré"""
    
    print(f"Mise à jour du fichier Angular environment: {ANGULAR_ENV_PATH}")
    
    # Vérifier que le fichier existe
    if not os.path.exists(ANGULAR_ENV_PATH):
        print(f"Erreur: Le fichier {ANGULAR_ENV_PATH} n'existe pas.")
        # Lister les fichiers dans le répertoire parent pour vérifier
        try:
            parent_dir = os.path.dirname(ANGULAR_ENV_PATH)
            if os.path.exists(parent_dir):
                print(f"Fichiers trouvés dans {parent_dir}:")
                for file in os.listdir(parent_dir):
                    print(f"  - {file}")
            else:
                print(f"Le répertoire {parent_dir} n'existe pas.")
        except Exception as e:
            print(f"Erreur lors de la vérification du répertoire: {str(e)}")
        return False
    
    try:
        # Lire le contenu du fichier
        with open(ANGULAR_ENV_PATH, 'r', encoding='utf-8') as file:
            content = file.read()
        
        # Afficher le contenu pour debug
        print("Contenu original du fichier environment.ts:")
        print("----------------------------------------")
        print(content)
        print("----------------------------------------")
        
        print(f"Recherche du pattern 'clientId' dans le fichier...")
        clientId_found = 'clientId' in content
        print(f"'clientId' trouvé: {clientId_found}")
        
        # Essayer différentes approches de mise à jour
        updated = False
        updated_content = content
        
        # Approche 1: Expression régulière pour la propriété clientId standard
        if not updated:
            try:
                clientId_pattern = r'clientId\s*:\s*"[^"]*"'
                if re.search(clientId_pattern, content):
                    print("Pattern 'clientId: \"...\"' trouvé, mise à jour...")
                    updated_content = re.sub(clientId_pattern, f'clientId:"{client_id}"', content)
                    updated = True
                else:
                    print("Pattern 'clientId: \"...\"' non trouvé.")
            except Exception as e:
                print(f"Erreur dans l'approche 1: {str(e)}")
        
        # Approche 2: Recherche de l'objet environment et ajout de clientId
        if not updated:
            try:
                env_pattern = r'export\s+const\s+environment\s*=\s*\{'
                if re.search(env_pattern, content):
                    print("Pattern 'export const environment = {' trouvé, ajout de clientId...")
                    # Vérifier s'il y a déjà du contenu après l'accolade
                    pos = content.find('{', content.find('export const environment')) + 1
                    after_brace = content[pos:].strip()
                    if after_brace and not after_brace.startswith('\n'):
                        # Il y a du contenu sur la même ligne que l'accolade
                        updated_content = re.sub(
                            env_pattern,
                            f'export const environment = {{\n  clientId:"{client_id}",',
                            content
                        )
                    else:
                        # L'accolade est seule sur sa ligne
                        updated_content = content[:pos] + f'\n  clientId:"{client_id}",' + content[pos:]
                    updated = True
                else:
                    print("Pattern 'export const environment = {' non trouvé.")
            except Exception as e:
                print(f"Erreur dans l'approche 2: {str(e)}")
        
        # Approche 3: Création complète du fichier
        if not updated:
            print("Aucun pattern reconnu, création d'un nouveau contenu...")
            updated_content = f"""export const environment = {{
  clientId:"{client_id}",
  apiUrl: 'http://localhost:8080/api'
}};
"""
            updated = True
        
        # Vérification des changements
        if content == updated_content:
            print("Attention: Le contenu n'a pas été modifié.")
        else:
            print("Contenu mis à jour:")
            print("----------------------------------------")
            print(updated_content)
            print("----------------------------------------")
        
        # Écrire le contenu mis à jour ou le nouveau contenu
        with open(ANGULAR_ENV_PATH, 'w', encoding='utf-8') as file:
            file.write(updated_content)
        
        # Vérifier que le fichier a bien été mis à jour
        with open(ANGULAR_ENV_PATH, 'r', encoding='utf-8') as file:
            verify_content = file.read()
        
        if verify_content == updated_content:
            print("Fichier environment.ts mis à jour avec succès.")
            return True
        else:
            print("Erreur: Le contenu du fichier après écriture ne correspond pas au contenu attendu.")
            return False
            
    except Exception as e:
        print(f"Erreur lors de la mise à jour du fichier environment.ts: {str(e)}")
        import traceback
        traceback.print_exc()
        return False

def update_login_bridge_html(client_id):
    """Met à jour le client ID dans le fichier login-bridge.html"""
    
    print(f"Mise à jour du fichier login-bridge.html: {LOGIN_BRIDGE_PATH}")
    
    # Vérifier que le fichier existe
    if not os.path.exists(LOGIN_BRIDGE_PATH):
        print(f"Erreur: Le fichier {LOGIN_BRIDGE_PATH} n'existe pas.")
        return False
    
    try:
        # Lire le contenu du fichier
        with open(LOGIN_BRIDGE_PATH, 'r', encoding='utf-8') as file:
            content = file.read()
        
        print("Recherche du client_id dans login-bridge.html...")
        
        # Rechercher le pattern dans le code JavaScript
        # Pattern typique pour un client_id dans une URL d'autorisation OAuth
        client_id_pattern = r'client_id=([^&"\s]+)'
        
        if re.search(client_id_pattern, content):
            print("Pattern client_id trouvé dans le fichier login-bridge.html")
            # Mettre à jour toutes les occurrences du client_id
            updated_content = re.sub(client_id_pattern, f'client_id={client_id}', content)
            
            # Vérifier si le client_id est aussi dans une variable JavaScript
            js_client_id_pattern = r'(var|let|const)\s+clientId\s*=\s*[\'"]([^\'"]+)[\'"]'
            if re.search(js_client_id_pattern, updated_content):
                print("Variable clientId trouvée dans le code JavaScript")
                updated_content = re.sub(js_client_id_pattern, f'\\1 clientId = "{client_id}"', updated_content)
            
            # Cas spécifique du HTML affiché dans le code source
            # Exemple: var authUrl = "http://10.0.2.2:3000/authorize" + "?client_id=IIBIjA..."
            specific_pattern = r'(\?client_id=)[^"&\s]+'
            if re.search(specific_pattern, updated_content):
                print("Pattern spécifique trouvé dans le code JavaScript")
                updated_content = re.sub(specific_pattern, f'\\1{client_id}', updated_content)
            
            # Écrire le contenu mis à jour
            with open(LOGIN_BRIDGE_PATH, 'w', encoding='utf-8') as file:
                file.write(updated_content)
            
            print("Fichier login-bridge.html mis à jour avec succès.")
            return True
        else:
            print("Attention: Aucun pattern client_id trouvé dans login-bridge.html")
            
            # Essayer une recherche plus générique pour trouver où pourrait être le client_id
            potential_matches = re.findall(r'client[-_]?id', content, re.IGNORECASE)
            if potential_matches:
                print(f"Potentielles références à client_id trouvées: {len(potential_matches)}")
                # Afficher le contexte autour de chaque match potentiel
                for i, match in enumerate(re.finditer(r'client[-_]?id', content, re.IGNORECASE)):
                    start = max(0, match.start() - 40)
                    end = min(len(content), match.end() + 60)
                    context = content[start:end].replace('\n', ' ')
                    print(f"Match {i+1}: ...{context}...")
            return False
    
    except Exception as e:
        print(f"Erreur lors de la mise à jour du fichier login-bridge.html: {str(e)}")
        import traceback
        traceback.print_exc()
        return False

def main():
    print("=== Début du processus de génération et mise à jour des clés eSigNet ===")
    
    # Génération des clés
    print("Génération de la paire de clés RSA...")
    keys = generate_rsa_keypair()
    
    # Affichage des informations générées
    print(f"CLIENT_ID généré: {keys['client_id']}")
    

    print("Enregistrement du client avec eSigNet...")
    client_id = register_client_with_esignet(keys['client_id'], keys['public_key_jwk'])
    
    # Mise à jour des fichiers
    spring_updated = update_spring_properties(client_id, keys['private_key_jwk'])
    angular_updated = update_angular_environment(client_id)
    login_bridge_updated = update_login_bridge_html(client_id)
    
    # Résumé final
    print("\n=== Résumé du processus ===")
    print(f"CLIENT_ID: {client_id}")
    print(f"Fichier Spring Boot mis à jour: {'Oui' if spring_updated else 'Non'}")
    print(f"Fichier Angular mis à jour: {'Oui' if angular_updated else 'Non'}")
    print(f"Fichier login-bridge.html mis à jour: {'Oui' if login_bridge_updated else 'Non'}")
    
    print("\n=== Processus terminé ===")
    
    # Exporter les clés dans un fichier JSON pour référence
    try:
        export_path = "esignet_keys.json"
        with open(export_path, 'w', encoding='utf-8') as file:
            export_data = {
                'client_id': client_id,
                'private_key_jwk': keys['private_key_jwk'],
                'public_key_jwk': keys['public_key_jwk']
            }
            json.dump(export_data, file, indent=2)
        print(f"Les clés ont été exportées dans {export_path} pour référence")
    except Exception as e:
        print(f"Impossible d'exporter les clés: {str(e)}")

if __name__ == "__main__":
    main()