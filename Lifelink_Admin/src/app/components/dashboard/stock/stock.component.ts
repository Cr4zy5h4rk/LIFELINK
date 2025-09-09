import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

interface BloodStock {
  id: number;
  region: string;
  bloodBank: string;
  address: string;
  bloodType: string;
  quantity: number;
  unit: string;
  expiryDate: Date;
  status: 'Critique' | 'Bas' | 'Normal' | 'Élevé';
  lastUpdated: Date;
}

interface Region {
  name: string;
  bloodBanks: {
    name: string;
    address: string;
  }[];
}

@Component({
  selector: 'app-stock',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './stock.component.html',
  styleUrl: './stock.component.scss'
})
export class StockComponent {

  bloodStocks: BloodStock[] = [];
  filteredStocks: BloodStock[] = [];
  regions: Region[] = [];
  
  // Filtres
  selectedRegion: string = 'Toutes';
  selectedBloodBank: string = 'Toutes';
  selectedBloodType: string = 'Tous';
  
  // Interface d'édition
  isEditing: boolean = false;
  currentStock: BloodStock | null = null;
  
  bloodTypes: string[] = ['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'];
  
  constructor() { }

  ngOnInit(): void {
    this.initializeRegions();
    this.generateMockData();
    this.applyFilters();
  }

  initializeRegions(): void {
    this.regions = [
      { 
        name: 'Dakar', 
        bloodBanks: [
          { name: 'Centre National de Transfusion Sanguine', address: 'Avenue Cheikh Anta Diop, Dakar' },
          { name: 'Hôpital Principal de Dakar', address: '1, Avenue Nelson Mandela, Dakar' },
          { name: 'Hôpital Aristide Le Dantec', address: '30, Avenue Pasteur, Dakar' }
        ]
      },
      { 
        name: 'Thiès', 
        bloodBanks: [
          { name: 'Centre Régional de Transfusion Sanguine de Thiès', address: 'Quartier Randoulène, Thiès' },
          { name: 'Hôpital Régional de Thiès', address: 'Route de Dakar, Thiès' }
        ]
      },
      { 
        name: 'Saint-Louis', 
        bloodBanks: [
          { name: 'Centre Hospitalier Régional de Saint-Louis', address: 'Avenue Général De Gaulle, Saint-Louis' },
          { name: 'Banque de Sang Universitaire', address: 'Université Gaston Berger, Saint-Louis' }
        ]
      },
      { 
        name: 'Ziguinchor', 
        bloodBanks: [
          { name: 'Hôpital Régional de Ziguinchor', address: 'Avenue du Général de Gaulle, Ziguinchor' }
        ]
      },
      { 
        name: 'Kaolack', 
        bloodBanks: [
          { name: 'Centre de Transfusion Sanguine de Kaolack', address: 'Quartier Boustane, Kaolack' }
        ]
      },
      { 
        name: 'Tambacounda', 
        bloodBanks: [
          { name: 'Hôpital Régional de Tambacounda', address: 'Route de Dialacoto, Tambacounda' }
        ]
      },
      { 
        name: 'Diourbel', 
        bloodBanks: [
          { name: 'Centre Hospitalier Régional de Diourbel', address: 'Quartier Keur Goumack, Diourbel' }
        ]
      },
      { 
        name: 'Louga', 
        bloodBanks: [
          { name: 'Hôpital Amadou Sakhir Mbaye', address: 'Quartier Montagne Sud, Louga' }
        ]
      },
      { 
        name: 'Fatick', 
        bloodBanks: [
          { name: 'Centre Hospitalier Régional de Fatick', address: 'Route de Kaolack, Fatick' }
        ]
      },
      { 
        name: 'Kolda', 
        bloodBanks: [
          { name: 'Hôpital Régional de Kolda', address: 'Quartier Sikilo Sud, Kolda' }
        ]
      },
      { 
        name: 'Matam', 
        bloodBanks: [
          { name: 'Centre Hospitalier Régional de Matam', address: 'Quartier Tantadji, Matam' }
        ]
      },
      { 
        name: 'Kaffrine', 
        bloodBanks: [
          { name: 'Hôpital Régional de Kaffrine', address: 'Quartier Diamaguène, Kaffrine' }
        ]
      },
      { 
        name: 'Kédougou', 
        bloodBanks: [
          { name: 'Centre Hospitalier Régional de Kédougou', address: 'Quartier Lawol Tamba, Kédougou' }
        ]
      },
      { 
        name: 'Sédhiou', 
        bloodBanks: [
          { name: 'Hôpital Régional de Sédhiou', address: 'Quartier Doumassou, Sédhiou' }
        ]
      }
    ];
  }

