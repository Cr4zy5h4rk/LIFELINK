import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { RegionStock } from '../../models/region-stock';

@Injectable({
  providedIn: 'root'
})
export class BloodStockService {
  // Données mockées pour simuler un retour d'API
  private mockData: RegionStock[] = [
    {
      id: 1,
      name: 'Dakar',
      bloodStocks: [
        { type: 'A+', level: 75 },
        { type: 'A-', level: 45 },
        { type: 'B+', level: 60 },
        { type: 'B-', level: 35 },
        { type: 'AB+', level: 25 },
        { type: 'AB-', level: 15 },
        { type: 'O+', level: 85 },
        { type: 'O-', level: 50 }
      ]
    },
    {
      id: 2,
      name: 'Thiès',
      bloodStocks: [
        { type: 'A+', level: 60 },
        { type: 'A-', level: 30 },
        { type: 'B+', level: 45 },
        { type: 'B-', level: 20 },
        { type: 'AB+', level: 15 },
        { type: 'AB-', level: 10 },
        { type: 'O+', level: 70 },
        { type: 'O-', level: 40 }
      ]
    },
    {
      id: 3,
      name: 'Saint-Louis',
      bloodStocks: [
        { type: 'A+', level: 55 },
        { type: 'A-', level: 25 },
        { type: 'B+', level: 40 },
        { type: 'B-', level: 15 },
        { type: 'AB+', level: 20 },
        { type: 'AB-', level: 10 },
        { type: 'O+', level: 65 },
        { type: 'O-', level: 35 }
      ]
    },
    {
      id: 4,
      name: 'Ziguinchor',
      bloodStocks: [
        { type: 'A+', level: 40 },
        { type: 'A-', level: 20 },
        { type: 'B+', level: 35 },
        { type: 'B-', level: 15 },
        { type: 'AB+', level: 10 },
        { type: 'AB-', level: 5 },
        { type: 'O+', level: 50 },
        { type: 'O-', level: 25 }
      ]
    },
    {
      id: 5,
      name: 'Diourbel',
      bloodStocks: [
        { type: 'A+', level: 45 },
        { type: 'A-', level: 20 },
        { type: 'B+', level: 35 },
        { type: 'B-', level: 10 },
        { type: 'AB+', level: 15 },
        { type: 'AB-', level: 5 },
        { type: 'O+', level: 60 },
        { type: 'O-', level: 30 }
      ]
    },
    {
      id: 6,
      name: 'Louga',
      bloodStocks: [
        { type: 'A+', level: 35 },
        { type: 'A-', level: 15 },
        { type: 'B+', level: 30 },
        { type: 'B-', level: 10 },
        { type: 'AB+', level: 10 },
        { type: 'AB-', level: 5 },
        { type: 'O+', level: 45 },
        { type: 'O-', level: 20 }
      ]
    },
    {
      id: 7,
      name: 'Fatick',
      bloodStocks: [
        { type: 'A+', level: 35 },
        { type: 'A-', level: 15 },
        { type: 'B+', level: 25 },
        { type: 'B-', level: 5 },
        { type: 'AB+', level: 10 },
        { type: 'AB-', level: 5 },
        { type: 'O+', level: 40 },
        { type: 'O-', level: 20 }
      ]
    },
    {
      id: 8,
      name: 'Kaolack',
      bloodStocks: [
        { type: 'A+', level: 40 },
        { type: 'A-', level: 20 },
        { type: 'B+', level: 30 },
        { type: 'B-', level: 10 },
        { type: 'AB+', level: 15 },
        { type: 'AB-', level: 5 },
        { type: 'O+', level: 50 },
        { type: 'O-', level: 25 }
      ]
    },
    {
      id: 9,
      name: 'Kolda',
      bloodStocks: [
        { type: 'A+', level: 30 },
        { type: 'A-', level: 15 },
        { type: 'B+', level: 25 },
        { type: 'B-', level: 10 },
        { type: 'AB+', level: 5 },
        { type: 'AB-', level: 5 },
        { type: 'O+', level: 40 },
        { type: 'O-', level: 20 }
      ]
    },
    {
      id: 10,
      name: 'Tambacounda',
      bloodStocks: [
        { type: 'A+', level: 25 },
        { type: 'A-', level: 10 },
        { type: 'B+', level: 20 },
        { type: 'B-', level: 5 },
        { type: 'AB+', level: 5 },
        { type: 'AB-', level: 5 },
        { type: 'O+', level: 35 },
        { type: 'O-', level: 15 }
      ]
    },
    {
      id: 11,
      name: 'Matam',
      bloodStocks: [
        { type: 'A+', level: 30 },
        { type: 'A-', level: 15 },
        { type: 'B+', level: 25 },
        { type: 'B-', level: 10 },
        { type: 'AB+', level: 10 },
        { type: 'AB-', level: 5 },
        { type: 'O+', level: 40 },
        { type: 'O-', level: 20 }
      ]
    },
    {
      id: 12,
      name: 'Kaffrine',
      bloodStocks: [
        { type: 'A+', level: 25 },
        { type: 'A-', level: 10 },
        { type: 'B+', level: 20 },
        { type: 'B-', level: 5 },
        { type: 'AB+', level: 5 },
        { type: 'AB-', level: 5 },
        { type: 'O+', level: 30 },
        { type: 'O-', level: 15 }
      ]
    },
    {
      id: 13,
      name: 'Kédougou',
      bloodStocks: [
        { type: 'A+', level: 20 },
        { type: 'A-', level: 10 },
        { type: 'B+', level: 15 },
        { type: 'B-', level: 5 },
        { type: 'AB+', level: 5 },
        { type: 'AB-', level: 5 },
        { type: 'O+', level: 25 },
        { type: 'O-', level: 10 }
      ]
    },
    {
      id: 14,
      name: 'Sédhiou',
      bloodStocks: [
        { type: 'A+', level: 25 },
        { type: 'A-', level: 10 },
        { type: 'B+', level: 20 },
        { type: 'B-', level: 5 },
        { type: 'AB+', level: 5 },
        { type: 'AB-', level: 5 },
        { type: 'O+', level: 30 },
        { type: 'O-', level: 15 }
      ]
    }
  ];

  constructor(private http: HttpClient) {}

  // Méthode simulant une requête API mais retournant les données mockées
  getRegionStocks(): Observable<RegionStock[]> {
    // Dans un environnement réel, on utiliserait :
    // return this.http.get<RegionStock[]>('api/blood-stocks');
    
    // Pour simuler un délai de réseau
    return new Observable(observer => {
      setTimeout(() => {
        observer.next(this.mockData);
        observer.complete();
      }, 800);
    });
  }
}