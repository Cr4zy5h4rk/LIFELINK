// auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode'; // N'oubliez pas d'installer ce package: npm install jwt-decode

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly TOKEN_KEY = 'token';
  private readonly REFRESH_TOKEN_KEY = 'refresh_token';
  private readonly USER_DATA_KEY = 'user_data';
  
  constructor(private http: HttpClient, private router: Router) {}
  
  // Sauvegarde les tokens dans le localStorage
  saveTokens(tokens: { access_token: string, id_token: string, refresh_token?: string }): void {
    localStorage.setItem(this.TOKEN_KEY, tokens.access_token);
    if (tokens.refresh_token) {
      localStorage.setItem(this.REFRESH_TOKEN_KEY, tokens.refresh_token);
    }
    
    // Décodage et sauvegarde des informations utilisateur depuis l'id_token
    try {
      const decodedToken: any = jwtDecode(tokens.id_token);
      this.saveUserData(decodedToken);
    } catch (error) {
      console.error('Erreur lors du décodage du token', error);
    }
  }
  
  // Sauvegarde les données utilisateur
  saveUserData(userData: any): void {
    localStorage.setItem(this.USER_DATA_KEY, JSON.stringify(userData));
  }
  
  // Récupère le token d'accès
  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }
  
  // Récupère le token de rafraîchissement
  getRefreshToken(): string | null {
    return localStorage.getItem(this.REFRESH_TOKEN_KEY);
  }
  
  // Récupère les données utilisateur
  getUserData(): any {
    const userData = localStorage.getItem('userInfo');
    return userData ? JSON.parse(userData) : null;
  }
  
  // Vérifie si l'utilisateur est connecté
  isAuthenticated(): boolean {
    const token = this.getToken();
    if (!token) return false;
    
    try {
      // Vérifier si le token est expiré
      const decodedToken: any = jwtDecode(token);
      const currentTime = Date.now() / 1000;
      return decodedToken.exp > currentTime;
    } catch (error) {
      return false;
    }
  }
  
  // Vérifie si l'utilisateur a un rôle admin (à adapter selon votre structure)
  // hasAdminRole(): boolean {
  //   const userData = this.getUserData();
  //   if (!userData || !userData.roles) return false;
    
  //   // À adapter selon votre structure de rôles
  //   return userData.roles.includes('ADMIN');
  // }
  
  // Rafraîchit le token
  // refreshToken(): Observable<string> {
  //   const refreshToken = this.getRefreshToken();
  //   if (!refreshToken) {
  //     return throwError(() => new Error('No refresh token available'));
  //   }
    
  //   return this.http.post<any>('/api/delegate/refresh-token', { refreshToken })
  //     .pipe(
  //       tap(tokens => this.saveTokens(tokens)),
  //       map(tokens => tokens.access_token),
  //       catchError(error => {
  //         this.logout();
  //         return throwError(() => error);
  //       })
  //     );
  // }
  
  // Échange le code d'autorisation contre des tokens
  // exchangeCodeForTokens(code: string): Observable<any> {
  //   return this.http.post<any>('/api/delegate/exchange-code', { code })
  //     .pipe(
  //       tap(tokens => this.saveTokens(tokens))
  //     );
  // }
  
  // Déconnexion
  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.REFRESH_TOKEN_KEY);
    localStorage.removeItem(this.USER_DATA_KEY);
    this.router.navigate(['/login']);
  }
}