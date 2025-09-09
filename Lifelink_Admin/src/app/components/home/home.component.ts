import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, CommonModule, ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {
  constructor() { }

  ngOnInit(): void {
    // Animation des compteurs au scroll
    this.initCountUp();
  }

  // Initialiser l'animation des compteurs au scroll
  initCountUp(): void {
    const observerOptions = {
      root: null,
      rootMargin: '0px',
      threshold: 0.1
    };

    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          const countUpElements = document.querySelectorAll('.count-up');
          countUpElements.forEach(element => {
            this.animateCountUp(element as HTMLElement);
          });
          observer.unobserve(entry.target);
        }
      });
    }, observerOptions);

    // Observer la section statistiques
    const statsSection = document.querySelector('.stats-section');
    if (statsSection) {
      observer.observe(statsSection);
    }
  }

  // Animation de comptage
  animateCountUp(element: HTMLElement): void {
    const target = parseInt(element.getAttribute('data-count') || '0');
    const duration = 2000; // ms
    const step = Math.ceil(target / (duration / 30)); // 30fps
    
    let current = 0;
    const timer = setInterval(() => {
      current += step;
      if (current >= target) {
        element.textContent = target.toLocaleString();
        clearInterval(timer);
      } else {
        element.textContent = current.toLocaleString();
      }
    }, 30);
  }
}
