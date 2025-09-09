// src/app/services/alerte.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Emergency } from '../../models/Emergency';




@Injectable({
  providedIn: 'root'
})
export class AlerteService {
  private apiUrl = 'http://localhost:8080/api'; // Assurez-vous que cette URL est correcte

  constructor(private http: HttpClient) { }

  /**
   * Récupère toutes les urgences depuis l'API
   */
  getAllEmergencies(): Observable<Emergency[]> {
    return this.http.get<Emergency[]>(`${this.apiUrl}/emergencies`);
  }

  /**
   * Récupère une urgence par son ID
   */
  getEmergencyById(id: number): Observable<Emergency> {
    return this.http.get<Emergency>(`${this.apiUrl}/emergencies/${id}`);
  }

  /**
   * Crée une nouvelle urgence
   */
  createEmergency(emergency: Emergency): Observable<Emergency> {
    return this.http.post<Emergency>(`${this.apiUrl}/emergencies`, emergency);
  }

  /**
   * Met à jour une urgence existante
   */
  updateEmergency(id: number, emergency: Emergency): Observable<Emergency> {
    return this.http.put<Emergency>(`${this.apiUrl}/emergencies/${id}`, emergency);
  }

  /**
   * Supprime une urgence
   */
  deleteEmergency(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/emergencies/${id}`);
  }

}