import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule, AbstractControl, ValidatorFn, ValidationErrors } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { DonationCenterService } from '../../services/DonationCenter/donation-center.service';
import { CampaignService } from '../../services/Campaign/campaign.service';
import { DonationCenterDTO } from '../../models/DonationCenterDTO';
import { CampaignDTO } from '../../models/Campaigns';
import { DonorService } from '../../services/donor.service';
import { CarnetService } from '../../services/carnet/carnet.service';
import { ShareInfoRequestDTO, ShareInfoRequestService, ShareInfoSelectionDTO } from '../../services/ShareInfo/share-info-request-service.service';


@Component({
  selector: 'app-share-info-requests',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    RouterModule
  ],
  providers: [DatePipe],
  templateUrl: './share-info-request.component.html',
  styleUrls: ['./share-info-request.component.scss']
})
export class ShareInfoRequestComponent implements OnInit {
  // Données et états
  requests: ShareInfoRequestDTO[] = [];
  filteredRequests: ShareInfoRequestDTO[] = [];
  campaigns: CampaignDTO[] = [];
  centers: DonationCenterDTO[] = [];

  selectionForm!: FormGroup;
  showSelectionModal = false;
  currentRequest: any = null;
  isSubmittingSelection = false;
  // Filtres
  selectedStatus: string = 'all';
  selectedType: string = 'all';
  searchTerm: string = '';
  viewMode: 'card' | 'list' = 'card';

  showInfoModal = false;
  sharedSelections: any = null;
  donorInfo: any = null;
  medicalData: any = null;
  
  // États UI
  isLoading: boolean = true;
  showModal: boolean = false;
  showDeleteConfirm: boolean = false;
  isSubmitting: boolean = false;
  isDeleting: boolean = false;
  requestToDelete: any = null;
  
  // Statistiques
  stats = {
    total: 0,
    pending: 0,
    accepted: 0,
    rejected: 0
  };
  
  // Formulaire
requestForm: FormGroup;
selectionFormControls!: FormGroup;
  
  constructor(
    private shareInfoService: ShareInfoRequestService,
    private campaignService: CampaignService,
    private centerService: DonationCenterService,
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private donorService: DonorService,
    private carnetService: CarnetService
  ) {

// Assurez-vous que cette initialisation est dans le constructeur ou ngOnInit
this.selectionFormControls = this.fb.group({
      // Infos personnelles
      shareDateNaissance: [false],
      shareAddress: [false],
      shareEmail: [false],
      
      // Infos médicales
      shareInfosMedicales: [false],
      shareVaccinations: [false],
      shareConsultations: [false],
      shareAppareilsMedicaux: [false],
      shareDonSanguin: [false],
      shareAlertes: [false],
      shareContactsUrgence: [false],
      shareAssurance: [false],
      shareModeDeVie: [false]
    });
    
    // Validator dynamique selon le type sélectionné
    this.requestForm = this.fb.group({
      phoneNumber: ['', [Validators.required, Validators.pattern(/^\+?[0-9\s]{8,15}$/)]],
      campaignId: [''],
      centerId: [''],
      selectionInfo: this.selectionFormControls
    });
    
    // Ajouter une validation personnalisée pour s'assurer qu'au moins un des deux est sélectionné
    this.requestForm.setValidators(this.atLeastOneFieldValidator('campaignId', 'centerId'));
  }

  
  ngOnInit(): void {
    this.loadRequests();
    this.loadCampaigns();
    this.loadCenters();
  }

  atLeastOneFieldValidator(field1: string, field2: string): ValidatorFn {
    return (group: AbstractControl): ValidationErrors | null => {
      const value1 = group.get(field1)?.value;
      const value2 = group.get(field2)?.value;
      
      return value1 || value2 ? null : { atLeastOneRequired: true };
    };
  }
  
  // Chargement des données
  loadRequests(): void {
    this.isLoading = true;
    this.shareInfoService.getAllRequests().subscribe({
      next: (data) => {
        this.requests = data;
        this.applyFilters();
        this.calculateStats();
        this.isLoading = false;
        console.log(data);
      },
      error: (error) => {
        console.error('Erreur lors du chargement des demandes', error);
        this.isLoading = false;
      }
    });
  }
  
