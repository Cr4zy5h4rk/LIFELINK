import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AdminSidebarComponent } from '../admin-sidebar/admin-sidebar.component';
import { ReversedChartComponent } from '../charts/reversed-chart/reversed-chart.component';
import { SenegalMapComponent } from '../senegal-map-component/senegal-map-component.component';
import { LineChartComponent } from '../charts/line-chart/line-chart.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [AdminSidebarComponent, RouterModule, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
  sidebarExpanded: boolean = true; // Par défaut, la sidebar est étendue
  
  constructor() {}
  
  ngOnInit(): void {
    // Vérifie la taille initiale de l'écran pour définir l'état de la sidebar
    const screenWidth = window.innerWidth;
    if (screenWidth <= 992) {
      this.sidebarExpanded = false;
    }
  }
  
  onSidebarToggle(expanded: boolean): void {
    this.sidebarExpanded = expanded;
  }
}
