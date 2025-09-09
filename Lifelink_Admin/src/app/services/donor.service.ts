import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DonorDTO } from '../models/donor';



export interface EsignetDTO {
  phone_number: string;
  // autres propriétés d'Esignet
}

@Injectable({
  providedIn: 'root'
})
export class DonorService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  // Récupérer tous les donneurs
  getAllDonors(): Observable<DonorDTO[]> {
    return this.http.get<DonorDTO[]>(`${this.apiUrl}/donor/all`);
  }

  // Récupérer un donneur par son numéro de téléphone
  getDonorByPhoneNumber(phoneNumber: string): Observable<DonorDTO> {
    return this.http.get<DonorDTO>(`${this.apiUrl}/donor/${phoneNumber}`);
  }


  // Enregistrer un nouveau donneur
  saveDonor(donor: DonorDTO): Observable<DonorDTO> {
    return this.http.post<DonorDTO>(`${this.apiUrl}/donor/saveDonor`, donor);
  }

  // Mettre à jour un donneur existant
  updateDonor(donor: DonorDTO): Observable<DonorDTO> {
    return this.http.put<DonorDTO>(`${this.apiUrl}/donor/updateDonor`, donor);
  }

  // Enregistrer un donneur via Esignet
  saveEsignetDonor(esignetDonor: EsignetDTO): Observable<DonorDTO> {
    return this.http.post<DonorDTO>(`${this.apiUrl}/donor/saveEsignetDonor`, esignetDonor);
  }

  // Supprimer un donneur
  deleteDonor(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/donor/${id}`);
  }

  // Conversion des dates pour l'API
  formatDateForApi(date: Date): string {
    return date.toISOString();
  }

  // Conversion des dates pour l'affichage
  formatDateForDisplay(isoDate: string): Date | null {
    return isoDate ? new Date(isoDate) : null;
  }
}