  loadCampaigns(): void {
    this.campaignService.getAllCampaigns().subscribe({
      next: (data) => {
        this.campaigns = data;
      },
      error: (error) => console.error('Erreur lors du chargement des campagnes', error)
    });
  }

  extractTitle(scheduleText: string): string {
    if (!scheduleText) return '';
    
    const titleMatch = scheduleText.match(/Title: ([^|]+)(?:\||$)/);
    return titleMatch ? titleMatch[1].trim() : scheduleText;
  }
  
  loadCenters(): void {
    this.centerService.getAllCenters().subscribe({
      next: (data) => {
        this.centers = data;
        console.log("centers",data);
      },
      error: (error) => console.error('Erreur lors du chargement des centres', error)
    });
  }
  
  // Filtrage
  applyFilters(): void {
    this.filteredRequests = this.requests.filter(request => {
      // Filtre par statut
      if (this.selectedStatus !== 'all') {
        if (this.selectedStatus === 'pending' && request.accepted !== null) return false;
        if (this.selectedStatus === 'accepted' && request.accepted !== true) return false;
        if (this.selectedStatus === 'rejected' && request.accepted !== false) return false;
      }
      
      // Filtre par type
      if (this.selectedType !== 'all') {
        if (this.selectedType === 'campaign' && !request.bloodDonationCampaign) return false;
        if (this.selectedType === 'center' && !request.donationCenter) return false;
      }
      
      // Filtre par recherche (numéro de téléphone)
      if (this.searchTerm && !request.phoneNumber?.toLowerCase().includes(this.searchTerm.toLowerCase())) {
        return false;
      }
      
      return true;
    });
  }
  
  resetFilters(): void {
    this.selectedStatus = 'all';
    this.selectedType = 'all';
    this.searchTerm = '';
    this.applyFilters();
  }
  
  // Calcul des statistiques
  calculateStats(): void {
    this.stats.total = this.requests.length;
    this.stats.pending = this.requests.filter(r => r.accepted === null).length;
    this.stats.accepted = this.requests.filter(r => r.accepted === true).length;
    this.stats.rejected = this.requests.filter(r => r.accepted === false).length;
  }
  
  // Formatage des données
  formatDate(dateStr?: string): string {
    if (!dateStr) return 'N/A';
    return this.datePipe.transform(dateStr, 'dd/MM/yyyy HH:mm') || 'N/A';
  }
  
  getStatusLabel(request: ShareInfoRequestDTO): string {
    if (request.accepted === null) return 'En attente';
    return request.accepted ? 'Acceptée' : 'Rejetée';
  }
  
  getStatusClass(request: ShareInfoRequestDTO): string {
    if (request.accepted === null) return 'status-pending';
    return request.accepted ? 'status-accepted' : 'status-rejected';
  }
  
  getStatusBadgeClass(request: ShareInfoRequestDTO): string {
    if (request.accepted === null) return 'status-badge-pending';
    return request.accepted ? 'status-badge-accepted' : 'status-badge-rejected';
  }
  
  getRequestType(request: ShareInfoRequestDTO): string {
    if (request.bloodDonationCampaign) return 'Campagne';
    if (request.donationCenter) return 'Centre';
    return 'Non spécifié';
  }
  
  getCampaignName(campaignId?: number): string {
    if (!campaignId) return 'N/A';
    const campaign = this.campaigns.find(c => c.id === campaignId);
    return campaign ? campaign.title : `ID: ${campaignId}`;
  }
  
  getCenterName(centerId?: number): string {
    if (!centerId) return 'N/A';
    const center = this.centers.find(c => c.id === centerId);
    return center ? center.hospital : `ID: ${centerId}`;
  }
  
  // Actions sur les requêtes
  // handleAccept(request: any): void {
  //   this.shareInfoService.acceptRequest(request.id).subscribe({
  //     next: (result) => {
  //       const index = this.requests.findIndex(r => r.id === request.id);
  //       if (index !== -1) {
  //         this.requests[index] = {...result};
  //         this.applyFilters();
  //         this.calculateStats();
  //       }
  //     },
  //     error: (error) => console.error('Erreur lors de l\'acceptation de la demande', error)
  //   });
  // }
  
