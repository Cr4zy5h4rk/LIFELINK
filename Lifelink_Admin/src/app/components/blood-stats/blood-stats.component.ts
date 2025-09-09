import { Component, Input, Output, EventEmitter, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, ChartConfiguration } from 'chart.js/auto';
import { BloodGroupStockLevel, BloodStats, StockLevel } from '../senegal-map-component/senegal-map-component.component';


@Component({
  selector: 'app-blood-stats',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="blood-stats-container">
      <!-- En-tête des statistiques -->
      <div class="stats-header">
        <h2>Statistiques de don de sang - {{ region?.region }}</h2>
        <button  class="bouton-fermer" aria-label="Fermer les statistiques">
          <i class="fas fa-times"></i>
        </button>
      </div>

      <!-- Carte de synthèse des donneurs -->
      <div class="cartes-principales">
        <div class="carte-donneurs">
          <div class="icone-carte">
            <i class="fas fa-users"></i>
          </div>
          <div class="contenu-carte">
            <h3>Donneurs enregistrés</h3>
            <p class="valeur-carte">{{ region?.totalDonors?.toLocaleString() }}</p>
          </div>
        </div>

        <!-- Carte du statut OMS -->
        <div class="carte-statut-oms" [ngClass]="classeStatutOMS">
          <div class="icone-carte">
            <i class="fas fa-chart-line"></i>
          </div>
          <div class="contenu-carte">
            <h3>Statut OMS</h3>
            <p class="valeur-carte">{{ tauxDonneurs.toFixed(2) }} ‰</p>
            <p class="statut-texte">{{ texteStatutOMS }}</p>
          </div>
        </div>
      </div>

      <!-- Groupes sanguins -->
      <div class="groupes-sanguins">
        <h3>Niveaux de stock par groupe sanguin</h3>
        <div class="grille-groupes-sanguins">
          @for (groupe of groupesSanguinsTableau; track groupe.type) {
            <div 
              class="carte-groupe-sanguin" 
              [ngClass]="obtenirClasseNiveauStock(groupe.stockLevel)"
            >
              <div class="type-groupe-sanguin">{{ groupe.type }}</div>
              <div class="niveau-stock">{{ groupe.stockLevel }}</div>
            </div>
          }
        </div>
      </div>

      <!-- Graphique de comparaison -->
      <div class="conteneur-graphique">
        <h3>Comparaison avec les normes de l'OMS</h3>
        <canvas #graphiqueOMS></canvas>
      </div>
    </div>
  `,
  styles: [`
    .blood-stats-container {
      background-color: #f9f9fc;
      border-radius: 1rem;
      padding: 1.5rem;
      box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
      display: flex;
      flex-direction: column;
      gap: 1.5rem;
      max-height: 80vh;
      overflow-y: auto;
    }

    .stats-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      border-bottom: 2px solid #e1e4e8;
      padding-bottom: 1rem;
    }

    .stats-header h2 {
      margin: 0;
      color: #2c3e50;
      font-size: 1.5rem;
    }

    .bouton-fermer {
      background: none;
      border: none;
      color: #6c757d;
      font-size: 1.5rem;
      cursor: pointer;
      transition: color 0.3s ease;
    }

    .bouton-fermer:hover {
      color: #dc3545;
    }

    .cartes-principales {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 1rem;
    }

    .carte-donneurs, .carte-statut-oms {
      background-color: white;
      border-radius: 0.75rem;
      padding: 1.25rem;
      display: flex;
      align-items: center;
      gap: 1rem;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
      transition: transform 0.3s ease;
    }

    .carte-donneurs:hover, .carte-statut-oms:hover {
      transform: translateY(-5px);
    }

    .icone-carte {
      background-color: #f0f4f8;
      border-radius: 50%;
      width: 3.5rem;
      height: 3.5rem;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 1.5rem;
    }

    .carte-donneurs .icone-carte i {
      color: #3498db;
    }

    .carte-statut-oms .icone-carte i {
      color: #e74c3c;
    }

    .contenu-carte h3 {
      margin: 0 0 0.5rem 0;
      color: #6c757d;
      font-size: 0.9rem;
    }

    .valeur-carte {
      font-size: 1.5rem;
      font-weight: 600;
      color: #2c3e50;
      margin: 0;
    }

    .statut-texte {
      margin: 0.5rem 0 0 0;
      font-size: 0.9rem;
    }

    .groupes-sanguins {
      background-color: white;
      border-radius: 0.75rem;
      padding: 1.25rem;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
    }

    .groupes-sanguins h3 {
      margin-bottom: 1rem;
      color: #34495e;
    }

    .grille-groupes-sanguins {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      gap: 1rem;
    }

    .carte-groupe-sanguin {
      border-radius: 0.5rem;
      padding: 1rem;
      text-align: center;
      transition: transform 0.3s ease;
    }

    .carte-groupe-sanguin:hover {
      transform: scale(1.05);
    }

    .type-groupe-sanguin {
      font-weight: 600;
      margin-bottom: 0.5rem;
      color: white;
    }

    .niveau-stock {
      font-size: 1rem;
      color: white;
    }

    .conteneur-graphique {
      background-color: white;
      border-radius: 0.75rem;
      padding: 1.25rem;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
    }

    /* Classes de couleur pour les niveaux de stock */
    .stock-critique { background-color: #FF6B6B; }
    .stock-faible { background-color: #FFA726; }
    .stock-normal { background-color: #29B6F6; }
    .stock-bon { background-color: #66BB6A; }
    .stock-excellent { background-color: #4CAF50; }

    /* Classes de statut OMS */
    .statut-bon { color: #2ECC71; }
    .statut-avertissement { color: #F39C12; }
    .statut-mauvais { color: #E74C3C; }

    @media (max-width: 768px) {
      .cartes-principales, .grille-groupes-sanguins {
        grid-template-columns: 1fr;
      }
    }
  `]
})
export class BloodStatsComponent implements OnInit, OnDestroy {
  // Propriétés d'entrée et de sortie
  @Input({ required: true }) region!: BloodStats | null;
  @Output() fermerStatistiques = new EventEmitter<void>();

  // Référence au canvas pour le graphique
  @ViewChild('graphiqueOMS', { static: true }) graphiqueOMS!: ElementRef<HTMLCanvasElement>;

  // Propriétés pour les statistiques
  groupesSanguinsTableau: BloodGroupStockLevel[] = [];
  tauxDonneurs: number = 0;
  texteStatutOMS: string = '';
  classeStatutOMS: string = '';

  // Instance du graphique Chart.js
  private graphique: Chart | null = null;

  /**
   * Initialisation du composant
   */
  ngOnInit(): void {
    // Préparer le tableau des groupes sanguins
    this.preparerGroupesSanguins();
    
    // Calculer le statut OMS
    this.calculerStatutOMS();
  }

  /**
   * Nettoyage à la destruction du composant
   */
  ngOnDestroy(): void {
    // Détruire le graphique pour libérer les ressources
    if (this.graphique) {
      this.graphique.destroy();
    }
  }

  /**
   * Préparer le tableau des groupes sanguins
   */
  private preparerGroupesSanguins(): void {
    if(this.region){
      this.groupesSanguinsTableau = Object.values(this.region.bloodGroups);
    }
    // Convertir l'objet des groupes sanguins en tableau
    
  }

  /**
   * Calculer le statut par rapport aux normes de l'OMS
   */
  private calculerStatutOMS(): void {
    if (this.region) {
      this.tauxDonneurs = this.region.totalDonors / this.region.population * 1000;
    }
    // Calculer le taux de donneurs pour 1000 habitants
    
    
    // Déterminer le statut et la classe CSS
    if (this.tauxDonneurs >= 10) {
      this.texteStatutOMS = 'Conforme aux normes de l\'OMS';
      this.classeStatutOMS = 'statut-bon';
    } else if (this.tauxDonneurs >= 7) {
      this.texteStatutOMS = 'Proche des normes de l\'OMS';
      this.classeStatutOMS = 'statut-avertissement';
    } else {
      this.texteStatutOMS = 'En dessous des normes de l\'OMS';
      this.classeStatutOMS = 'statut-mauvais';
    }

    // Créer le graphique après un court délai
    setTimeout(() => this.creerGraphique(), 100);
  }

  /**
   * Obtenir la classe CSS pour un niveau de stock donné
   * @param niveauStock Niveau de stock à styliser
   * @returns Classe CSS correspondante
   */
  obtenirClasseNiveauStock(niveauStock: StockLevel): string {
    switch(niveauStock) {
      case StockLevel.CRITIQUE: return 'stock-critique';
      case StockLevel.FAIBLE: return 'stock-faible';
      case StockLevel.NORMAL: return 'stock-normal';
      case StockLevel.BON: return 'stock-bon';
      case StockLevel.EXCELLENT: return 'stock-excellent';
      default: return '';
    }
  }

  /**
   * Créer le graphique de comparaison avec les normes OMS
   */
  private creerGraphique(): void {
    // Vérifier que le canvas existe
    if (!this.graphiqueOMS) return;
  
    // Définir la taille du canvas si nécessaire
    const canvasElement = this.graphiqueOMS.nativeElement;
    canvasElement.height = 300; // Hauteur en pixels
    canvasElement.style.height = '300px';
    canvasElement.style.maxHeight = '300px';
    canvasElement.width = 400; // Largeur en pixels
    canvasElement.style.width = '100%';
    canvasElement.style.maxWidth = '400px';
  
    // Configuration du graphique
    const configuration: ChartConfiguration = {
      type: 'bar',
      data: {
        labels: ['Région actuelle', 'Norme OMS'],
        datasets: [{
          label: 'Donneurs pour 1000 habitants',
          data: [this.tauxDonneurs, 10],
          backgroundColor: [
            this.tauxDonneurs >= 10 ? '#2ECC71' : 
            (this.tauxDonneurs >= 7 ? '#F39C12' : '#E74C3C'),
            '#3498DB'
          ],
          borderColor: [
            this.tauxDonneurs >= 10 ? '#27AE60' : 
            (this.tauxDonneurs >= 7 ? '#E67E22' : '#C0392B'),
            '#2980B9'
          ],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false, // Permet de définir une hauteur fixe
        layout: {
          padding: {
            left: 10,
            right: 10,
            top: 10,
            bottom: 10
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Donneurs pour 1000 habitants'
            }
          }
        },
        plugins: {
          legend: {
            display: false
          },
          tooltip: {
            callbacks: {
              label: function(context: any) {
                return `${context.raw} donneurs pour 1000 habitants`;
              }
            }
          }
        }
      }
    };
  
    // Créer le graphique
    this.graphique = new Chart(this.graphiqueOMS.nativeElement, configuration);
  }
}