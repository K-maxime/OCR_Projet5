import { Component, inject } from '@angular/core';
import { MaterialModule } from '../material.module';
import { Router } from '@angular/router';
import { AuthService } from '@app/core/service/auth.service';

@Component({
  selector: 'app-header',
  imports: [MaterialModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  
  private router = inject(Router);
  private authService= inject(AuthService);


  goToUser() {
    this.router.navigate(["/user"])
  }

  goToSubjects() {
    this.router.navigate(["/subjects"]);
  }

  goToFeed() {
    this.router.navigate(["/feed"]);
  }

  logout() {
    this.authService.logout();
  }


}