  generateMockData(): void {
    const now = new Date();
    let id = 1;
    
    this.regions.forEach(region => {
      region.bloodBanks.forEach(bloodBank => {
        this.bloodTypes.forEach(bloodType => {
          // Générer une quantité aléatoire pour chaque type de sang
          const quantity = Math.floor(Math.random() * 200) + 10;
          let status: 'Critique' | 'Bas' | 'Normal' | 'Élevé';
          
          if (quantity < 30) {
            status = 'Critique';
          } else if (quantity < 70) {
            status = 'Bas';
          } else if (quantity < 150) {
            status = 'Normal';
          } else {
            status = 'Élevé';
          }
          
          // Date d'expiration aléatoire (entre 7 et 42 jours dans le futur)
          const expiryDate = new Date(now);
          expiryDate.setDate(now.getDate() + Math.floor(Math.random() * 35) + 7);
          
          // Date de mise à jour aléatoire (dans les 7 derniers jours)
          const lastUpdated = new Date(now);
          lastUpdated.setDate(now.getDate() - Math.floor(Math.random() * 7));
          
          this.bloodStocks.push({
            id: id++,
            region: region.name,
            bloodBank: bloodBank.name,
            address: bloodBank.address,
            bloodType,
            quantity,
            unit: 'poches',
            expiryDate,
            status,
            lastUpdated
          });
        });
      });
    });
  }

  applyFilters(): void {
    this.filteredStocks = this.bloodStocks.filter(stock => {
      return (this.selectedRegion === 'Toutes' || stock.region === this.selectedRegion) &&
             (this.selectedBloodBank === 'Toutes' || stock.bloodBank === this.selectedBloodBank) &&
             (this.selectedBloodType === 'Tous' || stock.bloodType === this.selectedBloodType);
    });
  }

  onRegionChange(region: string): void {
    this.selectedRegion = region;
    this.selectedBloodBank = 'Toutes';
    this.applyFilters();
  }

