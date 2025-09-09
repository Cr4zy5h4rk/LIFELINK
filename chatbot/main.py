from langchain.chains.retrieval_qa.base import RetrievalQA
from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import ChatPromptTemplate, PromptTemplate
from langchain_core.runnables import RunnablePassthrough
from langchain_huggingface import HuggingFaceEndpoint
from dotenv import load_dotenv
import os
from fastapi import FastAPI
from pydantic import BaseModel
from langchain.chains import RetrievalQA
from langchain_huggingface import HuggingFaceEmbeddings
from langchain_community.vectorstores import FAISS
from itertools import cycle

load_dotenv()

#huggingfacehub_api_token = os.getenv("huggingfacehub_api_token")

hf_repo_id = "mistralai/Mistral-7B-Instruct-v0.3"
# Initialiser FastAPI
app = FastAPI()

huggingface_tokens = os.getenv("HUGGINGFACE_TOKENS").split(",")
token_cycle = cycle(huggingface_tokens)

# Définir un modèle Pydantic pour la requête
class ChatRequest(BaseModel):
    message: str

"""def load_llm(huggingface_repo_id, api_token):
    llm=HuggingFaceEndpoint(
        repo_id=huggingface_repo_id,
        temperature=0.5,
        model_kwargs={"token":api_token,
                      "max_length":"512"}
    )
    return llm"""

current_token = None

def load_llm(huggingface_repo_id):
    global current_token
    print("==========================current_token",current_token,"===============")
    current_token = next(token_cycle)  # Prend un token de la liste
    return HuggingFaceEndpoint(
        repo_id=huggingface_repo_id,
        temperature=0.5,
        model_kwargs={"token": current_token, "max_length": "512"}
    )

# Chargement du modèle
#llm = load_llm(hf_repo_id, huggingfacehub_api_token)
llm = load_llm(hf_repo_id)

# Création d'un prompt spécialisé pour le don de sang
prompt_template = """
<s>[INST]  
Tu es *Amina*, un assistant virtuel spécialisé dans le don de sang.  
Ta mission est d’aider les utilisateurs avec des réponses claires, précises et amicales sur le don de sang.  

### CONTEXTE  
Voici des informations utiles sur le don de sang : {context}  
(N’utilise ce contexte que si la question concerne le don de sang.)  

### RÈGLES DE COMPORTEMENT  
- Ne répond qu’aux questions liées au don de sang. Si la question sort du sujet, dis poliment que tu ne peux pas aider.  
- Sois précis, concis et encourageant.  
- Réponds directement sans reformuler la question.  
- Ne donne jamais de conseils médicaux personnalisés.  
- Ne mets jamais en relation des personnes.  
- Si la question est floue, demande une clarification.  
- Si tu ne sais pas, dis simplement : "Je ne sais pas."  
- Si une information est incertaine, recommande de contacter un organisme officiel.  
- Ne fais aucune référence à un pays, une entreprise ou une localisation spécifique.  
- Si on te salue, salue simplement et présente-toi.  
- Si on te remercie, réponds : "Heureux d’avoir pu aider."  

### QUESTION UTILISATEUR  
{question}  
[/INST]

"""


def set_custom_prompt(custom_prompt_template):
    prompt = PromptTemplate(template=custom_prompt_template, input_variables=["context", "question"])
    return prompt

DB_FAISS_PATH = "vectorstore/db_faiss"

def get_vectorstore():
    embedding_model = HuggingFaceEmbeddings(model_name='sentence-transformers/all-MiniLM-L6-v2')
    db = FAISS.load_local(DB_FAISS_PATH, embedding_model, allow_dangerous_deserialization=True)
    return db

vectorstore = get_vectorstore()

print(vectorstore.index.ntotal)  # Doit retourner un nombre > 0
qa_chain = RetrievalQA.from_chain_type(
                llm=llm,
                chain_type="stuff",
return_source_documents=True,
                retriever=vectorstore.as_retriever(search_kwargs={'k': 3}),
                chain_type_kwargs={'prompt': set_custom_prompt(prompt_template)}
            )
"""
@app.post("/chat/")
async def chat(request: ChatRequest):
    response = qa_chain.invoke({'query': request.message})

    return {"response": response["result"]}
"""
@app.post("/chat/")
async def chat(request: ChatRequest):
    global llm
    for _ in range(len(huggingface_tokens)):  # Essayer chaque token si nécessaire
        try:
            print(current_token)
            response = qa_chain.invoke({'query': request.message})
            return {"response": response["result"]}
        except Exception as e:
            print(f"Erreur avec le token {current_token}, tentative avec le suivant...,{e}")
            llm = load_llm(hf_repo_id)  # Charger un nouveau token et réessayer

    return {"erreur": "Aucun token ne fonctionne actuellement. Réessayez plus tard."}

# Endpoint de test
@app.get("/")
async def root():
    return {"message": "Bienvenue sur l'API SangBot! Utilisez /chat/ pour poser une question."}