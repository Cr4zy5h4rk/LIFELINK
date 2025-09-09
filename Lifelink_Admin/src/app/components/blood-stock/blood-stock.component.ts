import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RegionStock } from '../../models/region-stock';
import { BloodStockService } from '../../services/blood-stock/blood-stock.service';

@Component({
  selector: 'app-blood-stock',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './blood-stock.component.html',
  styleUrls: ['./blood-stock.component.scss']
})
export class BloodStockComponent implements OnInit {
  regionStocks: RegionStock[] = [];
  loading: boolean = true;
  error: string | null = null;

  constructor(private bloodStockService: BloodStockService) {}

  ngOnInit(): void {
    this.loadStockData();
  }

  loadStockData(): void {
    this.loading = true;
    this.bloodStockService.getRegionStocks().subscribe({
      next: (data) => {
        this.regionStocks = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Impossible de charger les données de stock. Veuillez réessayer plus tard.';
        this.loading = false;
        console.error('Erreur lors du chargement des stocks:', error);
      }
    });
  }

  getStockLevelClass(level: number): string {
    if (level < 30) {
      return 'critical';
    } else if (level < 60) {
      return 'warning';
    } else {
      return 'good';
    }
  }

  refreshData(): void {
    this.loadStockData();
  }
}