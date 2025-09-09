# RETRO-did-hackathon

---

```markdown
# 🩺 LifeLink - Blood Donation Management Platform with Digital ID + Blood Donation Chatbot

LifeLink is a comprehensive 3-tier medical management solution combining:
- A Spring Boot backend
- An Angular frontend
- A Flutter application
- An intelligent chatbot (Amina) to inform users about blood donation

---

## 🔧 Environment Setup

### 1. Launch eSignet services

Navigate to the `esignet/docker-compose` folder:
```bash
docker compose --file docker-compose.yml up
```

### 2. Generate eSignet keys and users

First install the requirements:
```bash
pip install -r requirements.txt
```

From the main folder:

```bash
python esignet_keygen.py
# or
python -m esignet_keygen.py

python esignet_usergen.py
# or
python -m esignet_usergen.py
```

These scripts create the following users:

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

To log in via eSignet, use the respective UINs:

```
8267411576 - 8267411577 - 8267411578
OTP: 111111
```

---

## 🐘 PostgreSQL Database

Start the PostgreSQL container with persistence:

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

### 📦 Java JDK 21 Installation

#### On **Windows**
Download the installer from:  
🔗 https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html

#### On **Linux**
```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

### ▶️ Launch the backend

#### With IntelliJ
- Open the `Lifelink_Backend` project
- Maven will automatically download dependencies

#### Command line:
```bash
cd Lifelink_Backend
mvn spring-boot:run
```

---

## 🌐 Frontend - Angular

### 📦 Node.js and Angular CLI Installation

#### On **Windows**
Download Node.js from:  
🔗 https://nodejs.org/

Install tailwind:  
🔗 https://tailwindcss.com/docs/installation/using-vite

Then install Angular CLI:
```bash
npm install -g @angular/cli
```

#### On **Linux**
```bash
sudo apt update
sudo apt install nodejs npm
sudo npm install -g @angular/cli
```

### ▶️ Launch the frontend

```bash
cd Lifelink_Admin
npm i --force
ng serve
```

Access the interface via:  
🔗 [http://localhost:4200](http://localhost:4200)

Click on "Dashboard" to access eSignet login.

---

## 📱 Flutter Application

A Flutter application has been developed for mobile users.

### 📦 Version used
- Flutter **3.29.0-stable**

### 🛠️ Prerequisites
- **Android Studio**: For emulator and Android build

👉 Follow the official Flutter documentation:  
🔗 [https://docs.flutter.dev/get-started/install](https://docs.flutter.dev/get-started/install)

---

## 🤖 Amina - Blood Donation Chatbot

**Amina** is an **intelligent chatbot** to answer questions about **blood donation**.

### ⚙️ Technologies used

- 🚀 **FastAPI**: for the REST API
- 🧠 **LangChain**: for contextual response management
- 🤗 **Hugging Face**: to access the Mistral-7B model via API
- 📚 **FAISS**: to quickly retrieve information from the PDF database

> Docker was planned for deployment but not used here.

### 🧠 Why Hugging Face?

Running a model like Mistral-7B locally requires too many resources.  
We therefore use Hugging Face Hub via API, with a **token list** to manage automatic rotation in case of expiration.

---

### 🚀 Start the chatbot

#### Clone the project
```bash
git clone https://github.com/ton-repo/sangbot.git
cd sangbot
```

#### Create a virtual environment
```bash
python -m venv venv
source venv/bin/activate         # Linux/Mac
venv\Scripts\activate            # Windows
```

#### Install dependencies
```bash
pip install -r requirements.txt
```

#### Configure Hugging Face tokens

Create a `.env` file:
```env
HUGGINGFACE_TOKENS=token1,token2,token3
```

> Replace `token1`, `token2`, etc. with valid Hugging Face tokens.

---

### ▶️ Launch the API
```bash
uvicorn main:app --reload
```

Accessible at:  
📍 [http://127.0.0.1:8000](http://127.0.0.1:8000)

#### API call example:
```bash
curl -X POST "http://127.0.0.1:8000/chat" \
-H "Content-Type: application/json" \
-d '{"question": "Can I donate blood if I just got a tattoo?"}'
```

---

---

## 🌍 LLM-W - Wolof Chatbot Prototype

The `LLM-W` folder contains a first **experiment of a chatbot in Wolof**, designed to meet local linguistic needs.  
This prototype aimed to extend **SangBot** so it could also dialogue in **Wolof language**.

However, due to **time constraints and integration issues**, this component **has not yet been finalized or integrated** into the current solution.

> 🧪 The code remains available for any future evolution or contribution around multilingualism.

---

🚧 Containerization Attempt
We undertook to containerize the entire project to facilitate deployment and portability. Although the majority of components were correctly isolated in Docker containers, we encountered last-minute technical difficulties, notably:

Communication problems between different containers, particularly with the eSignet service.

Assertion errors preventing certain parts of the system from functioning as expected in the containerized environment.

Despite our efforts to resolve these blockers, we ran out of time to finalize a fully functional solution in time.




## 🙌 Credits

Project created by RETR0  
As part of the Digital ID Hackathon Western Africa

---

## 📄 License

This project is under MIT license. See the `LICENSE` file for more information.
```

---
