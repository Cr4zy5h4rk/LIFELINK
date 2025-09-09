import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DonorService } from '../../services/donor.service';
import { DonorDTO } from '../../models/donor';
import { BloodType, ResusType } from '../../models/Blood';
import { Gender } from '../../models/gender';

@Component({
  selector: 'app-donor-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './donor-list.component.html',
  styleUrls: ['./donor-list.component.scss']
})
export class DonorListComponent implements OnInit {
  donors: DonorDTO[] = [];
  filteredDonors: DonorDTO[] = [];
  loading = true;
  error: string | null = null;

  // Filtres
  searchTerm = '';
  bloodTypeFilter = '';
  genderFilter = '';

  // Pagination
  currentPage = 1;
  itemsPerPage = 10;
  totalItems = 0;

  // Options pour les filtres
  bloodTypes = Object.values(BloodType);
  genders = Object.values(Gender);

  // Tri
  sortField: string = 'lastName';
  sortDirection: 'asc' | 'desc' = 'asc';

  constructor(private donorService: DonorService) { }

  ngOnInit(): void {
    this.loadDonors();
  }

  loadDonors(): void {
    this.loading = true;
    this.donorService.getAllDonors()
      .subscribe({
        next: (data) => {
          this.donors = data;
          this.applyFilters();
          this.loading = false;
        },
        error: (err) => {
          console.error('Erreur lors du chargement des donneurs', err);
          this.error = 'Impossible de charger la liste des donneurs. Veuillez réessayer plus tard.';
          this.loading = false;
        }
      });
  }

  applyFilters(): void {
    let filtered = [...this.donors];

    // Appliquer le filtre de recherche
    if (this.searchTerm) {
      const term = this.searchTerm.toLowerCase();
      filtered = filtered.filter(donor => 
        (donor.firstName?.toLowerCase().includes(term) || false) ||
        (donor.lastName?.toLowerCase().includes(term) || false) ||
        (donor.email?.toLowerCase().includes(term) || false) ||
        (donor.phoneNumber?.includes(term) || false)
      );
    }

    // Appliquer le filtre de groupe sanguin
    if (this.bloodTypeFilter) {
      filtered = filtered.filter(donor => donor.bloodType === this.bloodTypeFilter);
    }

    // Appliquer le filtre de genre
    if (this.genderFilter) {
      filtered = filtered.filter(donor => donor.gender === this.genderFilter);
    }

    // Appliquer le tri
    filtered = this.sortDonors(filtered);

    // Mettre à jour les données filtrées
    this.filteredDonors = filtered;
    this.totalItems = filtered.length;
  }

  resetFilters(): void {
    this.searchTerm = '';
    this.bloodTypeFilter = '';
    this.genderFilter = '';
    this.applyFilters();
  }

  sortDonors(donors: DonorDTO[]): DonorDTO[] {
    return donors.sort((a, b) => {
      let fieldA: any = this.getNestedProperty(a, this.sortField);
      let fieldB: any = this.getNestedProperty(b, this.sortField);

      // Gérer les valeurs null ou undefined
      if (fieldA === null || fieldA === undefined) return this.sortDirection === 'asc' ? -1 : 1;
      if (fieldB === null || fieldB === undefined) return this.sortDirection === 'asc' ? 1 : -1;

      // Comparer les dates
      if (this.sortField === 'birthDate' || this.sortField === 'lastDonationDate') {
        fieldA = new Date(fieldA).getTime();
        fieldB = new Date(fieldB).getTime();
      }

      // Comparer les nombres
      if (typeof fieldA === 'number' && typeof fieldB === 'number') {
        return this.sortDirection === 'asc' ? fieldA - fieldB : fieldB - fieldA;
      }

      // Comparer les chaînes de caractères
      const compareResult = String(fieldA).localeCompare(String(fieldB));
      return this.sortDirection === 'asc' ? compareResult : -compareResult;
    });
  }

  // Fonction pour accéder aux propriétés imbriquées
  getNestedProperty(obj: any, path: string): any {
    return path.split('.').reduce((acc, part) => {
      return acc && acc[part] !== undefined ? acc[part] : null;
    }, obj);
  }

  sortBy(field: string): void {
    if (this.sortField === field) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;
      this.sortDirection = 'asc';
    }
    this.applyFilters();
  }

  getSortIcon(field: string): string {
    if (this.sortField !== field) return 'bi-sort';
    return this.sortDirection === 'asc' ? 'bi-sort-up' : 'bi-sort-down';
  }

  // Pagination
  get paginatedDonors(): DonorDTO[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredDonors.slice(startIndex, startIndex + this.itemsPerPage);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredDonors.length / this.itemsPerPage);
  }

  get pages(): number[] {
    const result = [];
    for (let i = 1; i <= this.totalPages; i++) {
      result.push(i);
    }
    return result;
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  // Formatage et affichage
  formatDate(dateString: string | undefined): string {
    if (!dateString) return 'Non disponible';
    
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('fr-FR', { 
        year: 'numeric', 
        month: 'long', 
        day: 'numeric'
      });
    } catch (e) {
      return 'Date invalide';
    }
  }

  formatBloodType(bloodType: BloodType | undefined, resusType: ResusType | undefined): string {
    if (!bloodType || !resusType) return 'Non spécifié';
    const resusSymbol = resusType === ResusType.POSITIVE ? '+' : '-';
    return `${bloodType}${resusSymbol}`;
  }

  translateGender(gender: Gender | undefined): string {
    if (!gender) return 'Non spécifié';
    return gender === Gender.MALE ? 'Homme' : 'Femme';
  }

  // Suppression d'un donneur
  deleteDonor(id: string | undefined): void {
    if (!id) return;
    
    if (confirm('Êtes-vous sûr de vouloir supprimer ce donneur ?')) {
      this.donorService.deleteDonor(id).subscribe({
        next: () => {
          this.donors = this.donors.filter(donor => donor.id !== id);
          this.applyFilters();
        },
        error: (err) => {
          console.error('Erreur lors de la suppression du donneur', err);
          alert('Impossible de supprimer le donneur. Veuillez réessayer plus tard.');
        }
      });
    }
  }
}