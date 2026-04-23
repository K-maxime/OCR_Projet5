import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { AuthService } from '../../core/service/auth.service';;
import { Router } from '@angular/router';
import { of} from 'rxjs';


describe('RegisterComponent', () => {

  let fixture: ComponentFixture<RegisterComponent>;

  let authServiceMock: jest.Mocked<AuthService>;
  let routerMock: jest.Mocked<Router>;


  beforeEach(async() => {

    authServiceMock = {
      register: jest.fn().mockReturnValue(of(undefined))} as unknown as jest.Mocked<AuthService>;
    
    routerMock = { navigate: jest.fn() } as unknown as jest.Mocked<Router>;

    await TestBed.configureTestingModule({
      imports: [RegisterComponent],
      providers: [
        {provide: AuthService, useValue: authServiceMock },
        {provide: Router, useValue: routerMock}
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    fixture.detectChanges();
  });

  it('should register successfully', () => {

    const emailInput = fixture.nativeElement.querySelector('[data-testid="email-input"]');
    const usernameInput = fixture.nativeElement.querySelector('[data-testid="username-input"]');
    const passwordInput = fixture.nativeElement.querySelector('[data-testid="password-input"]');
    const submitButton = fixture.nativeElement.querySelector('[data-testid="submit-button"]');

    emailInput.value = 'email@example.com';
    emailInput.dispatchEvent(new Event('input'));
    usernameInput.value = 'usernameExample';
    usernameInput.dispatchEvent(new Event('input'));
    passwordInput.value = 'passwordExample123!';
    passwordInput.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    submitButton.click();

    expect(authServiceMock.register).toHaveBeenCalledWith({ email: 'email@example.com', username: 'usernameExample', password: 'passwordExample123!' });
    expect(routerMock.navigate).toHaveBeenCalledWith(['/home']);
  });

  it('should disable submit button when form is invalid', () => {
    const submitButton = fixture.nativeElement.querySelector('[data-testid="submit-button"]');
    
    expect(submitButton.disabled).toBe(true);
});
      
});


