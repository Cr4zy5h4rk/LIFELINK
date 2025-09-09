import { AddressDTO } from "./Address";
import { ArticleDTO } from "./Article";
import { BloodType, ResusType } from "./Blood";
import { DonationCenterDTO } from "./DonationCenterDTO";
import { Gender } from "./gender";
import { OrganizationPartnerDTO } from "./OrganizationPartner";
import {RoleDTO} from "./role";

export interface DonorDTO {
  id?: string;
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
  gender?: Gender;
  birthDate?: string; // format ISO pour les dates
  email?: string;
  password?: string;
  weight?: number;
  size?: number;
  fidelityPoints?: number;
  bloodType?: BloodType;
  resusType?: ResusType;
  receiveOtpForBloodRequest?: boolean;
  lastDonationDate?: string; // format ISO
  address?: AddressDTO;
  favorites?: ArticleDTO[];
  roles?: RoleDTO[];
  medicalStaff?: DonationCenterDTO;
  picture?: string;
  employees?: OrganizationPartnerDTO;
}
