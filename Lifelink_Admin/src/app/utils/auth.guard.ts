// auth.guard.ts
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { 
  CanActivateFn, 
  ActivatedRouteSnapshot, 
  RouterStateSnapshot 
} from '@angular/router';

// Guard pour vérifier si l'utilisateur est authentifié
export const isAuthenticatedGuard: CanActivateFn = 
  (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const authService = inject(AuthService);
    const router = inject(Router);
    
    if (authService.isAuthenticated()) {
      return true;
    }
    
    // Rediriger vers la page de connexion avec l'URL de retour
    router.navigate(['/login'], { 
      queryParams: { returnUrl: state.url }
    });
    return false;
  };

// Guard pour vérifier si l'utilisateur a un rôle admin
export const isAdminGuard: CanActivateFn = 
  (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const authService = inject(AuthService);
    const router = inject(Router);
    
    if (authService.isAuthenticated()) {
      return true;
    }
    
    // // Si l'utilisateur est authentifié mais n'a pas les droits d'admin
    // if (authService.isAuthenticated()) {
    //   // Rediriger vers une page d'accès refusé ou la page d'accueil
    //   router.navigate(['/access-denied']);
    //   return false;
    // }
    
    // Si l'utilisateur n'est pas authentifié, rediriger vers la page de connexion
    router.navigate(['/login'], { 
      queryParams: { returnUrl: state.url }
    });
    return false;
  };