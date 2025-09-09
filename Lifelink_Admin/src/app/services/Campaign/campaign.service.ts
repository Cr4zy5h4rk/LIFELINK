import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../Environment';
import { CampaignDTO, CampaignStatus } from '../../models/Campaigns';


@Injectable({
  providedIn: 'root'
})
export class CampaignService {
  private apiUrl = `${environment.apiUrl}/campaigns`;

  constructor(private http: HttpClient) {}

  // Récupérer toutes les campagnes
  getAllCampaigns(): Observable<CampaignDTO[]> {
    return this.http.get<CampaignDTO[]>(this.apiUrl);
  }

  // Récupérer une campagne par son ID
  getCampaign(id: number): Observable<CampaignDTO> {
    return this.http.get<CampaignDTO>(`${this.apiUrl}/${id}`);
  }

  // Créer une nouvelle campagne
  createCampaign(campaign: CampaignDTO): Observable<CampaignDTO> {
    return this.http.post<CampaignDTO>(this.apiUrl, campaign);
  }

  // Mettre à jour une campagne existante
  updateCampaign(campaign: CampaignDTO): Observable<CampaignDTO> {
    return this.http.put<CampaignDTO>(`${this.apiUrl}/${campaign.id}`, campaign);
  }

  // Supprimer une campagne
  deleteCampaign(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Récupérer les campagnes par statut
  getCampaignsByStatus(status: CampaignStatus): Observable<CampaignDTO[]> {
    return this.http.get<CampaignDTO[]>(`${this.apiUrl}/status/${status}`);
  }

  // Récupérer les campagnes par région
  getCampaignsByRegion(region: string): Observable<CampaignDTO[]> {
    return this.http.get<CampaignDTO[]>(`${this.apiUrl}/region/${region}`);
  }

  // Récupérer les campagnes actives
  getActiveCampaigns(): Observable<CampaignDTO[]> {
    return this.http.get<CampaignDTO[]>(`${this.apiUrl}/active`);
  }
}