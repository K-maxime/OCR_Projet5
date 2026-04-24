import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

/**
 * Guard qui protège les routes nécessitant authentification
 * Redirige vers login si pas de token
 */
@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {}

  /**
   * Vérifie si l'utilisateur est authentifié
   * 
   * @param route Route activée
   * @param state État du routeur
   * @returns true si authentifié, false sinon
   */
  public canActivate(): boolean {
    const token = sessionStorage.getItem('token');

    if (token) {
      return true;
    }

    // Pas de token, redirection login
    this.router.navigate(['/login']);
    return false;
  }
}