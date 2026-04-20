import { Component, inject } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { AbstractControl, FormBuilder, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/core/service/user.service';
import { RegisterRequest } from 'src/app/core/models/registerRequest.interface';
import { Subject } from 'src/app/core/models/subjects.interface';
import { SubjectService } from 'src/app/core/service/subject.service';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-user',
  imports: [MaterialModule, AsyncPipe],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss',
})
export class UserComponent {
  
  private userService = inject(UserService); 
  private subjectService = inject(SubjectService);
  private fb = inject(FormBuilder);
  private router = inject(Router);

  public onError = false;
  public subjects$: Observable<Subject[]> = this.subjectService.getSubjects();  

  public profileForm = this.fb.group({
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

  updateProfile(): void {
      if (this.profileForm.invalid) {
        return;
      }
   
      const registerRequest = this.profileForm.value as RegisterRequest;
   
      this.userService.updateUser(registerRequest).subscribe({
          error: _ => this.onError = true,
        });
  }

  unsubscribe(id: number) : void{
    this.subjectService.unsubscribe(id).subscribe({
        next: (_: void) => {
          location.reload();
        },  
    });
  }

}
