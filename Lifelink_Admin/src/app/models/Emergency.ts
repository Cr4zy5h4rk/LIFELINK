import { BloodType, ResusType } from "./Blood";
import { DonationCenterDTO } from "./DonationCenterDTO";

export enum BloodRequestStatus {
  PENDING = 'PENDING',
  CANCELLED = 'CANCELLED',
}

// Interface principale pour BloodRequest
export interface Emergency {
  id?: number;
  date?: string | Date;   
  title?: string;  // Modifié de createdAt à date
  status?: BloodRequestStatus;
  bloodType?: String;
  details?: string;
  wolofAudioDetails?: string;
  hospital?: string;  // Modifié de donationCenter à hospital
}