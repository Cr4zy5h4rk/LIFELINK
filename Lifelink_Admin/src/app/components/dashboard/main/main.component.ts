// dashboard.component.ts
import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, registerables } from 'chart.js';
import { SenegalMapComponent } from '../../senegal-map-component/senegal-map-component.component';

// Enregistrer tous les composants Chart.js
Chart.register(...registerables);

interface BloodInventory {
  bloodType: string;
  level: number; // pourcentage 0-100
  status: string;
}

interface Event {
  id: number;
  name: string;
  date: Date;
  location: string;
  time: string;
}

interface UrgentNeed {
  bloodType: string;
  hospital: string;
  quantity: number;
  deadline: Date;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, SenegalMapComponent],
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit, AfterViewInit {
  @ViewChild('bloodTypeChart') bloodTypeChart!: ElementRef;
  @ViewChild('genderChart') genderChart!: ElementRef;
  @ViewChild('donationTrendChart') donationTrendChart!: ElementRef;
  @ViewChild('ageGroupChart') ageGroupChart!: ElementRef;

  currentDate: Date = new Date();
  totalDonations: number = 12547;
  newDonors: number = 423;
  donationsThisMonth: number = 843;
  bloodBanksCount: number = 28;

  bloodInventory: BloodInventory[] = [
    { bloodType: 'A+', level: 65, status: 'Normal' },
    { bloodType: 'A-', level: 30, status: 'Bas' },
    { bloodType: 'B+', level: 45, status: 'Normal' },
    { bloodType: 'B-', level: 15, status: 'Critique' },
    { bloodType: 'AB+', level: 80, status: 'Surplus' },
    { bloodType: 'AB-', level: 20, status: 'Bas' },
    { bloodType: 'O+', level: 50, status: 'Normal' },
    { bloodType: 'O-', level: 10, status: 'Critique' }
  ];

  upcomingEvents: Event[] = [
    {
      id: 1,
      name: 'Collecte Université Cheikh Anta Diop',
      date: new Date('2025-04-10'),
      location: 'Campus UCAD, Dakar',
      time: '09:00 - 16:00'
    },
    {
      id: 2,
      name: 'Don de Sang - Place de l\'Indépendance',
      date: new Date('2025-04-15'),
      location: 'Place de l\'Indépendance, Dakar',
      time: '10:00 - 17:00'
    },
    {
      id: 3,
      name: 'Journée du Don Volontaire',
      date: new Date('2025-04-20'),
      location: 'Centre Hospitalier Regional, Thiès',
      time: '08:30 - 15:00'
    },
    {
      id: 4,
      name: 'Campagne Entreprises Responsables',
      date: new Date('2025-04-25'),
      location: 'Zone Industrielle, Dakar',
      time: '09:00 - 14:00'
    }
  ];

  urgentNeeds: UrgentNeed[] = [
    {
      bloodType: 'O-',
      hospital: 'Hôpital Principal de Dakar',
      quantity: 15,
      deadline: new Date('2025-04-05')
    },
    {
      bloodType: 'B-',
      hospital: 'Hôpital de Grand Yoff',
      quantity: 8,
      deadline: new Date('2025-04-07')
    },
    {
      bloodType: 'AB-',
      hospital: 'Clinique du Cap',
      quantity: 5,
      deadline: new Date('2025-04-03')
    }
  ];

  constructor() {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.initBloodTypeChart();
    this.initGenderChart();
    this.initDonationTrendChart();
    this.initAgeGroupChart();
  }

  getInventoryStatusClass(level: number): string {
    if (level <= 15) return 'critical';
    if (level <= 30) return 'low';
    if (level <= 70) return 'normal';
    return 'surplus';
  }

  private initBloodTypeChart(): void {
    const ctx = this.bloodTypeChart.nativeElement.getContext('2d');
    
    new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: ['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'],
        datasets: [{
          data: [35, 5, 20, 3, 7, 2, 25, 3],
          backgroundColor: [
            '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0',
            '#9966FF', '#FF9F40', '#2ECC71', '#E74C3C'
          ],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'right',
          },
          title: {
            display: false
          }
        }
      }
    });
  }

  private initGenderChart(): void {
    const ctx = this.genderChart.nativeElement.getContext('2d');
    
    new Chart(ctx, {
      type: 'pie',
      data: {
        labels: ['Hommes', 'Femmes'],
        datasets: [{
          data: [58, 42],
          backgroundColor: ['#36A2EB', '#FF6384'],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'bottom',
          }
        }
      }
    });
  }

  private initDonationTrendChart(): void {
    const ctx = this.donationTrendChart.nativeElement.getContext('2d');
    
    const months = ['Novembre', 'Décembre', 'Janvier', 'Février', 'Mars', 'Avril'];
    
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: months,
        datasets: [{
          label: 'Nombre de dons',
          data: [620, 748, 805, 675, 712, 843],
          borderColor: '#1e88e5',
          backgroundColor: 'rgba(30, 136, 229, 0.1)',
          borderWidth: 2,
          fill: true,
          tension: 0.3
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            beginAtZero: false,
            min: 500
          }
        },
        plugins: {
          legend: {
            display: false
          }
        }
      }
    });
  }

  private initAgeGroupChart(): void {
    const ctx = this.ageGroupChart.nativeElement.getContext('2d');
    
    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: ['18-24', '25-34', '35-44', '45-54', '55-65'],
        datasets: [{
          label: 'Donneurs par âge',
          data: [25, 38, 22, 10, 5],
          backgroundColor: [
            'rgba(255, 99, 132, 0.7)',
            'rgba(54, 162, 235, 0.7)',
            'rgba(255, 206, 86, 0.7)',
            'rgba(75, 192, 192, 0.7)',
            'rgba(153, 102, 255, 0.7)'
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              callback: function(value) {
                return value + '%';
              }
            }
          }
        },
        plugins: {
          legend: {
            display: false
          }
        }
      }
    });
  }
}