import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BloodRequestStatus, Emergency } from '../../../models/Emergency';
import { BloodType, ResusType } from '../../../models/Blood';
import { AlerteService } from '../../../services/alerte/alerte.service';

@Component({
  selector: 'app-alertes',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule],
  templateUrl: './alerte.component.html',
  styleUrls: ['./alerte.component.scss'],
  providers: [DatePipe]
})
export class AlerteComponent implements OnInit {
  // Tableau des urgences
  emergencies: Emergency[] = [];
  filteredEmergencies: Emergency[] = [];
  
  // État d'affichage
  loading = true;
  error: string | null = null;
  showForm = false;
  isEditMode = false;
  selectedEmergency: Emergency | null = null;
  showDetails = false;
  
  // Statistiques
  stats = {
    total: 0,
    pending: 0,
    active: 0
  };
  
  // Formulaire
  emergencyForm: FormGroup;
  
  // Options pour les listes déroulantes
  bloodTypes = Object.values(BloodType);
  resusTypes = Object.values(ResusType);
  
  // Filtre de recherche
  searchTerm = '';
  statusFilter: string = 'all';
  
  constructor(
    private alerteService: AlerteService,
    private fb: FormBuilder,
    private datePipe: DatePipe
  ) {
    this.emergencyForm = this.createEmergencyForm();
  }
  
  ngOnInit(): void {
    this.loadEmergencies();
  }
  
  // Méthode pour créer le formulaire
  createEmergencyForm(): FormGroup {
    return this.fb.group({
      id: [null],
      date: [new Date().toISOString().substring(0, 16), Validators.required],
      status: [BloodRequestStatus.PENDING, Validators.required],
      bloodType: ['', Validators.required],
      title: ['', Validators.required],
      resusType: ['', Validators.required],
      hospital: ['', Validators.required],
      details: ['', Validators.required],
      wolofAudioDetails: ['']
    });
  }
  
  // Charger les urgences depuis le service
  loadEmergencies(): void {
    this.loading = true;
    this.alerteService.getAllEmergencies().subscribe({
      next: (data) => {
        this.emergencies = data;
        this.applyFilters();
        this.calculateStats();
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des urgences', err);
        this.error = 'Impossible de charger les urgences. Veuillez réessayer.';
        this.loading = false;
      }
    });
  }
  
  // Appliquer les filtres
  applyFilters(): void {
    this.filteredEmergencies = this.emergencies.filter(emergency => {
      // Filtre par statut
      if (this.statusFilter !== 'all' && emergency.status !== this.statusFilter) {
        return false;
      }
      
      // Filtre par recherche
      if (this.searchTerm) {
        const searchTermLower = this.searchTerm.toLowerCase();
        const hospitalMatch = emergency.hospital?.toLowerCase().includes(searchTermLower);
        const detailsMatch = emergency.details?.toLowerCase().includes(searchTermLower);
        const bloodTypeMatch = emergency.bloodType?.toLowerCase().includes(searchTermLower);
        
        if (!hospitalMatch && !detailsMatch && !bloodTypeMatch) {
          return false;
        }
      }
      
      return true;
    });
  }
  
  // Calculer les statistiques
  calculateStats(): void {
    this.stats.total = this.emergencies.length;
    this.stats.pending = this.emergencies.filter(e => e.status === BloodRequestStatus.PENDING).length;
    this.stats.active = this.emergencies.filter(e => e.status !== BloodRequestStatus.CANCELLED).length;
  }
  
  // Ouvrir le formulaire pour créer une nouvelle urgence
  openNewEmergencyForm(): void {
    this.isEditMode = false;
    this.emergencyForm.reset();
    this.emergencyForm.patchValue({
      date: new Date().toISOString().substring(0, 16),
      status: BloodRequestStatus.PENDING
    });
    this.showForm = true;
  }
  
  // Ouvrir le formulaire pour éditer une urgence existante
  editEmergency(emergency: Emergency): void {
    this.isEditMode = true;
    this.emergencyForm.patchValue({
      ...emergency,
      date: emergency.date ? new Date(emergency.date).toISOString().substring(0, 16) : new Date().toISOString().substring(0, 16)
    });
    this.showForm = true;
  }
  
