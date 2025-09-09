import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../Environment';
import { DonationCenterDTO } from '../../models/DonationCenterDTO';



@Injectable({
  providedIn: 'root'
})
export class DonationCenterService {
  private apiUrl = `${environment.apiUrl}/donation-centers`;

  constructor(private http: HttpClient) {}

  /**
   * Récupère tous les centres de don
   */
  getAllCenters(): Observable<DonationCenterDTO[]> {
    return this.http.get<DonationCenterDTO[]>(this.apiUrl);
  }

  /**
   * Récupère un centre de don par son ID
   */
  getCenter(id: number): Observable<DonationCenterDTO> {
    return this.http.get<DonationCenterDTO>(`${this.apiUrl}/${id}`);
  }

  /**
   * Crée un nouveau centre de don
   */
  createCenter(center: DonationCenterDTO): Observable<DonationCenterDTO> {
    return this.http.post<DonationCenterDTO>(this.apiUrl, center);
  }

  /**
   * Met à jour un centre de don existant
   */
  updateCenter(center: DonationCenterDTO): Observable<DonationCenterDTO> {
    return this.http.put<DonationCenterDTO>(`${this.apiUrl}/${center.id}`, center);
  }

  /**
   * Supprime un centre de don
   */
  deleteCenter(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  /**
   * Recherche des centres par nom
   */
  searchCenters(keyword: string): Observable<DonationCenterDTO[]> {
    return this.http.get<DonationCenterDTO[]>(`${this.apiUrl}/search/${keyword}`);
  }

  /**
   * Récupère les centres par ID d'adresse
   */
  getCentersByAddress(addressId: number): Observable<DonationCenterDTO[]> {
    return this.http.get<DonationCenterDTO[]>(`${this.apiUrl}/by-address/${addressId}`);
  }

  /**
   * Récupère les centres avec pagination
   */
  getCentersPaginated(page: number, size: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<any>(`${this.apiUrl}`, { params });
  }

  /**
   * Charge les centres les plus proches d'une position géographique
   * Note: Cette fonction nécessite que votre API backend prenne en charge cette fonctionnalité
   */
  getNearestCenters(latitude: number, longitude: number, radius: number = 10): Observable<DonationCenterDTO[]> {
    let params = new HttpParams()
      .set('lat', latitude.toString())
      .set('lng', longitude.toString())
      .set('radius', radius.toString());
    
    return this.http.get<DonationCenterDTO[]>(`${this.apiUrl}/nearest`, { params });
  }

  /**
   * Obtient les statistiques des centres de don
   * Note: Cette fonction nécessite que votre API backend prenne en charge cette fonctionnalité
   */
  getCentersStats(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/stats`);
  }
}