import { AuthService } from '../../core/service/auth.service';
import { LoginComponent } from './login.component';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';


describe('LoginComponent', () => {

  let fixture: ComponentFixture<LoginComponent>;

  let authServiceMock: jest.Mocked<AuthService>;
  let routerMock: jest.Mocked<Router>;


  beforeEach(async () => {
    authServiceMock = {
      login: jest.fn().mockReturnValue(of({
        userId: 1,
        username: 'Userexample',
        email: 'email@exemple.fr',
        message: 'Login successful'
      }))
    } as unknown as jest.Mocked<AuthService>;
    routerMock = { navigate: jest.fn() } as unknown as jest.Mocked<Router>;

    await TestBed.configureTestingModule({
      imports: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock },
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    fixture.detectChanges();

  });

  it('should log in successfully', () => {

    const loginInput = fixture.nativeElement.querySelector('[data-testid="login-input"]');
    const passwordInput = fixture.nativeElement.querySelector('[data-testid="password-input"]');
    const loginButton = fixture.nativeElement.querySelector('[data-testid="login-button"]');

    loginInput.value = 'email@example.com';
    loginInput.dispatchEvent(new Event('input'));
    passwordInput.value = 'passewordExemple123!';
    passwordInput.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    loginButton.click();

    expect(authServiceMock.login).toHaveBeenCalledWith({ login:'email@example.com', password: 'passewordExemple123!' });  
    expect(routerMock.navigate).toHaveBeenCalledWith(['feed']);
  });

  it('should handle login error', () => {

    authServiceMock.login.mockReturnValue(throwError(() => new Error('Login failed')));
    const loginInput = fixture.nativeElement.querySelector('[data-testid="login-input"]');
    const passwordInput = fixture.nativeElement.querySelector('[data-testid="password-input"]');
    const loginButton = fixture.nativeElement.querySelector('[data-testid="login-button"]');

    loginInput.value = 'email@example.com';
    loginInput.dispatchEvent(new Event('input'));
    passwordInput.value = 'passewordExemple123!';
    passwordInput.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    loginButton.click();

    expect(authServiceMock.login).toHaveBeenCalledWith({ login:'email@example.com', password: 'passewordExemple123!' });
    expect(routerMock.navigate).not.toHaveBeenCalled();

    fixture.detectChanges();
    const errorMessage = fixture.nativeElement.querySelector('[data-testid="error-message"]');
    expect(errorMessage).toBeTruthy();
  });

  it('should disable submit button when form is invalid', () => {
    const loginButton = fixture.nativeElement.querySelector('[data-testid="login-button"]');
    
    // formulaire vide au démarrage
    expect(loginButton.disabled).toBe(true);
  });

});
