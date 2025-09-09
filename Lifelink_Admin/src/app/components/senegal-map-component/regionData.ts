import { Injectable } from '@angular/core';
import { BloodStats, StockLevel } from './senegal-map-component.component';

@Injectable({
  providedIn: 'root'
})
export class RegionsDataService {
  private regionsData: { [key: string]: BloodStats } = {
    'Dakar': {
      region: 'Dakar',
      population: 3500000,
      totalDonors: 42000,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.BON },
        'A-': { type: 'A-', stockLevel: StockLevel.NORMAL },
        'B+': { type: 'B+', stockLevel: StockLevel.BON },
        'B-': { type: 'B-', stockLevel: StockLevel.NORMAL },
        'AB+': { type: 'AB+', stockLevel: StockLevel.FAIBLE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.EXCELLENT },
        'O-': { type: 'O-', stockLevel: StockLevel.BON }
      }
    },
    'Thiès': {
      region: 'Thiès',
      population: 1900000,
      totalDonors: 15200,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.NORMAL },
        'A-': { type: 'A-', stockLevel: StockLevel.FAIBLE },
        'B+': { type: 'B+', stockLevel: StockLevel.NORMAL },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.FAIBLE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.BON },
        'O-': { type: 'O-', stockLevel: StockLevel.NORMAL }
      }
    },
    'Saint-Louis': {
      region: 'Saint-Louis',
      population: 1000000,
      totalDonors: 8500,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.NORMAL },
        'A-': { type: 'A-', stockLevel: StockLevel.FAIBLE },
        'B+': { type: 'B+', stockLevel: StockLevel.NORMAL },
        'B-': { type: 'B-', stockLevel: StockLevel.FAIBLE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.CRITIQUE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.BON },
        'O-': { type: 'O-', stockLevel: StockLevel.NORMAL }
      }
    },
    'Ziguinchor': {
      region: 'Ziguinchor',
      population: 600000,
      totalDonors: 3200,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.FAIBLE },
        'A-': { type: 'A-', stockLevel: StockLevel.CRITIQUE },
        'B+': { type: 'B+', stockLevel: StockLevel.FAIBLE },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.CRITIQUE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.NORMAL },
        'O-': { type: 'O-', stockLevel: StockLevel.FAIBLE }
      }
    },
    'Diourbel': {
      region: 'Diourbel',
      population: 1600000,
      totalDonors: 9600,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.NORMAL },
        'A-': { type: 'A-', stockLevel: StockLevel.FAIBLE },
        'B+': { type: 'B+', stockLevel: StockLevel.NORMAL },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.FAIBLE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.BON },
        'O-': { type: 'O-', stockLevel: StockLevel.NORMAL }
      }
    },
    'Tambacounda': {
      region: 'Tambacounda',
      population: 700000,
      totalDonors: 4900,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.FAIBLE },
        'A-': { type: 'A-', stockLevel: StockLevel.CRITIQUE },
        'B+': { type: 'B+', stockLevel: StockLevel.FAIBLE },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.CRITIQUE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.NORMAL },
        'O-': { type: 'O-', stockLevel: StockLevel.FAIBLE }
      }
    },
    'Kaolack': {
      region: 'Kaolack',
      population: 1200000,
      totalDonors: 7800,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.NORMAL },
        'A-': { type: 'A-', stockLevel: StockLevel.FAIBLE },
        'B+': { type: 'B+', stockLevel: StockLevel.NORMAL },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.FAIBLE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.BON },
        'O-': { type: 'O-', stockLevel: StockLevel.NORMAL }
      }
    },
    'Kolda': {
      region: 'Kolda',
      population: 750000,
      totalDonors: 5250,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.FAIBLE },
        'A-': { type: 'A-', stockLevel: StockLevel.CRITIQUE },
        'B+': { type: 'B+', stockLevel: StockLevel.FAIBLE },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.CRITIQUE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.NORMAL },
        'O-': { type: 'O-', stockLevel: StockLevel.FAIBLE }
      }
    },
    'Louga': {
      region: 'Louga',
      population: 880000,
      totalDonors: 5280,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.NORMAL },
        'A-': { type: 'A-', stockLevel: StockLevel.FAIBLE },
        'B+': { type: 'B+', stockLevel: StockLevel.NORMAL },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.FAIBLE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.BON },
        'O-': { type: 'O-', stockLevel: StockLevel.NORMAL }
      }
    },
    'Fatick': {
      region: 'Fatick',
      population: 750000,
      totalDonors: 4500,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.FAIBLE },
        'A-': { type: 'A-', stockLevel: StockLevel.CRITIQUE },
        'B+': { type: 'B+', stockLevel: StockLevel.FAIBLE },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.CRITIQUE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.NORMAL },
        'O-': { type: 'O-', stockLevel: StockLevel.FAIBLE }
      }
    },
    'Kaffrine': {
      region: 'Kaffrine',
      population: 580000,
      totalDonors: 2900,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.FAIBLE },
        'A-': { type: 'A-', stockLevel: StockLevel.CRITIQUE },
        'B+': { type: 'B+', stockLevel: StockLevel.FAIBLE },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.CRITIQUE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.NORMAL },
        'O-': { type: 'O-', stockLevel: StockLevel.FAIBLE }
      }
    },
    'Matam': {
      region: 'Matam',
      population: 680000,
      totalDonors: 4080,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.FAIBLE },
        'A-': { type: 'A-', stockLevel: StockLevel.CRITIQUE },
        'B+': { type: 'B+', stockLevel: StockLevel.FAIBLE },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.CRITIQUE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.NORMAL },
        'O-': { type: 'O-', stockLevel: StockLevel.FAIBLE }
      }
    },
    'Kédougou': {
      region: 'Kédougou',
      population: 180000,
      totalDonors: 900,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.CRITIQUE },
        'A-': { type: 'A-', stockLevel: StockLevel.CRITIQUE },
        'B+': { type: 'B+', stockLevel: StockLevel.CRITIQUE },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.CRITIQUE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.FAIBLE },
        'O-': { type: 'O-', stockLevel: StockLevel.CRITIQUE }
      }
    },
    'Sédhiou': {
      region: 'Sédhiou',
      population: 490000,
      totalDonors: 2450,
      bloodGroups: {
        'A+': { type: 'A+', stockLevel: StockLevel.FAIBLE },
        'A-': { type: 'A-', stockLevel: StockLevel.CRITIQUE },
        'B+': { type: 'B+', stockLevel: StockLevel.FAIBLE },
        'B-': { type: 'B-', stockLevel: StockLevel.CRITIQUE },
        'AB+': { type: 'AB+', stockLevel: StockLevel.CRITIQUE },
        'AB-': { type: 'AB-', stockLevel: StockLevel.CRITIQUE },
        'O+': { type: 'O+', stockLevel: StockLevel.NORMAL },
        'O-': { type: 'O-', stockLevel: StockLevel.FAIBLE }
      }
    }
    
  };

  getRegionData(regionName: string): BloodStats | undefined {
    return this.regionsData[regionName];
  }

  getAllRegions(): { [key: string]: BloodStats } {
    return this.regionsData;
  }

  getRegionColor(regionName: string): string {
    const regionData = this.getRegionData(regionName);
    if (!regionData) {
      return '#95a5a6'; // Default color
    }
    
    const ratio = regionData.totalDonors / regionData.population * 1000;
    
    if (ratio >= 10) return '#2ECC71'; // Green - Compliant
    if (ratio >= 7) return '#F39C12';  // Orange - Near Compliant
    return '#E74C3C'; // Red - Below Standard
  }
}