  handleReject(request: any): void {
    this.shareInfoService.rejectRequest(request.id).subscribe({
      next: (result) => {
        const index = this.requests.findIndex(r => r.id === request.id);
        if (index !== -1) {
          this.requests[index] = {...result};
          this.applyFilters();
          this.calculateStats();
        }
      },
      error: (error) => console.error('Erreur lors du rejet de la demande', error)
    });
  }
  
  confirmDelete(request: any): void {
    this.requestToDelete = request;
    this.showDeleteConfirm = true;
  }
  
  handleDelete(): void {
    if (!this.requestToDelete) return;
    
    this.isDeleting = true;
    this.shareInfoService.deleteRequest(this.requestToDelete.id).subscribe({
      next: () => {
        this.requests = this.requests.filter(r => r.id !== this.requestToDelete.id);
        this.applyFilters();
        this.calculateStats();
        this.cancelDelete();
        this.isDeleting = false;
      },
      error: (error) => {
        console.error('Erreur lors de la suppression de la demande', error);
        this.isDeleting = false;
      }
    });
  }
  
  cancelDelete(): void {
    this.showDeleteConfirm = false;
    this.requestToDelete = null;
  }
  
  // Gestion du modal
  showCreateModal(): void {
    this.requestForm.reset();
    this.showModal = true;
  }
  
  hideModal(event?: MouseEvent): void {
    if (event && (event.target as HTMLElement).className === 'modal-overlay') {
      this.showModal = false;
    } else if (!event) {
      this.showModal = false;
    }
  }
  
  // Création d'une nouvelle demande
  createRequest(): void {
    if (this.requestForm.invalid) return;
    
    const formValue = this.requestForm.value;
    const phoneNumber = formValue.phoneNumber;
    const campaignId = formValue.campaignId;
    const centerId = formValue.centerId;
    const selectionDTO = formValue.selectionInfo;
    
    // Vérifier si au moins une information est sélectionnée
    if (!this.isAnyInfoSelected(selectionDTO)) {
      // Ajouter un message d'erreur
      this.showError('Vous devez sélectionner au moins une information à partager.');
      return;
    }
    
    this.isSubmitting = true;
    this.shareInfoService.createRequestWithPhone(phoneNumber, selectionDTO, campaignId, centerId).subscribe({
      next: (result) => {
        this.requests.push(result);
        this.applyFilters();
        this.calculateStats();
        this.hideModal();
        this.isSubmitting = false;
        // Afficher un message de succès
        this.showSuccess('Demande créée avec succès.');
      },
      error: (error) => {
        console.error('Erreur lors de la création de la demande', error);
        this.isSubmitting = false;
        this.showError('Erreur lors de la création de la demande.');
      }
    });
  }

  isAnyInfoSelected(selection: any): boolean {
    return Object.values(selection).some(value => value === true);
  }
  
  // Méthodes pour afficher des notifications
  showSuccess(message: string): void {
    // Implémentation selon votre système de notification
    // Ex: this.toastr.success(message);
    console.log('Succès:', message);
  }
  
  showError(message: string): void {
    // Implémentation selon votre système de notification
    // Ex: this.toastr.error(message);
    console.error('Erreur:', message);
  }


  handleAccept(request: any): void {
    this.currentRequest = request;
    this.selectionForm.reset({
      shareDateNaissance: false,
      shareAddress: false,
      shareEmail: false,
      shareInfosMedicales: false,
      shareVaccinations: false,
      shareConsultations: false,
      shareAppareilsMedicaux: false,
      shareDonSanguin: false,
      shareAlertes: false,
      shareContactsUrgence: false,
      shareAssurance: false,
      shareModeDeVie: false
    });
    this.showSelectionModal = true;
  }
  
