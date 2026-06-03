import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/core/models/loginRequest.interface';
import { AuthService } from '@app/core/service/auth.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { MaterialModule } from '../shared/material.module';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  imports: [
    MaterialModule,
    ReactiveFormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {

  private authService = inject(AuthService); 
  private fb = inject(FormBuilder);
  private router = inject(Router);

  public onError = false;

  public form = this.fb.group({
    login: [
      '',
      [
        Validators.required,
        Validators.min(3)
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.min(8),        
      ]
    ]
  });
  errorMessage: string = "";
  loginForm: FormGroup<any> | undefined;

  
  hide = signal(true);
  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }

  public login(): void {    
    const loginRequest = this.form.value as LoginRequest;

    this.authService.login(loginRequest).subscribe({
      next: (response) => {
        this.router.navigate(["feed"]);
      },
      error: error => this.onError = true,
    });    
  }

  backToHome(): void {
    this.router.navigate(["home"]);
  }
}
