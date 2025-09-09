import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../Environment';

@Injectable({
  providedIn: 'root'
})
export class CarnetService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  // Récupérer tous les donneurs
  getAllDonors(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/donor/all`);
  }

  // Récupérer un donneur par son ID
  getDonorById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/donor/${id}`);
  }

  // Récupérer les données médicales d'un donneur
  getDonorMedicalData(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/donors/${id}/medical-data`);
  }

  // Récupérer une section spécifique des données médicales
  getMedicalSection(id: string, section: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/donors/${id}/medical-data/${section}`);
  }

}