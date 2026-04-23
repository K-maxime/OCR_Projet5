import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderComponent } from './header.component';
import { Router } from '@angular/router';
import { AuthService } from '@app/core/service/auth.service';
import { of } from 'rxjs';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let routerMock: jest.Mocked<Router>;
  let authServiceMock: jest.Mocked<AuthService>;

  beforeEach(async () => {

    routerMock = {
      navigate: jest.fn(),
      url: '/sessions/create'
      } as unknown as jest.Mocked<Router>;

    authServiceMock = {
      logout: jest.fn().mockReturnValue(of(void 0))
    } as unknown as jest.Mocked<AuthService>;

    await TestBed.configureTestingModule({
      imports: [HeaderComponent],
      providers: [
        { provide: Router,useValue: routerMock },
        { provide: AuthService,useValue: authServiceMock }
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should display the button "Se deconnecter" and it should be clickable to navigate to the home page', () => {
    const logoutButton = fixture.nativeElement.querySelector('[data-testid="logout-button"]');

    expect(logoutButton).toBeTruthy();

    logoutButton.click();

    expect(authServiceMock.logout).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/home']);

  });

  it('should display the button "Articles" and it should be clickable to navigate to the feed page', () => {
    const postButton = fixture.nativeElement.querySelector('[data-testid="post-button"]');

    expect(postButton).toBeTruthy();

    postButton.click();

    expect(routerMock.navigate).toHaveBeenCalledWith(['/feed']);

  });

  it('should display the button "Thèmes" and it should be clickable to navigate to the home page', () => {
    const subjectButton = fixture.nativeElement.querySelector('[data-testid="subject-button"]');

    expect(subjectButton).toBeTruthy();

    subjectButton.click();

    expect(routerMock.navigate).toHaveBeenCalledWith(['/subjects']);

  });

  it('should display the button "Profile" and it should be clickable to navigate to the user page', () => {
    const userButton = fixture.nativeElement.querySelector('[data-testid="user-button"]');

    expect(userButton).toBeTruthy();

    userButton.click();

    expect(routerMock.navigate).toHaveBeenCalledWith(['/user']);

  });


});
