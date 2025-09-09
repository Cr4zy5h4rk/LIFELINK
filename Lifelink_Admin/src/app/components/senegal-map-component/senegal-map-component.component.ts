import { Component, OnInit, OnDestroy, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import * as L from 'leaflet';
import { BloodStatsComponent } from '../blood-stats/blood-stats.component';
import { MapStatsComponent } from '../map-stats/map-stats.component';
import { RegionsDataService } from './regionData';



/**
 * Énumération des niveaux de stock de sang
 */
export enum StockLevel {
  CRITIQUE = 'Critique',
  FAIBLE = 'Faible', 
  NORMAL = 'Normal',
  BON = 'Bon',
  EXCELLENT = 'Excellent'
}

/**
 * Interface représentant le niveau de stock pour un groupe sanguin
 */
export interface BloodGroupStockLevel {
  type: string;
  stockLevel: StockLevel;
}

/**
 * Interface représentant les statistiques de don de sang pour une région
 */
export interface BloodStats {
  region: string;
  population: number;
  totalDonors: number;
  bloodGroups: {
    [key: string]: BloodGroupStockLevel;
  };
}

@Component({
  selector: 'app-senegal-map',
  standalone: true,
  imports: [
    CommonModule, 
    HttpClientModule,
    BloodStatsComponent, 
    MapStatsComponent
  ],
  template: `
    <div class="dashboard-container">
      <div class="map-section">
        <h2 class="section-title">Carte du Don de Sang au Sénégal</h2>
        <div id="map" class="map-container"></div>
      </div>
      
      @if (selectedRegion()) {
        <app-blood-stats 
          [region]="selectedRegion()" 
          (closeStats)="clearSelectedRegion()"
        ></app-blood-stats>
      } @else {
        <app-map-stats></app-map-stats>
      }
    </div>
  `,
  styles: [`
    .dashboard-container {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 1.5rem;
      padding: 1.5rem;
      max-width: 1600px;
      margin: 0 auto;
      min-height: 80vh;
    }

    .section-title {
      color: #2C3E50;
      text-align: center;
      margin-bottom: 1rem;
      font-size: 1.8rem;
      border-bottom: 3px solid #E74C3C;
      padding-bottom: 0.5rem;
    }

    .map-container {
      height: 70vh;
      border-radius: 1rem;
      box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
      border: 2px solid #ECF0F1;
    }

    @media (max-width: 1200px) {
      .dashboard-container {
        grid-template-columns: 1fr;
      }
    }
  `]
})
export class SenegalMapComponent implements OnInit, OnDestroy {
  // Propriétés privées pour la carte et la couche GeoJSON
  private map!: L.Map;
  private senegalLayer!: L.GeoJSON;
  
  // Signal pour la région sélectionnée
  selectedRegion = signal<BloodStats | null>(null);

  /**
   * Constructeur avec injection des dépendances
   * @param http Client HTTP pour les requêtes
   * @param regionsService Service de gestion des données de régions
   */
  constructor(
    private http: HttpClient,
    private regionsService: RegionsDataService
  ) {}

  /**
   * Initialisation du composant
   */
  ngOnInit(): void {
    // Utilisation de setTimeout pour s'assurer que le DOM est prêt
    setTimeout(() => {
      this.initMap();
      this.loadSenegalGeoJSON();
    }, 100);
  }

  /**
   * Nettoyage à la destruction du composant
   */
  ngOnDestroy(): void {
    // Supprimer la carte pour libérer les ressources
    if (this.map) {
      this.map.remove();
    }
  }

  /**
   * Initialisation de la carte Leaflet
   */
  private initMap(): void {
    try {
      // Création de la carte centrée sur le Sénégal
      this.map = L.map('map', {
        center: [14.4974, -14.4524], // Coordonnées centrales du Sénégal
        zoom: 7,
        minZoom: 6,
        maxZoom: 10
      });

      // Ajout de la couche de tuiles OpenStreetMap
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      }).addTo(this.map);
    } catch (error) {
      console.error('Erreur lors de l\'initialisation de la carte :', error);
    }
  }

  /**
   * Chargement du fichier GeoJSON des régions du Sénégal
   */
  private loadSenegalGeoJSON(): void {
    this.http.get('assets/data/senegalMap.geojson').subscribe({
      next: (data: any) => {
        // Création de la couche GeoJSON avec styles personnalisés
        this.senegalLayer = L.geoJSON(data, {
          // Style des régions basé sur les données de don
          style: (feature) => ({
            fillColor: this.regionsService.getRegionColor(feature?.properties?.name),
            weight: 1.5,
            opacity: 1,
            color: '#666',
            fillOpacity: 0.7
          }),
          // Configuration des interactions avec chaque région
          onEachFeature: (feature, layer) => {
            if (feature.properties?.name) {
              const regionName = feature.properties.name;
              
              // Popup avec le nom de la région
              layer.bindPopup(`<strong>${regionName}</strong>`);
              
              // Gestion du clic sur une région
              layer.on('click', () => {
                this.onRegionClick(regionName);
              });
            }
          }
        }).addTo(this.map);

        // Ajuster la vue pour afficher toutes les régions
        this.map.fitBounds(this.senegalLayer.getBounds());
      },
      error: (error) => {
        console.error('Erreur lors du chargement du GeoJSON :', error);
      }
    });
  }

  /**
   * Gestion du clic sur une région
   * @param regionName Nom de la région cliquée
   */
  private onRegionClick(regionName: string): void {
    // Récupérer les données de la région depuis le service
    const regionData = this.regionsService.getRegionData(regionName);
    if (regionData) {
      // Mettre à jour la région sélectionnée
      this.selectedRegion.set(regionData);
    } else {
      console.warn(`Aucune donnée disponible pour la région : ${regionName}`);
    }
  }

  /**
   * Effacer la région sélectionnée
   */
  clearSelectedRegion(): void {
    this.selectedRegion.set(null);
  }
}