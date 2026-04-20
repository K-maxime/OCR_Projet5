import { Component, inject, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Router } from '@angular/router';
import { RegisterRequest } from 'src/app/core/models/registerRequest.interface';
import { AuthService } from 'src/app/core/service/auth.service';

 
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
  imports: [
    MatFormFieldModule, 
    MatInputModule, 
    MatButtonModule, 
    MatIconModule, 
    MatSelectModule, 
    MatCardModule,
    ReactiveFormsModule],
})
export class RegisterComponent {

  private authService = inject(AuthService); 
  private fb = inject(FormBuilder);
  private router = inject(Router);

  public onError = false;

  public registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', 
        [
          Validators.required, 
          Validators.minLength(8),
          this.passwordStrengthValidator()
        ]]
  });

  private passwordStrengthValidator() {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.value;
      
      if (!password) return null;
      
      const hasDigit = /\d/.test(password);
      const hasLowerCase = /[a-z]/.test(password);
      const hasUpperCase = /[A-Z]/.test(password);
      const hasSpecialChar = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(password);
      
      const isValid = hasDigit && hasLowerCase && hasUpperCase && hasSpecialChar;
      
      return !isValid ? { passwordStrength: true } : null;
    };
  }
  
  

  backToHome() : void {
    this.router.navigate(["home"]);

  }
 
  
  register(): void {
    if (this.registerForm.invalid) {
      return;
    }
 
    const registerRequest = this.registerForm.value as RegisterRequest;
 
    this.authService.register(registerRequest).subscribe({
        next: (_: void) => this.router.navigate(['/home']),
        error: _ => this.onError = true,
      });
  }
}
 














