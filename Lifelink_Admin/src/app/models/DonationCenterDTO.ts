export interface DonationCenterDTO {
    id?: number;
    hospital: string;
    contact?: string;
    centerDetails?: string;
    wolofAudioDetails?: string;
    address?: any; // Vous pouvez définir une interface plus précise pour l'adresse
    organizationPartner?: any; // Pareil pour le partenaire
  }