  openBankDetails: { [key: string]: boolean } = {};

// Obtenir les régions filtrées selon les critères actuels
getFilteredRegions(): Region[] {
  if (this.selectedRegion !== 'Toutes') {
    return this.regions.filter(region => region.name === this.selectedRegion);
  }
  
  // Filtre pour ne montrer que les régions qui contiennent des résultats
  return this.regions.filter(region => {
    const hasMatchingBanks = this.getFilteredBanksForRegion(region.name).length > 0;
    return hasMatchingBanks;
  });
}

// Obtenir les banques de sang filtrées pour une région donnée
getFilteredBanksForRegion(regionName: string): {name: string, address: string}[] {
  const region = this.regions.find(r => r.name === regionName);
  if (!region) return [];
  
  if (this.selectedBloodBank !== 'Toutes') {
    return region.bloodBanks.filter(bank => bank.name === this.selectedBloodBank);
  }

  // Filtre pour ne montrer que les banques qui contiennent des résultats
  return region.bloodBanks.filter(bank => {
    const hasMatchingStocks = this.filteredStocks.some(stock => 
      stock.region === regionName && 
      stock.bloodBank === bank.name &&
      (this.selectedBloodType === 'Tous' || stock.bloodType === this.selectedBloodType)
    );
    return hasMatchingStocks;
  });
}

// Obtenir les stocks pour une banque de sang spécifique
getStocksForBank(regionName: string, bankName: string): BloodStock[] {
  return this.filteredStocks.filter(stock => 
    stock.region === regionName && 
    stock.bloodBank === bankName &&
    (this.selectedBloodType === 'Tous' || stock.bloodType === this.selectedBloodType)
  );
}

// Obtenir le résumé des statuts de stock pour une banque
getStockStatusForBank(regionName: string, bankName: string): { bloodType: string, quantity: number, status: string }[] {
  const bankStocks = this.getStocksForBank(regionName, bankName);
  const summary: { bloodType: string, quantity: number, status: string }[] = [];
  
  // Si on a filtré par type de sang, ne montrer que celui-là
  if (this.selectedBloodType !== 'Tous') {
    const stocks = bankStocks.filter(stock => stock.bloodType === this.selectedBloodType);
    if (stocks.length > 0) {
      // Calculer la quantité totale et déterminer le statut global
      const totalQuantity = stocks.reduce((sum, stock) => sum + stock.quantity, 0);
      let status = 'Normal';
      
      if (totalQuantity < 30) {
        status = 'Critique';
      } else if (totalQuantity < 70) {
        status = 'Bas';
      } else if (totalQuantity > 150) {
        status = 'Élevé';
      }
      
      summary.push({
        bloodType: this.selectedBloodType,
        quantity: totalQuantity,
        status: status
      });
    }
  } else {
    // Grouper par type de sang
    this.bloodTypes.forEach(bloodType => {
      const stocks = bankStocks.filter(stock => stock.bloodType === bloodType);
      if (stocks.length > 0) {
        // Calculer la quantité totale et déterminer le statut global
        const totalQuantity = stocks.reduce((sum, stock) => sum + stock.quantity, 0);
        let status = 'Normal';
        
        if (totalQuantity < 30) {
          status = 'Critique';
        } else if (totalQuantity < 70) {
          status = 'Bas';
        } else if (totalQuantity > 150) {
          status = 'Élevé';
        }
        
        summary.push({
          bloodType: bloodType,
          quantity: totalQuantity,
          status: status
        });
      }
    });
  }
  
  return summary;
}

// Basculer l'affichage des détails pour une banque
toggleBankDetails(regionName: string, bankName: string): void {
  const key = `${regionName}-${bankName}`;
  this.openBankDetails[key] = !this.openBankDetails[key];
}

// Vérifier si les détails d'une banque sont ouverts
isBankDetailsOpen(regionName: string, bankName: string): boolean {
  const key = `${regionName}-${bankName}`;
  return this.openBankDetails[key] === true;
}
  onBloodBankChange(bloodBank: string): void {
    this.selectedBloodBank = bloodBank;
    this.applyFilters();
  }

  onBloodTypeChange(bloodType: string): void {
    this.selectedBloodType = bloodType;
    this.applyFilters();
  }

  getAvailableBloodBanks(): {name: string, address: string}[] {
    if (this.selectedRegion === 'Toutes') {
      return this.regions.flatMap(r => r.bloodBanks);
    } else {
      const region = this.regions.find(r => r.name === this.selectedRegion);
      return region ? region.bloodBanks : [];
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'Critique': return 'status-critical';
      case 'Bas': return 'status-low';
      case 'Normal': return 'status-normal';
      case 'Élevé': return 'status-high';
      default: return '';
    }
  }

  editStock(stock: BloodStock): void {
    this.isEditing = true;
    this.currentStock = {...stock};
  }

