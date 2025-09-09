import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { 
  DonorService
} from '../../services/donor.service';
import { BloodType, ResusType } from '../../models/Blood';
import { Gender } from '../../models/gender';
import { DonorDTO } from '../../models/donor';
import { AddressDTO } from '../../models/Address';

@Component({
  selector: 'app-donor-edit',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './donor-edit.component.html',
  styleUrls: ['./donor-edit.component.scss']
})
export class DonorEditComponent implements OnInit {
  donorForm!: FormGroup;
  addressForm!: FormGroup;
  isEditMode = false;
  loading = false;
  submitting = false;
  error: string | null = null;
  donorId: string | null = null;
  
  // Options pour les enums
  bloodTypes = Object.values(BloodType);
  genders = Object.values(Gender);
  resusTypes = Object.values(ResusType);
  
  constructor(
    private fb: FormBuilder,
    private donorService: DonorService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.initForm();
    
    // Vérifier si on est en mode édition
    const id = this.route.snapshot.paramMap.get('id');
    if (id && id !== 'new') {
      this.isEditMode = true;
      this.donorId = String(id);
      this.loadDonorData(this.donorId);
    }
  }

  initForm(): void {
    // Initialiser le formulaire d'adresse
    this.addressForm = this.fb.group({
      id: [null],
      street: [''],
      city: [''],
      postalCode: [''],
      country: ['']
    });
    
    // Initialiser le formulaire du donneur
    this.donorForm = this.fb.group({
      id: [null],
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      phoneNumber: ['', [Validators.required, Validators.pattern(/^[0-9+\s-]{8,15}$/)]],
      gender: [null, Validators.required],
      birthDate: [null, Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', this.isEditMode ? [] : [Validators.required, Validators.minLength(8)]],
      weight: [null, [Validators.min(20), Validators.max(200)]],
      size: [null, [Validators.min(100), Validators.max(250)]],
      fidelityPoints: [0],
      bloodType: [null],
      resusType: [null],
      receiveOtpForBloodRequest: [true],
      lastDonationDate: [null],
      picture: [''],
      address: this.addressForm
    });
  }

  loadDonorData(id: string): void {
    this.loading = true;
    this.donorService.getDonorByPhoneNumber(id).subscribe({
      next: (donor) => {
        // Formatter les dates pour le formulaire
        const formattedDonor = {
          ...donor,
          birthDate: donor.birthDate ? this.formatDateForInput(donor.birthDate) : null,
          lastDonationDate: donor.lastDonationDate ? this.formatDateForInput(donor.lastDonationDate) : null
        };
        
        // Remplir le formulaire d'adresse si disponible
        if (donor.address) {
          this.addressForm.patchValue(donor.address);
        }
        
        // Ne pas inclure le mot de passe dans le formulaire d'édition
        const { password, ...donorWithoutPassword } = formattedDonor;
        
        // Remplir le formulaire principal
        this.donorForm.patchValue(donorWithoutPassword);
        
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des données du donneur', err);
        this.error = 'Impossible de charger les données du donneur. Veuillez réessayer plus tard.';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.donorForm.invalid) {
      // Marquer tous les champs comme touchés pour afficher les erreurs
      this.markFormGroupTouched(this.donorForm);
      return;
    }
    
    this.submitting = true;
    
    // Préparer les données du donneur
    const donorData: DonorDTO = this.prepareDonorData();
    
    // Soumettre le formulaire selon le mode (création ou édition)
    const request = this.isEditMode 
      ? this.donorService.updateDonor(donorData)
      : this.donorService.saveDonor(donorData);
      
    request.subscribe({
      next: (donor) => {
        this.router.navigate(['/dashboard/donors']);
      },
      error: (err) => {
        console.error('Erreur lors de l\'enregistrement du donneur', err);
        this.error = 'Impossible d\'enregistrer le donneur. Veuillez réessayer plus tard.';
        this.submitting = false;
      }
    });
  }

  prepareDonorData(): DonorDTO {
    const formValue = this.donorForm.value;
    
    // Formater les dates pour l'API
    const birthDate = formValue.birthDate ? new Date(formValue.birthDate).toISOString() : undefined;
    const lastDonationDate = formValue.lastDonationDate ? new Date(formValue.lastDonationDate).toISOString() : undefined;
    
    // Créer l'objet d'adresse si des valeurs sont présentes
    let address: AddressDTO | undefined = undefined;
    const addressValue = this.addressForm.value;
    
    if (addressValue.street || addressValue.city || addressValue.postalCode || addressValue.country) {
      address = addressValue;
    }
    
    // Ne pas inclure le mot de passe si en mode édition et que le champ est vide
    const password = this.isEditMode && !formValue.password ? undefined : formValue.password;
    
    return {
      ...formValue,
      birthDate,
      lastDonationDate,
      address,
      password
    };
  }

  // Utilitaire pour marquer tous les champs comme touchés
  markFormGroupTouched(formGroup: FormGroup): void {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();
      
      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

  // Formater une date ISO en format local pour input date
  formatDateForInput(isoDate: string): string {
    const date = new Date(isoDate);
    return date.toISOString().split('T')[0];
  }

  // Vérifier si un contrôle a des erreurs
  hasError(controlName: string): boolean {
    const control = this.donorForm.get(controlName);
    return control !== null && control.invalid && (control.dirty || control.touched);
  }

  // Obtenir le message d'erreur pour un contrôle
  getErrorMessage(controlName: string): string {
    const control = this.donorForm.get(controlName);
    
    if (!control) return '';
    if (!control.errors) return '';
    
    if (control.errors['required']) return 'Ce champ est requis';
    if (control.errors['email']) return 'Veuillez entrer une adresse email valide';
    if (control.errors['minlength']) {
      return `Ce champ doit contenir au moins ${control.errors['minlength'].requiredLength} caractères`;
    }
    if (control.errors['maxlength']) {
      return `Ce champ ne doit pas dépasser ${control.errors['maxlength'].requiredLength} caractères`;
    }
    if (control.errors['pattern']) return 'Format invalide';
    if (control.errors['min']) return `La valeur minimale est ${control.errors['min'].min}`;
    if (control.errors['max']) return `La valeur maximale est ${control.errors['max'].max}`;
    
    return 'Champ invalide';
  }
}