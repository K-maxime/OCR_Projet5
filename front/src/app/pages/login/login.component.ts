import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/core/models/loginRequest.interface';
import { AuthService } from 'src/app/core/service/auth.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserInformation } from 'src/app/core/models/userInformation.interface';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private authService = inject(AuthService); 
  private fb = inject(FormBuilder);
  private router = inject(Router);

  public form = this.fb.group({
    login: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.min(3)
      ]
    ]
  });
  errorMessage: string = "";
  loginForm: FormGroup<any> | undefined;

  public submit(): void {

    console.log('submit');
    
    const loginRequest = this.form.value as LoginRequest;

    this.authService.login(loginRequest).subscribe({
      next: (response) => {
        console.log('Connexion réussie', response);
        // TODO: redirection
      },
      error: (err) => {
        this.errorMessage = 'Email ou mot de passe incorrect';
      }
    });
  }
}
