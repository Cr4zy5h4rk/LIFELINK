## SangBot - Chatbot pour le Don de Sang

SangBot est un chatbot interactif conçu pour fournir des informations fiables et rapides sur le don de sang. 
Ce chatbot utilise un modèle de langage puissant hébergé sur Hugging Face et une base de connaissances(fichier pdf) pour répondre aux questions des utilisateurs 
 concernant le processus, les critères et les informations liées au don de sang.
### Technologies utilisées
FastAPI : Framework pour créer l'API RESTful.

LangChain : Pour l'intégration du modèle de langage avec un moteur de recherche contextuelle.

Hugging Face : Hébergement du modèle de langage et gestion des tokens d'authentification.

FAISS : Utilisation d'un moteur de recherche basé sur les embeddings pour trouver les informations pertinentes.

(Docker était prévu pour le déploiement mais n’est pas utilisé ici)
### Pourquoi HuggingFace HUB ?

Faire tourner un modèle comme Mistral-7B-Instruct en local nécessite des ressources importantes (puissance GPU, mémoire, stockage).
Cela peut être coûteux.
Pour faciliter les tests nous avons utilisé Hugging Face Hub.
Hugging Face Hub permet d'accéder facilement au modèle via une API, sans avoir à gérer l'infrastructure ou 
les coûts associés à l'hébergement local.
Mais les tokens Hugging Face peuvent expirer ou atteindre des limites d’utilisation.
Pour éviter que le chatbot ne s’arrête en cas de problème avec un token, une `liste de tokens` est utilisée.
Lorsqu'un token échoue, un autre est testé automatiquement.
Cela permet de maintenir le chatbot fonctionnel pour un temps plus long.

### Démarrer l’application : 
- ##### Cloner le projet et se déplacer dans le dossier principal:

    `git clone https://github.com/ton-repo/sangbot.git `
    
     `cd sangbot`


- ##### Créer un environnement virtuel et l'activer

  `python -m venv venv`

  `source venv/bin/activate`   # sur Linux/Mac

  `venv\Scripts\activate`      # sur Windows



-  ##### Installer les dépendances 

   `pip install -r requirements.txt`

- ##### Configurer les tokens Hugging Face

  Crée ou modifier le fichier .env à la racine du projet en ajoutant :

  `HUGGINGFACE_TOKENS=token1,token2,token3`

  Remplacez token1, token2, etc. par des tokens Hugging Face valides. 
  
  Ici on utilise un exemple de token pas encore expiré. 
  
  En cas d'expiration des tokens,vous pouvez ajouter ou remplacer par des tokens huggingface valide.

- ##### Lancer l’API

  `uvicorn main:app --reload`

   L’API sera accessible à l’adresse : http://127.0.0.1:8000

- #### Exemple d’appel à l’API

  ```curl -X POST "http://127.0.0.1:8000/chat" -H "Content-Type: application/json" -d '{"question": "Puis-je donner du sang si je viens de me faire tatouer ?"}'```

