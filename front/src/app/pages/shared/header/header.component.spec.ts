import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HeaderComponent } from './header.component';
import { NavigationEnd, Router } from '@angular/router';
import { AuthService } from '@app/core/service/auth.service';
import { of, Subject } from 'rxjs';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let routerMock: jest.Mocked<Router>;
  let authServiceMock: jest.Mocked<AuthService>;
  let routerEventsSubject: Subject<any>;

  beforeEach(async () => {
    routerEventsSubject = new Subject();

    routerMock = {
      navigate: jest.fn(),
      url: '/sessions/create',
      events: routerEventsSubject.asObservable()
    } as unknown as jest.Mocked<Router>;

    authServiceMock = {
      logout: jest.fn().mockReturnValue(of(void 0))
    } as unknown as jest.Mocked<AuthService>;

    await TestBed.configureTestingModule({
      imports: [HeaderComponent],
      providers: [
        { provide: Router, useValue: routerMock },
        { provide: AuthService, useValue: authServiceMock }
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  describe('Desktop Navigation', () => {
    it('should display logout button and call logout on click', () => {
      component.isMobileView = false;
      component.isAuthRoute = false;
      fixture.detectChanges();

      const logoutButton = fixture.nativeElement.querySelector('[data-testid="logout-button"]');
      expect(logoutButton).toBeTruthy();

      logoutButton.click();
      expect(authServiceMock.logout).toHaveBeenCalled();
      expect(routerMock.navigate).toHaveBeenCalledWith(['/home']);
    });

    it('should display articles button and navigate to feed on click', () => {
      component.isMobileView = false;
      component.isAuthRoute = false;
      fixture.detectChanges();

      const postButton = fixture.nativeElement.querySelector('[data-testid="post-button"]');
      expect(postButton).toBeTruthy();

      postButton.click();
      expect(routerMock.navigate).toHaveBeenCalledWith(['/feed']);
    });

    it('should display subjects button and navigate to subjects on click', () => {
      component.isMobileView = false;
      component.isAuthRoute = false;
      fixture.detectChanges();

      const subjectButton = fixture.nativeElement.querySelector('[data-testid="subject-button"]');
      expect(subjectButton).toBeTruthy();

      subjectButton.click();
      expect(routerMock.navigate).toHaveBeenCalledWith(['/subjects']);
    });

    it('should display user button and navigate to user on click', () => {
      component.isMobileView = false;
      component.isAuthRoute = false;
      fixture.detectChanges();

      const userButton = fixture.nativeElement.querySelector('[data-testid="user-button"]');
      expect(userButton).toBeTruthy();

      userButton.click();
      expect(routerMock.navigate).toHaveBeenCalledWith(['/user']);
    });
  });

  describe('Mobile Navigation', () => {
    it('should display logout button and call logout on click in mobile menu', () => {
      component.isMobileView = true;
      component.isAuthRoute = false;
      component.isMenuOpen = true;
      fixture.detectChanges();

      const logoutButton = fixture.nativeElement.querySelector('[data-testid="logout-button-mobile"]');
      expect(logoutButton).toBeTruthy();

      logoutButton.click();
      expect(authServiceMock.logout).toHaveBeenCalled();
      expect(routerMock.navigate).toHaveBeenCalledWith(['/home']);
    });

    it('should display articles button and navigate to feed on click in mobile menu', () => {
      component.isMobileView = true;
      component.isAuthRoute = false;
      component.isMenuOpen = true;
      fixture.detectChanges();

      const postButton = fixture.nativeElement.querySelector('[data-testid="post-button-mobile"]');
      expect(postButton).toBeTruthy();

      postButton.click();
      expect(routerMock.navigate).toHaveBeenCalledWith(['/feed']);
    });

    it('should display subjects button and navigate to subjects on click in mobile menu', () => {
      component.isMobileView = true;
      component.isAuthRoute = false;
      component.isMenuOpen = true;
      fixture.detectChanges();

      const subjectButton = fixture.nativeElement.querySelector('[data-testid="subject-button-mobile"]');
      expect(subjectButton).toBeTruthy();

      subjectButton.click();
      expect(routerMock.navigate).toHaveBeenCalledWith(['/subjects']);
    });

    it('should display user button and navigate to user on click in mobile menu', () => {
      component.isMobileView = true;
      component.isAuthRoute = false;
      component.isMenuOpen = true;
      fixture.detectChanges();

      const userButton = fixture.nativeElement.querySelector('[data-testid="user-button-mobile"]');
      expect(userButton).toBeTruthy();

      userButton.click();
      expect(routerMock.navigate).toHaveBeenCalledWith(['/user']);
    });

    it('should close menu when clicking on menu item', () => {
      component.isMobileView = true;
      component.isAuthRoute = false;
      component.isMenuOpen = true;
      fixture.detectChanges();

      const logoutButton = fixture.nativeElement.querySelector('[data-testid="logout-button-mobile"]');
      logoutButton.click();

      expect(component.isMenuOpen).toBe(false);
    });

    it('should close menu when clicking on backdrop', () => {
      component.isMobileView = true;
      component.isAuthRoute = false;
      component.isMenuOpen = true;
      fixture.detectChanges();

      const backdrop = fixture.nativeElement.querySelector('.menu-backdrop');
      backdrop.click();

      expect(component.isMenuOpen).toBe(false);
    });
  });
});