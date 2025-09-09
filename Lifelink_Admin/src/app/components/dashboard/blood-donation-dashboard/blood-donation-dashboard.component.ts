import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CampaignService } from '../../../services/Campaign/campaign.service';
import { CampaignDTO, CampaignStatus } from '../../../models/Campaigns';

@Component({
  selector: 'app-campaign-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule
  ],
  providers: [DatePipe],
  templateUrl: './blood-donation-dashboard.component.html',
  styleUrls: ['./blood-donation-dashboard.component.scss']
})
export class BloodDonationDashboardComponent implements OnInit {
  // Listes des campagnes
  campaigns: CampaignDTO[] = [];
  filteredCampaigns: CampaignDTO[] = [];
  
  // Formulaire
  campaignForm: FormGroup;
  showForm = false;
  isEditing = false;
  
  // Suppression
  showDeleteConfirm = false;
  campaignToDelete: CampaignDTO | null = null;
  
  // État du filtre
  currentFilter = 'all';
  
  // Statistiques
  stats = {
    total: 0,
    active: 0,
    planned: 0,
    completed: 0
  };

  constructor(
    private campaignService: CampaignService,
    private fb: FormBuilder,
    private datePipe: DatePipe
  ) {
    this.campaignForm = this.createCampaignForm();
  }

  ngOnInit(): void {
    this.loadCampaigns();
  }

  // Créer le formulaire pour les campagnes
  createCampaignForm(): FormGroup {
    return this.fb.group({
      id: [null],
      title: ['', Validators.required],
      description: ['', Validators.required], // URL audio
      imageUrl: [''],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      location: ['', Validators.required],
      contactPhone: [''],
      schedule: ['', Validators.required], // Informations additionnelles
      status: [CampaignStatus.NOT_STARTED, Validators.required]
    });
  }

  // Charger toutes les campagnes
  loadCampaigns(): void {
    this.campaignService.getAllCampaigns().subscribe({
      next: (data) => {
        this.campaigns = data;
        this.filterCampaigns();
        this.calculateStats();
      },
      error: (error) => {
        console.error('Erreur lors du chargement des campagnes', error);
        // Ici on pourrait implémenter un système de notification pour l'utilisateur
      }
    });
  }

  // Filtrer les campagnes par statut
  filterByStatus(status: string): void {
    this.currentFilter = status;
    this.filterCampaigns();
  }

  // Appliquer le filtre actuel aux campagnes
  filterCampaigns(): void {
    if (this.currentFilter === 'all') {
      this.filteredCampaigns = [...this.campaigns];
    } else {
      this.filteredCampaigns = this.campaigns
        .filter(campaign => campaign.status === this.currentFilter as CampaignStatus);
    }
  }

  // Calculer les statistiques
  calculateStats(): void {
    this.stats.total = this.campaigns.length;
    this.stats.active = this.campaigns.filter(c => c.status === CampaignStatus.IN_PROGRESS).length;
    this.stats.planned = this.campaigns.filter(c => c.status === CampaignStatus.NOT_STARTED).length;
    this.stats.completed = this.campaigns.filter(c => c.status === CampaignStatus.COMPLETED).length;
  }

  // Extraire les informations du champ schedule
  getScheduleInfo(scheduleText: string, field: string): string {
    try {
      const regex = new RegExp(`${field}: ([^|]+)(?:\\||$)`);
      const match = scheduleText.match(regex);
      return match ? match[1].trim() : '';
    } catch (error) {
      console.error(`Erreur lors de l'extraction de ${field} depuis le texte du schedule`, error);
      return '';
    }
  }

  // Afficher le formulaire d'ajout
  showAddForm(): void {
    this.isEditing = false;
    this.campaignForm.reset();
    this.campaignForm.patchValue({
      status: CampaignStatus.NOT_STARTED
    });
    this.showForm = true;
  }

  // Éditer une campagne existante
  editCampaign(campaign: CampaignDTO): void {
    this.isEditing = true;
    
    // Formatter les dates pour l'input datetime-local
    const startDate = campaign.startDate ? this.formatDateForInput(campaign.startDate) : '';
    const endDate = campaign.endDate ? this.formatDateForInput(campaign.endDate) : '';
    
    this.campaignForm.patchValue({
      ...campaign,
      startDate,
      endDate
    });
    
    this.showForm = true;
  }

