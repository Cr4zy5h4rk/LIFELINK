import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { UserInfo } from '../../models/user-info';


@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  user: UserInfo | null = null;
  loading = true;
  error: string | null = null;
  
  // URL de l'image par défaut
  defaultProfileImage = 'assets/img/default-profile.png';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.getUser();
  }

  getUser() {
    try {
      const userData = this.authService.getUserData();
      this.user = userData;
      this.loading = false;
    } catch (error) {
      console.error('Erreur lors de la récupération des données utilisateur:', error);
      this.error = 'Impossible de charger vos informations. Veuillez réessayer plus tard.';
      this.loading = false;
    }
  }

  // Formate une date au format lisible
  formatDate(date: string | Date | null): string {
    if (!date) return 'Non disponible';
    
    try {
      const dateObj = typeof date === 'string' ? new Date(date) : date;
      return dateObj.toLocaleDateString('fr-FR', {
        day: 'numeric',
        month: 'long',
        year: 'numeric'
      });
    } catch (error) {
      return 'Date invalide';
    }
  }

  // Retourne le texte associé au genre
  getGenderText(gender: string | null): string {
    if (!gender) return 'Non spécifié';
    return gender === 'MALE' ? 'Homme' : gender === 'FEMALE' ? 'Femme' : gender;
  }

  // Calcule l'âge à partir de la date de naissance
  calculateAge(birthDate: string | Date | null): number | null {
    if (!birthDate) return null;
    
    try {
      const today = new Date();
      const birthDateObj = typeof birthDate === 'string' ? new Date(birthDate) : birthDate;
      let age = today.getFullYear() - birthDateObj.getFullYear();
      const m = today.getMonth() - birthDateObj.getMonth();
      
      if (m < 0 || (m === 0 && today.getDate() < birthDateObj.getDate())) {
        age--;
      }
      
      return age;
    } catch (error) {
      return null;
    }
  }

  // Retourne le type sanguin complet
  getFullBloodType(): string {
    if (!this.user) return '';
    if (!this.user.bloodType || !this.user.resusType) return 'Non spécifié';
    
    return `${this.user.bloodType}${this.user.resusType === 'POSITIVE' ? '+' : '-'}`;
  }
}