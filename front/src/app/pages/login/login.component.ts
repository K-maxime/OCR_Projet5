import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/core/models/loginRequest.interface';
import { AuthService } from 'src/app/core/service/auth.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';


import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  imports: [
    MatFormFieldModule, 
    MatInputModule, 
    MatButtonModule, 
    MatIconModule, 
    MatSelectModule, 
    MatCardModule,
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
        console.log('Connexion réussie', response);
        this.router.navigate(["feed"]);
      },
      error: error => this.onError = true,
    });    
  }

  backToHome(): void {
    this.router.navigate(["home"]);
  }
}