  // Formater la date pour l'input datetime-local
  formatDateForInput(dateStr: string): string {
    // Si on utilise une date ISO ou un timestamp
    const date = new Date(dateStr);
    return date.toISOString().slice(0, 16); // Format YYYY-MM-DDTHH:MM
  }

  // Enregistrer une campagne (création ou mise à jour)
  saveCampaign(): void {
    if (this.campaignForm.invalid) {
      return;
    }

    const campaignData = this.prepareCampaignData();
    
    if (this.isEditing) {
      this.campaignService.updateCampaign(campaignData).subscribe({
        next: (result) => {
          this.updateLocalCampaignList(result);
          this.showForm = false;
        },
        error: (error) => {
          console.error('Erreur lors de la mise à jour de la campagne', error);
        }
      });
    } else {
      this.campaignService.createCampaign(campaignData).subscribe({
        next: (result) => {
          this.campaigns.push(result);
          this.filterCampaigns();
          this.calculateStats();
          this.showForm = false;
        },
        error: (error) => {
          console.error('Erreur lors de la création de la campagne', error);
        }
      });
    }
  }

  // Préparer les données du formulaire
  prepareCampaignData(): CampaignDTO {
    const formValue = this.campaignForm.value;
    
    // Conversion des dates en format ISO si nécessaire
    const startDate = formValue.startDate ? new Date(formValue.startDate).toISOString() : '';
    const endDate = formValue.endDate ? new Date(formValue.endDate).toISOString() : '';
    
    return {
      ...formValue,
      startDate,
      endDate
    };
  }

  // Mettre à jour la liste locale des campagnes après une modification
  updateLocalCampaignList(updatedCampaign: CampaignDTO): void {
    const index = this.campaigns.findIndex(c => c.id === updatedCampaign.id);
    if (index !== -1) {
      this.campaigns[index] = updatedCampaign;
      this.filterCampaigns();
      this.calculateStats();
    }
  }

  // Annuler le formulaire
  cancelForm(): void {
    this.showForm = false;
  }

  // Supprimer une campagne (afficher confirmation)
  deleteCampaign(campaign: CampaignDTO): void {
    this.campaignToDelete = campaign;
    this.showDeleteConfirm = true;
  }

  // Confirmer la suppression
  confirmDelete(): void {
    if (this.campaignToDelete?.id) {
      this.campaignService.deleteCampaign(this.campaignToDelete.id).subscribe({
        next: () => {
          this.campaigns = this.campaigns.filter(c => c.id !== this.campaignToDelete?.id);
          this.filterCampaigns();
          this.calculateStats();
          this.showDeleteConfirm = false;
          this.campaignToDelete = null;
        },
        error: (error) => {
          console.error('Erreur lors de la suppression de la campagne', error);
          this.showDeleteConfirm = false;
        }
      });
    }
  }

  // Annuler la suppression
  cancelDelete(): void {
    this.showDeleteConfirm = false;
    this.campaignToDelete = null;
  }

  // Obtenir la classe CSS pour le statut de la campagne
  getCampaignStatusClass(status: CampaignStatus): string {
    switch (status) {
      case CampaignStatus.IN_PROGRESS:
        return 'status-active';
      case CampaignStatus.NOT_STARTED:
        return 'status-planned';
      case CampaignStatus.COMPLETED:
        return 'status-completed';
      case CampaignStatus.CANCELLED:
        return 'status-cancelled';
      default:
        return '';
    }
  }

  // Obtenir le libellé du statut
  getStatusLabel(status: CampaignStatus): string {
    switch (status) {
      case CampaignStatus.IN_PROGRESS:
        return 'Active';
      case CampaignStatus.NOT_STARTED:
        return 'Planifiée';
      case CampaignStatus.COMPLETED:
        return 'Terminée';
      case CampaignStatus.CANCELLED:
        return 'Annulée';
      default:
        return status;
    }
  }
}