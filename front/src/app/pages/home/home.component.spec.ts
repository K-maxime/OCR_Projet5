import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { Router } from '@angular/router';

describe('HomeComponent', () => {
  let fixture: ComponentFixture<HomeComponent>;
  let routerMock: jest.Mocked<Router>;

  beforeEach(async () => {
    routerMock = {
          navigate: jest.fn(),
          url: '/sessions/create'
        } as unknown as jest.Mocked<Router>;

    await TestBed.configureTestingModule({
      imports: [ HomeComponent ],
      providers: [
        { provide: Router,useValue: routerMock }
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    fixture.detectChanges();
  });

  it('should display the button "Se connecter" and it should be clickable to navigate to the login page', () => {
    const loginButton = fixture.nativeElement.querySelector('[data-testid="login-button"]');

    expect(loginButton).toBeTruthy();

    loginButton.click();

    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);

  });

  it('should display the button "S inscrire" and it should be clickable to navigate to the register page', () => {
    const registerButton = fixture.nativeElement.querySelector('[data-testid="register-button"]');

    expect(registerButton).toBeTruthy();

    registerButton.click();

    expect(routerMock.navigate).toHaveBeenCalledWith(['/register']);

  });


});
