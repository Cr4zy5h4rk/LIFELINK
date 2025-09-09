export interface BloodStock {
    type: string;  // Type de sang (A+, B-, etc.)
    level: number; // Niveau de stock en pourcentage (0-100)
  }
  
  export interface RegionStock {
    id: number;
    name: string;        // Nom de la région
    bloodStocks: BloodStock[]; // Stocks de sang par type
  }