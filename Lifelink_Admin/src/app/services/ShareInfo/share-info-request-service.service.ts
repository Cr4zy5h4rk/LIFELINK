// Mise à jour du service ShareInfoRequestService
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../Environment';

export interface ShareInfoRequestDTO {
  id?: number;
  requestDate?: string;
  acceptanceDate?: string;
  accepted?: boolean;
  bloodDonationCampaign?: number;
  donationCenter?: number;
  phoneNumber?: string;
  sharedInfoSelection?: string;
  sharedInfoUser?: string;
}

export interface ShareInfoSelectionDTO {
  // Infos personnelles
  shareDateNaissance: boolean;
  shareAddress: boolean;
  shareEmail: boolean;
  
  // Infos médicales
  shareInfosMedicales: boolean;
  shareVaccinations: boolean;
  shareConsultations: boolean;
  shareAppareilsMedicaux: boolean;
  shareDonSanguin: boolean;
  shareAlertes: boolean;
  shareContactsUrgence: boolean;
  shareAssurance: boolean;
  shareModeDeVie: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ShareInfoRequestService {
  private apiUrl = `${environment.apiUrl}/share-info-requests`;

  constructor(private http: HttpClient) {}

  // Récupérer toutes les demandes
  getAllRequests(): Observable<ShareInfoRequestDTO[]> {
    return this.http.get<ShareInfoRequestDTO[]>(this.apiUrl);
  }

  // Récupérer les demandes en attente
  getPendingRequests(): Observable<ShareInfoRequestDTO[]> {
    return this.http.get<ShareInfoRequestDTO[]>(`${this.apiUrl}/pending`);
  }

  // Récupérer les demandes par campagne
  getRequestsByCampaign(campaignId: number): Observable<ShareInfoRequestDTO[]> {
    return this.http.get<ShareInfoRequestDTO[]>(`${this.apiUrl}/campaign/${campaignId}`);
  }

  // Récupérer les demandes par centre
  getRequestsByCenter(centerId: number): Observable<ShareInfoRequestDTO[]> {
    return this.http.get<ShareInfoRequestDTO[]>(`${this.apiUrl}/center/${centerId}`);
  }

  // Méthode mise à jour pour créer une demande avec les sélections d'informations
  createRequestWithPhone(
    phoneNumber: string, 
    selectionDTO: ShareInfoSelectionDTO, 
    campaignId?: number, 
    centerId?: number
  ): Observable<ShareInfoRequestDTO> {
    let params = new HttpParams().set('phoneNumber', phoneNumber);
    
    if (campaignId) {
      params = params.set('campaignId', campaignId.toString());
    }
    
    if (centerId) {
      params = params.set('centerId', centerId.toString());
    }
    
    return this.http.post<ShareInfoRequestDTO>(`${this.apiUrl}/phone`, selectionDTO, { params });
  }

  // Rejeter une demande
  rejectRequest(id: number): Observable<ShareInfoRequestDTO> {
    return this.http.put<ShareInfoRequestDTO>(`${this.apiUrl}/${id}/reject`, {});
  }

  // Supprimer une demande
  deleteRequest(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Cette méthode n'est plus nécessaire si le nouveau flux de création inclut déjà les sélections
  // Mais elle peut être conservée pour d'autres cas d'utilisation si nécessaire
  acceptRequestWithSelection(id: number, selectionData: any): Observable<ShareInfoRequestDTO> {
    return this.http.put<ShareInfoRequestDTO>(`${this.apiUrl}/${id}/accept`, selectionData);
  }
}