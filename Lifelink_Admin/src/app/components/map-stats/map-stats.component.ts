import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-map-stats',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="map-instructions">
      <div class="instruction-content">
        <h2>Explore Senegal's Blood Donation Landscape</h2>
        
        <div class="feature-grid">
          <div class="feature-card">
            <div class="feature-icon">
              <i class="fas fa-map-marker-alt"></i>
            </div>
            <div class="feature-text">
              <h3>Interactive Regions</h3>
              <p>Click on any region to view detailed blood donation statistics</p>
            </div>
          </div>

          <div class="feature-card">
            <div class="feature-icon">
              <i class="fas fa-users"></i>
            </div>
            <div class="feature-text">
              <h3>Donor Insights</h3>
              <p>Discover the number of registered donors in each region</p>
            </div>
          </div>

          <div class="feature-card">
            <div class="feature-icon">
              <i class="fas fa-chart-pie"></i>
            </div>
            <div class="feature-text">
              <h3>Blood Group Levels</h3>
              <p>Check stock levels for different blood groups</p>
            </div>
          </div>

          <div class="feature-card">
            <div class="feature-icon">
              <i class="fas fa-globe"></i>
            </div>
            <div class="feature-text">
              <h3>WHO Compliance</h3>
              <p>Compare regional donor rates with WHO standards</p>
            </div>
          </div>
        </div>

        <div class="legend">
          <h3>Region Color Legend</h3>
          <div class="legend-colors">
            <div class="legend-item">
              <span class="color-box good"></span>
              <span>Compliant (10+ donors per 1000)</span>
            </div>
            <div class="legend-item">
              <span class="color-box warning"></span>
              <span>Near Compliant (7-10 donors per 1000)</span>
            </div>
            <div class="legend-item">
              <span class="color-box bad"></span>
              <span>Below Standard (Less than 7 donors per 1000)</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .map-instructions {
      background-color: #f9f9fc;
      border-radius: 1rem;
      padding: 2rem;
      display: flex;
      justify-content: center;
      align-items: center;
      box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
      height: 100%;
    }

    .instruction-content {
      max-width: 800px;
      text-align: center;
    }

    .instruction-content h2 {
      color: #2c3e50;
      margin-bottom: 2rem;
      font-size: 2rem;
    }

    .feature-grid {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      gap: 1.5rem;
      margin-bottom: 2rem;
    }

    .feature-card {
      background-color: white;
      border-radius: 0.75rem;
      padding: 1.5rem;
      display: flex;
      align-items: center;
      gap: 1.5rem;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
      transition: transform 0.3s ease;
    }

    .feature-card:hover {
      transform: translateY(-5px);
    }

    .feature-icon {
      background-color: #f0f4f8;
      border-radius: 50%;
      width: 4rem;
      height: 4rem;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 1.8rem;
      color: #3498db;
    }

    .feature-text h3 {
      margin: 0 0 0.5rem 0;
      color: #2c3e50;
      font-size: 1.1rem;
    }

    .feature-text p {
      margin: 0;
      color: #6c757d;
      font-size: 0.9rem;
    }

    .legend {
      background-color: white;
      border-radius: 0.75rem;
      padding: 1.5rem;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
    }

    .legend h3 {
      margin-bottom: 1rem;
      color: #2c3e50;
      text-align: center;
    }

    .legend-colors {
      display: flex;
      justify-content: center;
      gap: 1.5rem;
    }

    .legend-item {
      display: flex;
      align-items: center;
      gap: 0.5rem;
    }

    .color-box {
      width: 20px;
      height: 20px;
      border-radius: 4px;
    }

    .color-box.good {
      background-color: #2ECC71;
    }

    .color-box.warning {
      background-color: #F39C12;
    }

    .color-box.bad {
      background-color: #E74C3C;
    }

    @media (max-width: 768px) {
      .feature-grid {
        grid-template-columns: 1fr;
      }
    }
  `]
})
export class MapStatsComponent {}