  hideSelectionModal(): void {
    this.showSelectionModal = false;
    this.currentRequest = null;
  }
  
isAllPersonalInfoChecked(): boolean {
  const selectionControls = this.requestForm.get('selectionInfo') as FormGroup;
  return (
    selectionControls.get('shareDateNaissance')?.value &&
    selectionControls.get('shareAddress')?.value &&
    selectionControls.get('shareEmail')?.value
  );
}

isAllMedicalInfoChecked(): boolean {
  const selectionControls = this.requestForm.get('selectionInfo') as FormGroup;
  return (
    selectionControls.get('shareInfosMedicales')?.value &&
    selectionControls.get('shareVaccinations')?.value &&
    selectionControls.get('shareConsultations')?.value &&
    selectionControls.get('shareAppareilsMedicaux')?.value &&
    selectionControls.get('shareDonSanguin')?.value &&
    selectionControls.get('shareAlertes')?.value &&
    selectionControls.get('shareContactsUrgence')?.value &&
    selectionControls.get('shareAssurance')?.value &&
    selectionControls.get('shareModeDeVie')?.value
  );
}

toggleAllPersonalInfo(event: any): void {
  const isChecked = event.target.checked;
  const selectionControls = this.requestForm.get('selectionInfo') as FormGroup;
  selectionControls.get('shareDateNaissance')?.setValue(isChecked);
  selectionControls.get('shareAddress')?.setValue(isChecked);
  selectionControls.get('shareEmail')?.setValue(isChecked);
}

toggleAllMedicalInfo(event: any): void {
  const isChecked = event.target.checked;
  const selectionControls = this.requestForm.get('selectionInfo') as FormGroup;
  selectionControls.get('shareInfosMedicales')?.setValue(isChecked);
  selectionControls.get('shareVaccinations')?.setValue(isChecked);
  selectionControls.get('shareConsultations')?.setValue(isChecked);
  selectionControls.get('shareAppareilsMedicaux')?.setValue(isChecked);
  selectionControls.get('shareDonSanguin')?.setValue(isChecked);
  selectionControls.get('shareAlertes')?.setValue(isChecked);
  selectionControls.get('shareContactsUrgence')?.setValue(isChecked);
  selectionControls.get('shareAssurance')?.setValue(isChecked);
  selectionControls.get('shareModeDeVie')?.setValue(isChecked);
}
  
  isAnyOptionSelected(): boolean {
    for (const key in this.selectionForm.controls) {
      if (this.selectionForm.get(key)?.value === true) {
        return true;
      }
    }
    return false;
  }
  
