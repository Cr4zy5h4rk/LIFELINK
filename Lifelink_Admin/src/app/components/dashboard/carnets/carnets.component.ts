import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { forkJoin } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CarnetService } from '../../../services/carnet/carnet.service';

@Component({
  selector: 'app-carnets',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './carnets.component.html',
  styleUrls: ['./carnets.component.scss']
})
export class CarnetsComponent implements OnInit {
  donorId: string | null = null;
  donor: any;
  medicalData: any;
  loading = true;
  error: string |null = null;

  constructor(
    private route: ActivatedRoute,
    private carnetService:CarnetService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.donorId = params.get('id');
      this.loadDonorData();
    });
  }

  loadDonorData(): void {
    this.loading = true;
    this.error = '';

    // Charger les données de base du donneur
    this.carnetService.getDonorById(this.donorId!)
      .pipe(
        catchError(this.handleError.bind(this))
      )
      .subscribe(donor => {
        this.donor = donor;
        this.loadDonorMedicalData();
      });
  }

  loadDonorMedicalData(): void {
    this.carnetService.getDonorMedicalData(this.donorId!)
      .pipe(
        catchError(this.handleError.bind(this))
      )
      .subscribe(medicalData => {
        this.medicalData = medicalData;
        this.loading = false;
      });
  }

  handleError(error: HttpErrorResponse): never {
    this.loading = false;
    
    if (error.status === 0) {
      this.error = 'Impossible de se connecter au serveur. Veuillez vérifier votre connexion internet.';
    } else if (error.status === 404) {
      this.error = 'Donneur non trouvé.';
    } else {
      this.error = `Une erreur s'est produite: ${error.message}`;
    }
    
    throw error;
  }

  // Méthodes utilitaires pour l'affichage
  getInitials(firstName: string, lastName: string): string {
    const firstInitial = firstName ? firstName.charAt(0).toUpperCase() : '';
    const lastInitial = lastName ? lastName.charAt(0).toUpperCase() : '';
    return `${firstInitial}${lastInitial}`;
  }

  formatDate(date: string | null): string {
    if (!date) return 'Non spécifié';
    
    try {
      const dateObj = new Date(date);
      return dateObj.toLocaleDateString('fr-FR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      });
    } catch (e) {
      return date; // Retourner la chaîne originale si le parsing échoue
    }
  }

  getAge(birthDate: string | null): number {
    if (!birthDate) return 0;
    
    try {
      const today = new Date();
      const birth = new Date(birthDate);
      let age = today.getFullYear() - birth.getFullYear();
      const monthDiff = today.getMonth() - birth.getMonth();
      
      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
        age--;
      }
      
      return age;
    } catch (e) {
      return 0;
    }
  }

  translateGender(gender: string | null): string {
    if (!gender) return 'Non spécifié';
    
    const genderMap = {
      'MALE': 'Homme',
      'FEMALE': 'Femme',
      'OTHER': 'Autre'
    };
    
    return genderMap[gender as keyof typeof genderMap] || gender;
  }

  formatBloodType(bloodType: string | null, resusType: string | null): string {
    if (!bloodType) return 'Non spécifié';
    
    const resus = resusType ? (resusType === 'POSITIVE' ? '+' : '-') : '';
    return `${bloodType}${resus}`;
  }
}