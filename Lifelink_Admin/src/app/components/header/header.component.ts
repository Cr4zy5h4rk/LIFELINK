import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <header class="header">
      <div class="container">
        <div class="logo">
          <a routerLink="/">
            <img src="../../../assets/img/lfllogo.png" alt="LifeLink Logo" class="logo-img">
            <span class="logo-text">LifeLink</span>
          </a>
        </div>
        
        <!-- Mobile menu toggle -->
        <div class="menu-toggle" (click)="toggleMobileMenu()">
          <span></span>
          <span></span>
          <span></span>
        </div>
        
        <nav class="nav" [class.active]="mobileMenuOpen">
          <ul class="nav-list">
            <li><a routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">
              <i class="fas fa-home"></i> Accueil
            </a></li>
            <li><a routerLink="/centres" routerLinkActive="active">
              <i class="fas fa-hospital"></i> Centres
            </a></li>
            <li>
              <a routerLink="/urgences" routerLinkActive="active" class="emergency-link">
                <i class="fas fa-heartbeat"></i> Urgences
                <span class="urgent-badge pulse">Urgent</span>
              </a>
            </li>
            <li><a routerLink="/contact" routerLinkActive="active">
              <i class="fas fa-envelope"></i> Contact
            </a></li>
            <ng-container>            
              <li><a [routerLink]="['dashboard/main']" routerLinkActive="active" class="dashboard-link">
                <i class="fas fa-chart-line"></i> Tableau de bord
              </a></li>
            </ng-container>
            <li class="donate-btn">
              <a routerLink="/don" class="btn-donate">
                <i class="fas fa-tint"></i> Faire un don
              </a>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  `,
  styles: [`
    @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css');
    
    :host {
      --primary-color: #e53935;
      --primary-light: #ffebee;
      --primary-dark: #b71c1c;
      --accent-color: #2196f3;
      --text-color: #424242;
      --text-light: #757575;
      --background-color: #ffffff;
      --shadow-color: rgba(0, 0, 0, 0.1);
    }

    .header {
      background-color: var(--background-color);
      box-shadow: 0 2px 15px var(--shadow-color);
      padding: 12px 0;
      position: sticky;
      top: 0;
      z-index: 100;
      transition: all 0.3s ease;
    }

    .header:hover {
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
    }

    .container {
      display: flex;
      justify-content: space-between;
      align-items: center;
      max-width: 1200px;
      margin: 0 auto;
      padding: 0 20px;
      position: relative;
    }

    .emergency-link {
  position: relative;
}

.urgent-badge {
  position: absolute;
  top: -8px;
  right: -8px;
  background-color: #e53935;
  color: white;
  padding: 3px 6px;
  border-radius: 10px;
  font-size: 0.7rem;
  font-weight: bold;
}

.pulse {
  animation: pulse-animation 2s infinite;
}

@keyframes pulse-animation {
  0% {
    transform: scale(0.95);
    box-shadow: 0 0 0 0 rgba(229, 57, 53, 0.7);
  }
  
  70% {
    transform: scale(1);
    box-shadow: 0 0 0 10px rgba(229, 57, 53, 0);
  }
  
  100% {
    transform: scale(0.95);
    box-shadow: 0 0 0 0 rgba(229, 57, 53, 0);
  }
}

    .logo {
      display: flex;
      align-items: center;
    }

    .logo a {
      display: flex;
      align-items: center;
      text-decoration: none;
      color: var(--primary-color);
      transition: transform 0.3s ease;
    }

    .logo a:hover {
      transform: scale(1.05);
    }

    .logo-img {
      width: 40px;
      height: auto;
      margin-right: 10px;
      filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.2));
    }

    .logo-text {
      font-size: 24px;
      font-weight: 700;
      background: linear-gradient(to right, var(--primary-color), var(--primary-dark));
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      letter-spacing: 0.5px;
    }

    .nav-list {
      display: flex;
      list-style: none;
      gap: 25px;
      margin: 0;
      padding: 0;
      align-items: center;
    }

    .nav-list a {
      text-decoration: none;
      color: var(--text-color);
      font-weight: 500;
      padding: 8px 5px;
      transition: all 0.3s ease;
      position: relative;
      display: flex;
      align-items: center;
      gap: 6px;
    }

    .nav-list a::after {
      content: '';
      position: absolute;
      bottom: 0;
      left: 0;
      width: 0;
      height: 2px;
      background-color: var(--primary-color);
      transition: width 0.3s ease;
    }

    .nav-list a:hover::after, 
    .nav-list a.active::after {
      width: 100%;
    }

    .nav-list a:hover, 
    .nav-list a.active {
      color: var(--primary-color);
    }

    .nav-list i {
      font-size: 16px;
    }

    .dashboard-link {
      color: var(--accent-color) !important;
    }

    .dashboard-link:hover, 
    .dashboard-link.active {
      color: var(--accent-color) !important;
    }

    .dashboard-link::after {
      background-color: var(--accent-color);
    }

    .btn-donate {
      background-color: var(--primary-color);
      color: white !important;
      padding: 10px 20px !important;
      border-radius: 50px;
      transition: all 0.3s ease;
      box-shadow: 0 2px 10px rgba(229, 57, 53, 0.3);
    }

    .btn-donate:hover {
      background-color: var(--primary-dark);
      transform: translateY(-2px);
      box-shadow: 0 4px 15px rgba(229, 57, 53, 0.4);
    }

    .btn-donate::after {
      display: none;
    }

    .menu-toggle {
      display: none;
      flex-direction: column;
      gap: 6px;
      cursor: pointer;
      z-index: 200;
    }

    .menu-toggle span {
      display: block;
      width: 30px;
      height: 3px;
      background-color: var(--primary-color);
      border-radius: 3px;
      transition: all 0.3s ease;
    }

    @media (max-width: 768px) {
      .menu-toggle {
        display: flex;
      }

      .nav {
        position: fixed;
        top: 0;
        right: -100%;
        width: 70%;
        height: 100vh;
        background-color: white;
        box-shadow: -5px 0 15px rgba(0, 0, 0, 0.1);
        transition: right 0.3s ease;
        padding: 80px 20px 20px;
        z-index: 100;
      }

      .nav.active {
        right: 0;
      }

      .nav-list {
        flex-direction: column;
        align-items: flex-start;
        gap: 20px;
      }

      .donate-btn {
        margin-top: 20px;
        width: 100%;
      }

      .btn-donate {
        display: block;
        text-align: center;
        width: 100%;
      }

      .menu-toggle.active span:nth-child(1) {
        transform: translateY(9px) rotate(45deg);
      }

      .menu-toggle.active span:nth-child(2) {
        opacity: 0;
      }

      .menu-toggle.active span:nth-child(3) {
        transform: translateY(-9px) rotate(-45deg);
      }
    }
  `]
})
export class HeaderComponent {
  mobileMenuOpen = false;

  constructor(private authService: AuthService) {}

  toggleMobileMenu(): void {
    this.mobileMenuOpen = !this.mobileMenuOpen;
    const toggle = document.querySelector('.menu-toggle');
    if (toggle) {
      toggle.classList.toggle('active');
    }
  }

  logout(): void {
    this.authService.logout();
  }
}