  // Fermer le formulaire
  closeForm(): void {
    this.showForm = false;
  }
  
  // Sauvegarder une urgence (nouvelle ou modifiée)
  saveEmergency(): void {
    if (this.emergencyForm.invalid) {
      return;
    }
    
    const formData = this.emergencyForm.value;
    
    if (this.isEditMode && formData.id) {
      // Mettre à jour une urgence existante
      this.alerteService.updateEmergency(formData.id, formData).subscribe({
        next: (updated) => {
          const index = this.emergencies.findIndex(e => e.id === updated.id);
          if (index !== -1) {
            this.emergencies[index] = updated;
          }
          this.applyFilters();
          this.calculateStats();
          this.showForm = false;
        },
        error: (err) => {
          console.error('Erreur lors de la mise à jour', err);
          // TODO: Afficher un message d'erreur à l'utilisateur
        }
      });
    } else {
      // Créer une nouvelle urgence
      this.alerteService.createEmergency(formData).subscribe({
        next: (created) => {
          this.emergencies.push(created);
          this.applyFilters();
          this.calculateStats();
          this.showForm = false;
        },
        error: (err) => {
          console.error('Erreur lors de la création', err);
          // TODO: Afficher un message d'erreur à l'utilisateur
        }
      });
    }
  }
  
  // Afficher les détails d'une urgence
  viewEmergencyDetails(emergency: Emergency): void {
    this.selectedEmergency = emergency;
    this.showDetails = true;
  }
  
  // Fermer la vue détaillée
  closeDetails(): void {
    this.showDetails = false;
    this.selectedEmergency = null;
  }
  
  // Supprimer une urgence
  deleteEmergency(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette urgence ?')) {
      this.alerteService.deleteEmergency(id).subscribe({
        next: () => {
          this.emergencies = this.emergencies.filter(e => e.id !== id);
          this.applyFilters();
          this.calculateStats();
        },
        error: (err) => {
          console.error('Erreur lors de la suppression', err);
          // TODO: Afficher un message d'erreur à l'utilisateur
        }
      });
    }
  }
  
  // Annuler une urgence
  cancelEmergency(emergency: Emergency): void {
    if (confirm('Êtes-vous sûr de vouloir annuler cette urgence ?')) {
      const updatedEmergency = {
        ...emergency,
        status: BloodRequestStatus.CANCELLED
      };
      
      this.alerteService.updateEmergency(emergency.id!, updatedEmergency).subscribe({
        next: (updated) => {
          const index = this.emergencies.findIndex(e => e.id === updated.id);
          if (index !== -1) {
            this.emergencies[index] = updated;
          }
          this.applyFilters();
          this.calculateStats();
        },
        error: (err) => {
          console.error('Erreur lors de l\'annulation', err);
          // TODO: Afficher un message d'erreur à l'utilisateur
        }
      });
    }
  }
  
  // Formater l'affichage de la date
  formatDate(date: string | Date | undefined): string {
    if (!date) return 'Non spécifiée';
    return this.datePipe.transform(date, 'dd/MM/yyyy HH:mm') || 'Date invalide';
  }
  
  // Obtenir la classe CSS pour le badge de statut
  getStatusClass(status: string | undefined): string {
    if (!status) return 'status-unknown';
    
    switch (status) {
      case BloodRequestStatus.PENDING:
        return 'status-pending';
      case BloodRequestStatus.CANCELLED:
        return 'status-cancelled';
      default:
        return 'status-unknown';
    }
  }
  
  // Obtenir le libellé pour le statut
  getStatusLabel(status: string | undefined): string {
    if (!status) return 'Inconnu';
    
    switch (status) {
      case BloodRequestStatus.PENDING:
        return 'En attente';
      case BloodRequestStatus.CANCELLED:
        return 'Annulée';
      default:
        return 'Inconnu';
    }
  }

}