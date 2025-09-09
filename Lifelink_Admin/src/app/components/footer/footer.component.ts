import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <footer class="footer">
      <div class="footer-wave-container">
        <svg class="footer-wave" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
          <path fill="#14213d" fill-opacity="1" d="M0,288L48,272C96,256,192,224,288,213.3C384,203,480,213,576,229.3C672,245,768,267,864,261.3C960,256,1056,224,1152,208C1248,192,1344,192,1392,192L1440,192L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path>
        </svg>
      </div>
      
      <div class="container">
        <div class="footer-main">
          <div class="footer-section about">
            <div class="footer-logo">
              <img src="../../../assets/img/lfllogo.png" alt="LifeLink Logo">
              <h3>LIFELINK</h3>
            </div>
            <p>Plateforme de don de sang basée sur l'identité numérique en Afrique.</p>
            <div class="social-icons">
              <a href="#" aria-label="Facebook"><i class="fab fa-facebook-f"></i></a>
              <a href="#" aria-label="Twitter"><i class="fab fa-twitter"></i></a>
              <a href="#" aria-label="Instagram"><i class="fab fa-instagram"></i></a>
              <a href="#" aria-label="LinkedIn"><i class="fab fa-linkedin-in"></i></a>
            </div>
          </div>
          
          <div class="footer-nav">
            <div class="footer-section links">
              <h3>Liens rapides</h3>
              <ul>
                <li><a routerLink="/"><i class="fas fa-angle-right"></i> Accueil</a></li>
                <li><a routerLink="/centres"><i class="fas fa-angle-right"></i> Centres de don</a></li>
                <li><a routerLink="/a-propos"><i class="fas fa-angle-right"></i> À propos</a></li>
                <li><a routerLink="/contact"><i class="fas fa-angle-right"></i> Contact</a></li>
              </ul>
            </div>
            
            <div class="footer-section resources">
              <h3>Ressources</h3>
              <ul>
                <li><a routerLink="/faq"><i class="fas fa-angle-right"></i> FAQ</a></li>
                <li><a routerLink="/eligibilite"><i class="fas fa-angle-right"></i> Critères d'éligibilité</a></li>
                <li><a routerLink="/blog"><i class="fas fa-angle-right"></i> Blog</a></li>
                <li><a routerLink="/partenaires"><i class="fas fa-angle-right"></i> Nos partenaires</a></li>
              </ul>
            </div>
            
            <div class="footer-section contact">
              <h3>Contact</h3>
              <ul>
                <li><i class="fas fa-envelope"></i> <a href="mailto:contact@lifelink.org">contact&nbsp;lifelink.org</a></li>
                <li><i class="fas fa-phone"></i> +221 33 123 45 67</li>
                <li><i class="fas fa-map-marker-alt"></i> Dakar, Sénégal</li>
                <li><i class="fas fa-clock"></i> Lun-Ven: 9h-17h</li>
              </ul>
            </div>
          </div>
        </div>
        
        <div class="footer-divider"></div>
        
        <div class="footer-bottom">
          <p>&copy; 2025 LIFELINK. Tous droits réservés.</p>
          <div class="footer-links">
            <a routerLink="/politique-confidentialite">Politique de confidentialité</a>
            <a routerLink="/conditions-utilisation">Conditions d'utilisation</a>
            <a routerLink="/mentions-legales">Mentions légales</a>
          </div>
        </div>
      </div>
      
      <div class="blood-drop-container">
        <div class="blood-drop"></div>
        <div class="blood-drop"></div>
        <div class="blood-drop"></div>
      </div>
      
      <a href="#" class="back-to-top" aria-label="Retour en haut de page">
        <i class="fas fa-chevron-up"></i>
      </a>
    </footer>
  `,
  styles: [`
    @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css');
    
    :host {
      --primary-color: #e53935;
      --primary-dark: #b71c1c;
      --primary-light: #ffebee;
      --dark-color: #14213d;
      --dark-color-lighter: #1a2b4d;
      --text-color: #424242;
      --text-light: #757575;
    }

    .footer {
      position: relative;
      background-color: var(--dark-color);
      color: white;
      padding: 70px 0 30px;
      margin-top: 100px;
      overflow: hidden;
    }
    
    .container {
      max-width: 1200px;
      margin: 0 auto;
      padding: 0 20px;
      position: relative;
      z-index: 2;
    }
    
    /* Vague en haut du footer */
    .footer-wave-container {
      position: absolute;
      top: -100px;
      left: 0;
      width: 100%;
      overflow: hidden;
    }
    
    .footer-wave {
      display: block;
      width: 100%;
      height: auto;
    }
    
    /* Layout du footer */
    .footer-main {
      display: flex;
      flex-wrap: wrap;
      gap: 40px;
      margin-bottom: 50px;
    }
    
    .footer-section {
      margin-bottom: 30px;
    }
    
    .about {
      flex: 1 1 300px;
    }
    
    .footer-nav {
      flex: 2 1 600px;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      gap: 30px;
    }
    
    .links, .resources, .contact {
      flex: 1 1 180px;
    }
    
    /* Logo et section À propos */
    .footer-logo {
      display: flex;
      align-items: center;
      gap: 15px;
      margin-bottom: 20px;
    }
    
    .footer-logo img {
      width: 50px;
      height: auto;
      filter: brightness(0) invert(1);
    }
    
    .footer-logo h3 {
      font-size: 1.8rem;
      margin: 0;
      letter-spacing: 1px;
      background: linear-gradient(to right, #fff, #ffebee);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }
    
    .about p {
      margin-bottom: 25px;
      font-size: 0.95rem;
      line-height: 1.6;
      color: rgba(255, 255, 255, 0.8);
    }
    
    /* Icônes sociales */
    .social-icons {
      display: flex;
      gap: 15px;
    }
    
    .social-icons a {
      width: 36px;
      height: 36px;
      border-radius: 50%;
      background: rgba(255, 255, 255, 0.1);
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      font-size: 0.9rem;
      transition: all 0.3s ease;
    }
    
    .social-icons a:hover {
      background: var(--primary-color);
      transform: translateY(-3px);
    }
    
    /* Titres de sections */
    .footer-section h3 {
      color: white;
      margin-bottom: 20px;
      font-size: 1.2rem;
      font-weight: 600;
      position: relative;
      padding-bottom: 10px;
    }
    
    .footer-section h3::after {
      content: '';
      position: absolute;
      bottom: 0;
      left: 0;
      width: 40px;
      height: 3px;
      background-color: var(--primary-color);
      border-radius: 3px;
    }
    
    /* Listes dans les sections */
    .footer-section ul {
      list-style: none;
      padding: 0;
      margin: 0;
    }
    
    .footer-section ul li {
      margin-bottom: 12px;
      display: flex;
      align-items: flex-start;
      gap: 10px;
    }
    
    .footer-section ul li i {
      color: var(--primary-color);
      margin-top: 5px;
    }
    
    .footer-section a {
      color: rgba(255, 255, 255, 0.8);
      text-decoration: none;
      transition: all 0.3s ease;
      position: relative;
    }
    
    .footer-section a:hover {
      color: white;
      padding-left: 5px;
    }
    
    /* Séparateur */
    .footer-divider {
      height: 1px;
      background: linear-gradient(to right, transparent, rgba(255, 255, 255, 0.1), transparent);
      margin-bottom: 30px;
    }
    
    /* Bas du footer */
    .footer-bottom {
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-wrap: wrap;
      gap: 20px;
      color: rgba(255, 255, 255, 0.7);
      font-size: 0.9rem;
    }
    
    .footer-links {
      display: flex;
      gap: 20px;
      flex-wrap: wrap;
    }
    
    .footer-links a {
      color: rgba(255, 255, 255, 0.7);
      text-decoration: none;
      transition: color 0.3s ease;
    }
    
    .footer-links a:hover {
      color: white;
    }
    
    /* Animation gouttes de sang */
    .blood-drop-container {
      position: absolute;
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
      pointer-events: none;
      z-index: 1;
    }
    
    .blood-drop {
      position: absolute;
      width: 100px;
      height: 140px;
      background-color: rgba(229, 57, 53, 0.1);
      border-radius: 50% 50% 50% 50% / 60% 60% 40% 40%;
      transform: rotate(45deg);
      z-index: 1;
    }
    
    .blood-drop:nth-child(1) {
      top: 40px;
      right: 10%;
      animation: float 15s ease-in-out infinite;
    }
    
    .blood-drop:nth-child(2) {
      top: -30px;
      right: 30%;
      width: 60px;
      height: 90px;
      animation: float 12s ease-in-out infinite;
      animation-delay: -3s;
    }
    
    .blood-drop:nth-child(3) {
      bottom: 70px;
      left: 15%;
      width: 80px;
      height: 120px;
      animation: float 18s ease-in-out infinite;
      animation-delay: -7s;
    }
    
    @keyframes float {
      0%, 100% {
        transform: rotate(45deg) translate(0, 0);
      }
      50% {
        transform: rotate(45deg) translate(15px, 15px);
      }
    }
    
    /* Bouton retour en haut */
    .back-to-top {
      position: absolute;
      bottom: 80px;
      right: 30px;
      width: 40px;
      height: 40px;
      background-color: var(--primary-color);
      color: white;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      text-decoration: none;
      transition: all 0.3s ease;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
      z-index: 3;
    }
    
    .back-to-top:hover {
      background-color: var(--primary-dark);
      transform: translateY(-5px);
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
    }
    
    /* Responsive */
    @media (max-width: 992px) {
      .footer-main {
        flex-direction: column;
        gap: 30px;
      }
      
      .about {
        text-align: center;
      }
      
      .footer-logo {
        justify-content: center;
      }
      
      .social-icons {
        justify-content: center;
      }
      
      .footer-section h3::after {
        left: 50%;
        transform: translateX(-50%);
      }
      
      .footer-nav {
        justify-content: center;
        text-align: center;
      }
      
      .footer-section ul li {
        justify-content: center;
      }
      
      .footer-bottom {
        flex-direction: column;
        text-align: center;
      }
      
      .footer-links {
        justify-content: center;
      }
    }
    
    @media (max-width: 768px) {
      .footer {
        padding-top: 50px;
        margin-top: 70px;
      }
      
      .footer-wave-container {
        top: -50px;
      }
      
      .footer-nav {
        flex-direction: column;
        gap: 40px;
      }
      
      .links, .resources, .contact {
        flex: 1 1 100%;
      }
    }
  `]
})
export class FooterComponent {}