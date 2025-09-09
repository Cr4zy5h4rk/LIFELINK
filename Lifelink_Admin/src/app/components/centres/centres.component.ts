import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DonationCenterService } from '../../services/DonationCenter/donation-center.service';

// Définition de l'interface pour le DTO
export interface DonationCenterDTO {
  id?: number;
  hospital: string;
  contact?: string;
  centerDetails?: string;
  wolofAudioDetails?: string;
  address?: any; // Interface pour l'adresse
  organizationPartner?: any; // Interface pour le partenaire
}

interface CityStats {
  city: string;
  count: number;
}

interface DonationCenterFilters {
  city: string;
  withAudio: boolean;
  withPartner: boolean;
  search: string;
}

@Component({
  selector: 'app-donation-centers',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './centres.component.html',
  styleUrls: ['./centres.component.scss']
})
export class CentresComponent implements OnInit {
  centers: DonationCenterDTO[] = [];
  filteredCenters: DonationCenterDTO[] = [];
  cities: CityStats[] = [];
  loading = true;
  error: string | null = null;
  
  // État du composant
  showMap = false;
  activeCenter: DonationCenterDTO | null = null;
  showDetails = false; // Pour contrôler l'affichage du modal de détails
  
  // Filtres
  filters: DonationCenterFilters = {
    city: '',
    withAudio: false,
    withPartner: false,
    search: ''
  };
  
  // Pagination
  currentPage = 1;
  itemsPerPage = 4;
  
  constructor(private donationCenterService: DonationCenterService) { }

  ngOnInit(): void {
    this.loadCenters();
  }
  
  loadCenters(): void {
    this.loading = true;
    this.donationCenterService.getAllCenters().subscribe({
      next: (centers) => {
        this.centers = centers;
        this.filteredCenters = [...centers]; // Copie initiale
        this.applyFilters();
        this.loadCitiesStats();
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des centres', err);
        this.error = 'Impossible de charger les centres de don. Veuillez réessayer plus tard.';
        this.loading = false;
      }
    });
  }
  
  loadCitiesStats(): void {
    // Extraire les statistiques par ville à partir des centres chargés
    const citiesMap = new Map<string, number>();
    
    this.centers.forEach(center => {
      const city = center.address?.city || 'Non spécifié';
      const currentCount = citiesMap.get(city) || 0;
      citiesMap.set(city, currentCount + 1);
    });
    
    // Convertir en tableau de CityStats
    this.cities = Array.from(citiesMap.entries()).map(([city, count]) => ({
      city,
      count
    }));
  }
  
  applyFilters(): void {
    this.filteredCenters = this.centers.filter(center => {
      // Filtre par ville
      if (this.filters.city && center.address?.city !== this.filters.city) {
        return false;
      }
      
      // Filtre par audio
      if (this.filters.withAudio && !center.wolofAudioDetails) {
        return false;
      }
      
      // Filtre par partenaire
      if (this.filters.withPartner && !center.organizationPartner) {
        return false;
      }
      
      // Filtre par recherche textuelle
      if (this.filters.search) {
        const searchLower = this.filters.search.toLowerCase();
        const nameMatch = center.hospital?.toLowerCase().includes(searchLower);
        const detailsMatch = center.centerDetails?.toLowerCase().includes(searchLower);
        const contactMatch = center.contact?.toLowerCase().includes(searchLower);
        
        if (!nameMatch && !detailsMatch && !contactMatch) {
          return false;
        }
      }
      
      return true;
    });
    
    // Réinitialiser la pagination
    this.currentPage = 1;
  }
  
  resetFilters(): void {
    this.filters = {
      city: '',
      withAudio: false,
      withPartner: false,
      search: ''
    };
    this.applyFilters();
  }
  
  filterByCity(city: string): void {
    this.filters.city = city;
    this.applyFilters();
  }
  
  toggleMap(): void {
    this.showMap = !this.showMap;
  }
  
  selectCenter(center: DonationCenterDTO): void {
    console.log('Centre sélectionné:', center);
    // Chargement des détails complets du centre
    if (center.id) {
      this.donationCenterService.getCenter(center.id).subscribe({
        next: (fullCenter) => {
          this.activeCenter = fullCenter;
          this.showDetails = true; // Afficher la modal
          console.log('Détails du centre chargés:', fullCenter);
        },
        error: (err) => {
          console.error('Erreur lors du chargement des détails du centre', err);
          this.activeCenter = center;
          this.showDetails = true; // Afficher quand même la modal avec les données disponibles
        }
      });
    } else {
      this.activeCenter = center;
      this.showDetails = true; // Afficher la modal
    }
  }
  
  closeDetails(): void {
    this.showDetails = false;
    this.activeCenter = null;
  }
  
  // Gestion de la pagination
  get totalPages(): number {
    return Math.ceil(this.filteredCenters.length / this.itemsPerPage);
  }
  
  get paginatedCenters(): DonationCenterDTO[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredCenters.slice(startIndex, startIndex + this.itemsPerPage);
  }
  
  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }
  
  // Utilitaires pour l'affichage
  hasMapCoordinates(center: DonationCenterDTO): boolean {
    return !!(center.address && center.address.latitude && center.address.longitude);
  }
  
  hasAudio(center: DonationCenterDTO): boolean {
    return !!center.wolofAudioDetails;
  }
  
  getCenterMapUrl(center: DonationCenterDTO): string {
    if (!this.hasMapCoordinates(center)) return '';
    
    const lat = center.address.latitude;
    const lng = center.address.longitude;
    
    return `https://maps.googleapis.com/maps/api/staticmap?center=${lat},${lng}&zoom=14&size=600x300&markers=color:red%7C${lat},${lng}&key=YOUR_API_KEY`;
  }
  
  openGoogleMaps(center: DonationCenterDTO): void {
    if (!this.hasMapCoordinates(center)) return;
    
    const lat = center.address.latitude;
    const lng = center.address.longitude;
    
    window.open(`https://www.google.com/maps/search/?api=1&query=${lat},${lng}`, '_blank');
  }
  
  // Pour les statistiques
  getTotalCenters(): number {
    return this.centers.length;
  }
  
  getCitiesCount(): number {
    return this.cities.length;
  }
  
  // Méthode pour planifier un don dans ce centre
  planDonation(center: DonationCenterDTO): void {
    window.location.href = `/planifier-don/${center.id}`;
  }
}