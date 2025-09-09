import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-callback',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.scss']
})
export class CallbackComponent implements OnInit {
  isLoading = true;
  error: string | null = null;
  loadingProgress = 0;
  logoUrl = 'https://i.imgur.com/q7oel6B.png';
  loadingSteps = ['Vérification...', 'Authentification...', 'Récupération du profil...', 'Redirection...'];
  currentStep = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    // Animation de chargement
    this.startLoadingAnimation();
    
    // Traitement des paramètres de requête
    this.route.queryParams.subscribe(params => {
      const code = params['code'];
      const errorParam = params['error'];

      if (errorParam) {
        this.handleAuthenticationError(errorParam, params['error_description']);
        return;
      }

      if (code) {
        this.exchangeCodeForToken(code);
      } else {
        this.handleAuthenticationError('no_code', 'Aucun code d\'autorisation reçu');
      }
    });
  }

  private startLoadingAnimation(): void {
    const interval = setInterval(() => {
      this.loadingProgress += 1;
      
      if (this.loadingProgress % 25 === 0) {
        this.currentStep = Math.min(this.currentStep + 1, this.loadingSteps.length - 1);
      }
      
      if (this.loadingProgress >= 100) {
        clearInterval(interval);
      }
    }, 30);
  }

  private exchangeCodeForToken(code: string): void {
    // Endpoint pour échanger le code contre un token
    const tokenExchangeUrl = `http://localhost:8080/api/delegate/fetchUserInfo?code=${code}`;

    this.http.get(tokenExchangeUrl).pipe(
      map((response: any) => {
        // Stocker le token et les informations utilisateur
        localStorage.setItem('token', response.token);
        localStorage.setItem('userInfo', JSON.stringify(response.user));
        
        // Attendre que l'animation soit complète avant de rediriger
        setTimeout(() => {
          this.router.navigate(['/dashboard']);
        }, Math.max(0, 3000 - (this.loadingProgress * 30)));
      }),
      catchError((error) => {
        this.handleAuthenticationError('token_exchange_failed', error.message);
        return of(null);
      })
    ).subscribe();
  }

  private handleAuthenticationError(errorCode: string, errorMessage?: string): void {
    this.isLoading = false;
    this.error = `Erreur d'authentification: ${errorMessage || errorCode}`;

    // Rediriger vers la page de connexion avec l'erreur
    setTimeout(() => {
      this.router.navigate(['/login'], { 
        queryParams: { 
          error: errorCode, 
          error_description: errorMessage 
        } 
      });
    }, 3000);
  }
}