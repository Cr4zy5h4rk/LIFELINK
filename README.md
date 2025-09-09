# RETRO-did-hackathon

---

```markdown
# 🩺 LifeLink - Plateforme de gestion des dons de sang avec l'id numerique + Chatbot pour le Don de Sang

LifeLink est une solution complète de gestion médicale 3-tiers combinant :
- Un backend Spring Boot
- Un frontend Angular
- Une application Flutter
- Un chatbot intelligent (Amina) pour informer les utilisateurs sur le don de sang

---

## 🔧 Préparation de l’environnement

### 1. Lancer les services eSignet

Se rendre dans le dossier `esignet/docker-compose` :
```bash
docker compose --file docker-compose.yml up
```

### 2. Génération des clés et utilisateurs eSignet

Dabord installer les requirements: 
```bash
pip install -r requirements.txt
```

Depuis le dossier principal :

```bash
python esignet_keygen.py
# ou
python -m esignet_keygen.py

python esignet_usergen.py
# ou
python -m esignet_usergen.py
```

Ces scripts créent les utilisateurs suivants :

```json
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
```

Pour vous connecter via eSignet, utilisez les UIN respectifs :

```
8267411576 - 8267411577 - 8267411578
OTP : 111111
```

---

## 🐘 Base de données PostgreSQL

Démarrer le conteneur PostgreSQL avec persistance :

```bash
docker run --name some-postgres \
-e POSTGRES_DB=lifelink \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=root \
-v pgdata:/var/lib/postgresql/data \
-d postgres
```

---

## ☕ Backend - Spring Boot

### 📦 Installation de Java JDK 21

#### Sous **Windows**
Télécharger l'installateur depuis :  
🔗 https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html

#### Sous **Linux**
```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

### ▶️ Lancer le backend

#### Avec IntelliJ
- Ouvrir le projet `Lifelink_Backend`
- Maven téléchargera automatiquement les dépendances

#### En ligne de commande :
```bash
cd Lifelink_Backend
mvn spring-boot:run
```

---

## 🌐 Frontend - Angular

### 📦 Installation de Node.js et Angular CLI

#### Sous **Windows**
Télécharger Node.js depuis :  
🔗 https://nodejs.org/

Installez tailwind :  
🔗 https://tailwindcss.com/docs/installation/using-vite

Ensuite, installer Angular CLI :
```bash
npm install -g @angular/cli
```

#### Sous **Linux**
```bash
sudo apt update
sudo apt install nodejs npm
sudo npm install -g @angular/cli
```

### ▶️ Lancer le frontend

```bash
cd Lifelink_Admin
npm i --force
ng serve
```

Accédez à l’interface via :  
🔗 [http://localhost:4200](http://localhost:4200)

Cliquez sur "Tableau de bord" pour accéder au login eSignet.

---

## 📱 Application Flutter

Une application Flutter a été développée pour les utilisateurs mobiles.

### 📦 Version utilisée
- Flutter **3.29.0-stable**

### 🛠️ Prérequis
- **Android Studio** : Pour l’émulateur et le build Android

👉 Suivre la documentation officielle Flutter :  
🔗 [https://docs.flutter.dev/get-started/install](https://docs.flutter.dev/get-started/install)

---

## 🤖 Amina - Chatbot pour le Don de Sang

**Amina** est un **chatbot intelligent** pour répondre aux questions sur le **don de sang**.

### ⚙️ Technologies utilisées

- 🚀 **FastAPI** : pour l’API REST
- 🧠 **LangChain** : pour la gestion contextuelle des réponses
- 🤗 **Hugging Face** : pour accéder au modèle Mistral-7B via API
- 📚 **FAISS** : pour retrouver rapidement les infos dans la base PDF

> Docker était prévu pour le déploiement mais non utilisé ici.

### 🧠 Pourquoi Hugging Face ?

Exécuter un modèle comme Mistral-7B en local demande trop de ressources.  
Nous utilisons donc Hugging Face Hub via API, avec une **liste de tokens** pour gérer la rotation automatique en cas d’expiration.

---

### 🚀 Démarrer le chatbot

#### Cloner le projet
```bash
git clone https://github.com/ton-repo/sangbot.git
cd sangbot
```

#### Créer un environnement virtuel
```bash
python -m venv venv
source venv/bin/activate         # Linux/Mac
venv\Scripts\activate            # Windows
```

#### Installer les dépendances
```bash
pip install -r requirements.txt
```

#### Configurer les tokens Hugging Face

Créer un fichier `.env` :
```env
HUGGINGFACE_TOKENS=token1,token2,token3
```

> Remplacer `token1`, `token2`, etc. par des tokens Hugging Face valides.

---

### ▶️ Lancer l’API
```bash
uvicorn main:app --reload
```

Accessible sur :  
📍 [http://127.0.0.1:8000](http://127.0.0.1:8000)

#### Exemple d’appel API :
```bash
curl -X POST "http://127.0.0.1:8000/chat" \
-H "Content-Type: application/json" \
-d '{"question": "Puis-je donner du sang si je viens de me faire tatouer ?"}'
```

---

---

## 🌍 LLM-W - Prototype de Chatbot Wolof

Le dossier `LLM-W` contient une première **expérimentation d’un chatbot en Wolof**, destiné à répondre aux besoins linguistiques locaux.  
Ce prototype visait à étendre **SangBot** pour qu’il puisse aussi dialoguer en **langue wolof**.

Cependant, pour des raisons de **contraintes de temps et d’intégration**, ce composant **n’a pas encore été finalisé ni intégré** dans la solution actuelle.

> 🧪 Le code reste disponible pour toute évolution ou contribution future autour du multilinguisme.

---

🚧 Tentative de conteneurisation
Nous avons entrepris de conteneuriser l'ensemble du projet afin de faciliter le déploiement et la portabilité. Bien que la majorité des composants aient été correctement isolés dans des conteneurs Docker, nous avons rencontré des difficultés techniques de dernière minute, notamment :

Des problèmes de communication entre les différents conteneurs, en particulier avec le service eSignet.

Des erreurs d'assertion empêchant certaines parties du système de fonctionner comme prévu dans l’environnement conteneurisé.

Malgré nos efforts pour résoudre ces blocages, le temps nous a manqué pour finaliser une solution pleinement fonctionnelle à temps.




## 🙌 Crédits

Projet réalisé par RETR0 
Dans le cadre du Digital Id Hackathon Western Africa

---

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus d’informations.
```

---

