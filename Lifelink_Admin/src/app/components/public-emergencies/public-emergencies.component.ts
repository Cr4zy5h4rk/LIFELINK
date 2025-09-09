import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Emergency, BloodRequestStatus } from '../../models/Emergency';
import { BloodType } from '../../models/Blood';
import { AlerteService } from '../../services/alerte/alerte.service';

@Component({
  selector: 'app-public-emergencies',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './public-emergencies.component.html',
  styleUrls: ['./public-emergencies.component.scss']
})
export class PublicEmergenciesComponent implements OnInit {
  // Données
  emergencies: Emergency[] = [];
  filteredEmergencies: Emergency[] = [];
  selectedEmergency: Emergency | null = null;
  showDetails = false;
  
  // États
  loading = true;
  error: string | null = null;
  
  // Filtres
  searchTerm = '';
  selectedBloodType = '';
  bloodTypes = Object.values(BloodType);

  // Pagination
  itemsPerPage = 6;
  currentPage = 1;

  constructor(private alerteService: AlerteService) {}

  ngOnInit(): void {
    this.loadEmergencies();
  }

  loadEmergencies(): void {
    this.loading = true;
    this.alerteService.getAllEmergencies().subscribe({
      next: (data) => {
        // Ne garder que les urgences actives (non annulées)
        this.emergencies = data.filter(e => e.status !== BloodRequestStatus.CANCELLED);
        this.applyFilters();
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des urgences', err);
        this.error = 'Impossible de charger les urgences. Veuillez réessayer plus tard.';
        this.loading = false;
      }
    });
  }

  applyFilters(): void {
    this.filteredEmergencies = this.emergencies.filter(emergency => {
      // Filtre par type sanguin (vérification simplifiée car bloodType contient déjà le groupe complet)
      if (this.selectedBloodType && !emergency.bloodType?.includes(this.selectedBloodType)) {
        return false;
      }
      
      // Filtre par recherche
      if (this.searchTerm) {
        const searchTermLower = this.searchTerm.toLowerCase();
        const titleMatch = emergency.title?.toLowerCase().includes(searchTermLower);
        const hospitalMatch = emergency.hospital?.toLowerCase().includes(searchTermLower);
        
        if (!titleMatch && !hospitalMatch) {
          return false;
        }
      }
      
      return true;
    });
    
    // Réinitialiser à la première page après filtrage
    this.currentPage = 1;
  }

  resetFilters(): void {
    this.searchTerm = '';
    this.selectedBloodType = '';
    this.applyFilters();
  }

  showEmergencyDetails(emergency: Emergency): void {
    this.selectedEmergency = emergency;
    this.showDetails = true;
  }

  closeDetails(): void {
    this.showDetails = false;
    setTimeout(() => {
      this.selectedEmergency = null;
    }, 300);
  }

  formatDate(date: string | Date | undefined): string {
    if (!date) return 'Non spécifiée';
    const d = new Date(date);
    return d.toLocaleDateString('fr-FR', { 
      day: '2-digit', 
      month: '2-digit', 
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getTimeAgo(date: string | Date | undefined): string {
    if (!date) return '';
    
    const now = new Date();
    const pastDate = new Date(date);
    const diffInMs = now.getTime() - pastDate.getTime();
    const diffInSecs = Math.floor(diffInMs / 1000);
    const diffInMins = Math.floor(diffInSecs / 60);
    const diffInHours = Math.floor(diffInMins / 60);
    const diffInDays = Math.floor(diffInHours / 24);
    
    if (diffInSecs < 60) {
      return 'il y a quelques secondes';
    } else if (diffInMins < 60) {
      return `il y a ${diffInMins} minute${diffInMins > 1 ? 's' : ''}`;
    } else if (diffInHours < 24) {
      return `il y a ${diffInHours} heure${diffInHours > 1 ? 's' : ''}`;
    } else if (diffInDays < 30) {
      return `il y a ${diffInDays} jour${diffInDays > 1 ? 's' : ''}`;
    } else {
      return this.formatDate(date);
    }
  }

  getBloodTypeClass(bloodType: String | undefined, isCard: boolean = false): string {
    if (!bloodType) return '';
    
    const baseClass = isCard ? 'blood-type-card-' : 'blood-type-';
    
    // Extraction du groupe sanguin de base (A, B, AB, O) sans le rhésus
    const baseBloodType = bloodType.replace('+', '').replace('-', '').toUpperCase();
    
    switch (baseBloodType) {
      case 'O':
        return `${baseClass}o`;
      case 'A':
        return `${baseClass}a`;
      case 'B':
        return `${baseClass}b`;
      case 'AB':
        return `${baseClass}ab`;
      default:
        return '';
    }
  }

  isCompatible(donorBloodType: String, patientBloodType: String | undefined): boolean {
    if (!patientBloodType) return false;
    
    // Extraction des groupes de base et des rhésus pour le donneur et le patient
    const donorBase = donorBloodType.replace('+', '').replace('-', '').toUpperCase();
    const patientBase = patientBloodType.replace('+', '').replace('-', '').toUpperCase();
    
    const donorIsPositive = donorBloodType.includes('+');
    const patientIsPositive = patientBloodType.includes('+');
    
    // Si le donneur est Rh+ et le patient est Rh-, incompatible
    if (donorIsPositive && !patientIsPositive) {
      return false;
    }
    
    // Vérification des compatibilités de groupe
    switch (donorBase) {
      case 'O':
        return true; // O peut donner à tous les groupes
      case 'A':
        return patientBase === 'A' || patientBase === 'AB';
      case 'B':
        return patientBase === 'B' || patientBase === 'AB';
      case 'AB':
        return patientBase === 'AB';
      default:
        return false;
    }
  }

  get paginatedEmergencies(): Emergency[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredEmergencies.slice(startIndex, startIndex + this.itemsPerPage);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredEmergencies.length / this.itemsPerPage);
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }
}