  saveChanges(): void {
    if (this.currentStock) {
      // Mise à jour du stock
      const index = this.bloodStocks.findIndex(s => s.id === this.currentStock!.id);
      if (index !== -1) {
        // Mise à jour du statut en fonction de la quantité
        let status: 'Critique' | 'Bas' | 'Normal' | 'Élevé';
        const quantity = this.currentStock.quantity;
        
        if (quantity < 30) {
          status = 'Critique';
        } else if (quantity < 70) {
          status = 'Bas';
        } else if (quantity < 150) {
          status = 'Normal';
        } else {
          status = 'Élevé';
        }
        
        this.currentStock.status = status;
        this.currentStock.lastUpdated = new Date();
        
        this.bloodStocks[index] = {...this.currentStock};
        this.applyFilters();
      }
    }
    
    this.cancelEdit();
  }

  cancelEdit(): void {
    this.isEditing = false;
    this.currentStock = null;
  }

  formatDate(date: Date): string {
    return date.toLocaleDateString('fr-FR');
  }

  getTotalByType(): { type: string, count: number }[] {
    const totals = this.bloodTypes.map(type => {
      const count = this.filteredStocks
        .filter(stock => stock.bloodType === type)
        .reduce((sum, stock) => sum + stock.quantity, 0);
      
      return { type, count };
    });
    
    return totals.sort((a, b) => b.count - a.count);
  }

  exportData(): void {
    // Simuler un export de données (dans une application réelle, cela générerait un fichier)
    alert('Export des données en cours...');
  }

  addNewStock(): void {
    this.isEditing = true;
    // Créer un nouveau stock vide
    this.currentStock = {
      id: Math.max(...this.bloodStocks.map(s => s.id)) + 1,
      region: this.selectedRegion !== 'Toutes' ? this.selectedRegion : '',
      bloodBank: this.selectedBloodBank !== 'Toutes' ? this.selectedBloodBank : '',
      address: '',
      bloodType: '',
      quantity: 0,
      unit: 'poches',
      expiryDate: new Date(),
      status: 'Bas',
      lastUpdated: new Date()
    };
  }

  saveNewStock(): void {
    if (this.currentStock) {
      // Trouver l'adresse correspondant à la banque de sang
      const region = this.regions.find(r => r.name === this.currentStock!.region);
      if (region) {
        const bloodBank = region.bloodBanks.find(bb => bb.name === this.currentStock!.bloodBank);
        if (bloodBank) {
          this.currentStock.address = bloodBank.address;
        }
      }
      
      // Déterminer le statut en fonction de la quantité
      const quantity = this.currentStock.quantity;
      let status: 'Critique' | 'Bas' | 'Normal' | 'Élevé';
      
      if (quantity < 30) {
        status = 'Critique';
      } else if (quantity < 70) {
        status = 'Bas';
      } else if (quantity < 150) {
        status = 'Normal';
      } else {
        status = 'Élevé';
      }
      
      this.currentStock.status = status;
      
      // Ajouter le nouveau stock
      this.bloodStocks.push({...this.currentStock});
      this.applyFilters();
    }
    
    this.cancelEdit();
  }

  deleteStock(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce stock?')) {
      const index = this.bloodStocks.findIndex(s => s.id === id);
      if (index !== -1) {
        this.bloodStocks.splice(index, 1);
        this.applyFilters();
      }
    }
  }

  setAllAlerts(): void {
    const alertsSet: {[key: string]: boolean} = {};
    
    // Parcourir tous les stocks critiques
    this.bloodStocks
      .filter(stock => stock.status === 'Critique')
      .forEach(stock => {
        const key = `${stock.region}-${stock.bloodType}`;
        if (!alertsSet[key]) {
          // Simuler l'envoi d'une alerte (dans une application réelle, cela déclencherait des notifications)
          console.log(`Alerte envoyée pour le type ${stock.bloodType} dans la région ${stock.region}`);
          alertsSet[key] = true;
        }
      });
    
    alert(`Alertes envoyées pour ${Object.keys(alertsSet).length} situations critiques.`);
  }
}