  confirmSelection(): void {
    if (this.isSubmittingSelection || !this.isAnyOptionSelected() || !this.currentRequest) return;
    
    this.isSubmittingSelection = true;
    const selectionData = this.selectionForm.value;
    
    this.shareInfoService.acceptRequestWithSelection(this.currentRequest.id, selectionData)
      .subscribe({
        next: (result) => {
          const index = this.requests.findIndex(r => r.id === this.currentRequest.id);
          if (index !== -1) {
            this.requests[index] = {...result};
            this.applyFilters();
            this.calculateStats();
          }
          this.hideSelectionModal();
          this.isSubmittingSelection = false;
        },
        error: (error) => {
          console.error('Erreur lors de l\'acceptation de la demande', error);
          this.isSubmittingSelection = false;
        }
      });
  }

viewSharedInfo(request: any): void {
  console.log('Afficher les informations partagées pour la demande:', request);
  
  // Vérifier si nous avons des informations à afficher
  if (!request.sharedInfoUser && !request.sharedInfoSelection) {
    console.warn('Aucune information partagée disponible pour cette demande');
    // Afficher un message à l'utilisateur
    // Par exemple: this.toastr.warning('Aucune information partagée n\'est disponible pour cette demande.');
    return;
  }
  
  this.currentRequest = request;
  
  // Essayer d'abord sharedInfoUser, puis sharedInfoSelection si le premier est vide
  const sharedInfoData = request.sharedInfoUser || request.sharedInfoSelection;
  console.log('Données d\'informations partagées brutes:', sharedInfoData);
  
  this.loadSharedInfo(request);
  this.showInfoModal = true;
}

hideInfoModal(): void {
  this.showInfoModal = false;
  this.currentRequest = null;
  this.sharedSelections = null;
  this.donorInfo = null;
  this.medicalData = null;
}

loadSharedInfo(request: any): void {
  // Essayer d'abord sharedInfoUser, puis sharedInfoSelection si le premier est vide
  const sharedInfoData = request.sharedInfoUser || request.sharedInfoSelection;
  console.log('Données d\'informations partagées à charger:', sharedInfoData);
  
  // Charger les informations partagées
  this.sharedSelections = this.parseSharedInfo(sharedInfoData);
  console.log('Informations partagées parsées:', this.sharedSelections);
  
  // Si aucune information n'a été correctement parsée, initialiser un objet vide
  if (!this.sharedSelections || Object.keys(this.sharedSelections).length === 0) {
    console.warn('Aucune information partagée n\'a pu être parsée');
    this.sharedSelections = {
      shareDateNaissance: false,
      shareAddress: false,
      shareEmail: false,
      shareInfosMedicales: false,
      shareVaccinations: false,
      shareConsultations: false,
      shareAppareilsMedicaux: false,
      shareDonSanguin: false,
      shareAlertes: false,
      shareContactsUrgence: false,
      shareAssurance: false,
      shareModeDeVie: false
    };
  }
  
  // Charger les informations du donneur seulement si nécessaire
  if (this.hasSharedPersonalInfo()) {
    console.log('Chargement des informations personnelles du donneur...');
    this.donorService.getDonorByPhoneNumber(request.phoneNumber).subscribe({
      next: (donor) => {
        console.log('Informations du donneur récupérées:', donor);
        this.donorInfo = donor;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des informations du donneur', error);
        this.donorInfo = null;
      }
    });
  } else {
    this.donorInfo = null;
  }
  
  // Charger les informations médicales seulement si nécessaire
  if (this.hasSharedMedicalInfo()) {
    console.log('Chargement des informations médicales...');
    this.carnetService.getDonorMedicalData(request.phoneNumber).subscribe({
      next: (medicalData) => {
        console.log('Données médicales récupérées:', medicalData);
        this.medicalData = medicalData;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des données médicales', error);
        this.medicalData = null;
      }
    });
  } else {
    this.medicalData = null;
  }
}

// Mise à jour de la méthode parseSharedInfo
parseSharedInfo(sharedInfoJson: string): any {
  if (!sharedInfoJson) return {};
  
  try {
    // Ajouter un log pour déboguer
    console.log("JSON à parser:", sharedInfoJson);
    const parsed = JSON.parse(sharedInfoJson);
    console.log("Résultat du parsing:", parsed);
    return parsed;
  } catch (e) {
    console.error('Erreur lors du parsing des informations partagées', e);
    // Si le format n'est pas un JSON valide, essayer d'autres formats possibles
    try {
      // Certains backends peuvent envoyer des objets sérialisés de manière non standard
      const cleanedJson = sharedInfoJson.replace(/(['"])?([a-zA-Z0-9_]+)(['"])?:/g, '"$2":');
      return JSON.parse(cleanedJson);
    } catch (e2) {
      console.error('Deuxième tentative de parsing échouée', e2);
      return {};
    }
  }
}

hasSharedPersonalInfo(): boolean {
  if (!this.sharedSelections) return false;
  
  return (
    this.sharedSelections.shareDateNaissance ||
    this.sharedSelections.shareAddress ||
    this.sharedSelections.shareEmail
  );
}

hasSharedMedicalInfo(): boolean {
  if (!this.sharedSelections) return false;
  
  return (
    this.sharedSelections.shareInfosMedicales ||
    this.sharedSelections.shareVaccinations ||
    this.sharedSelections.shareConsultations ||
    this.sharedSelections.shareAppareilsMedicaux ||
    this.sharedSelections.shareDonSanguin ||
    this.sharedSelections.shareAlertes ||
    this.sharedSelections.shareContactsUrgence ||
    this.sharedSelections.shareAssurance ||
    this.sharedSelections.shareModeDeVie
  );
}

hasSharedInfo(): boolean {
  return this.hasSharedPersonalInfo() || this.hasSharedMedicalInfo();
}

formatAddress(address: any): string {
  if (!address) return 'Non disponible';
  
  let formattedAddress = '';
  
  if (address.street) formattedAddress += address.street;
  if (address.city) formattedAddress += (formattedAddress ? ', ' : '') + address.city;
  if (address.postalCode) formattedAddress += (formattedAddress ? ' ' : '') + address.postalCode;
  if (address.country) formattedAddress += (formattedAddress ? ', ' : '') + address.country;
  
  return formattedAddress || 'Non disponible';
}
}