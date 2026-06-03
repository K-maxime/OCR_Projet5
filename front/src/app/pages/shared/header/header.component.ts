import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { MaterialModule } from '../material.module';
import { Router, NavigationEnd } from '@angular/router';
import { AuthService } from '@app/core/service/auth.service';
import { Subject } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-header',
  imports: [MaterialModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit, OnDestroy {
  private router = inject(Router);
  private authService = inject(AuthService);
  private destroy$ = new Subject<void>();

  // Propriétés publiques pour le template
  isMobileView = false;
  isAuthRoute = false;
  isHomeRoute = false;
  isMenuOpen = false;

  // Routes où le header doit être minimaliste (logo seulement)
  private authRoutes = ['login', 'register'];
  private homeRoute = ['home'];

  ngOnInit(): void {
    // Détection du changement de route
    this.router.events
      .pipe(
        filter((event) => event instanceof NavigationEnd),
        takeUntil(this.destroy$)
      )
      .subscribe((event: any) => {
        this.updateAuthRoute(event.urlAfterRedirects);
        // Fermer le menu lors de la navigation
        this.isMenuOpen = false;
      });

    // Détection de la taille de l'écran
    this.updateMobileView();
    window.addEventListener('resize', () => this.updateMobileView());

    // Vérifier la route initiale
    this.updateAuthRoute(this.router.url);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  /**
   * Bascule l'ouverture/fermeture du menu mobile
   */
  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  /**
   * Met à jour le statut mobile en fonction de la taille de l'écran
   */
  private updateMobileView(): void {
    this.isMobileView = window.innerWidth <= 1024;
    // Fermer le menu si on passe en desktop
    if (!this.isMobileView) {
      this.isMenuOpen = false;
    }
  }

  /**
   * Vérifie si la route actuelle est une route d'authentification
   */
  private updateAuthRoute(url: string): void {
    // Extraire le segment de route principal (ex: /login => login)
    const routeSegment = url.split('/')[1];
    this.isAuthRoute = this.authRoutes.includes(routeSegment);
    this.isHomeRoute = this.homeRoute.includes(routeSegment);
  }

  /**
   * Navigue vers une page et ferme le menu
   */
  navigate(action: string): void {
    this.isMenuOpen = false;

    switch (action) {
      case 'logout':
        this.logout();
        break;
      case 'feed':
        this.goToFeed();
        break;
      case 'subjects':
        this.goToSubjects();
        break;
      case 'user':
        this.goToUser();
        break;
    }
  }

  /**
   * Navigue vers la page utilisateur
   */
  goToUser(): void {
    this.router.navigate(['/user']);
  }

  /**
   * Navigue vers la page des thèmes
   */
  goToSubjects(): void {
    this.router.navigate(['/subjects']);
  }

  /**
   * Navigue vers la page du feed (articles)
   */
  goToFeed(): void {
    this.router.navigate(['/feed']);
  }

  /**
   * Déconnecte l'utilisateur et le redirige
   */
  logout(): void {
    this.authService.logout();
    // La redirection devrait être gérée par le service auth
    this.router.navigate(['/home']);
  }
}