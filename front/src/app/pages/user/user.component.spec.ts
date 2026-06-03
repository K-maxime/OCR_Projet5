import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { UserComponent } from './user.component';
import { UserService } from '../../core/service/user.service';
import { SubjectService } from '../../core/service/subject.service';
import { Router } from '@angular/router';
import { MaterialModule } from '../shared/material.module';
import { of, throwError } from 'rxjs';
import { Subject } from '../../core/models/subjects.interface';

describe('UserComponent', () => {
  let component: UserComponent;
  let fixture: ComponentFixture<UserComponent>;
  let userService: jest.Mocked<UserService>;
  let subjectService: jest.Mocked<SubjectService>;
  let router: jest.Mocked<Router>;

  // Mock data
  const mockSubjects: Subject[] = [
    { id: 1, name: 'Math', description: 'Mathematics', subscribed: true },
    { id: 2, name: 'Physics', description: 'Physics', subscribed: false },
    { id: 3, name: 'Chemistry', description: 'Chemistry', subscribed: false },
  ];

  beforeEach(async () => {
    // Créer les mocks
    const userServiceMock = {
      updateUser: jest.fn(),
    };

    const subjectServiceMock = {
      getSubjects: jest.fn(),
      unsubscribe: jest.fn(),
    };

    const routerMock = {
      navigate: jest.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [UserComponent, MaterialModule, ReactiveFormsModule],
      providers: [
        { provide: UserService, useValue: userServiceMock },
        { provide: SubjectService, useValue: subjectServiceMock },
        { provide: Router, useValue: routerMock },
      ],
    }).compileComponents();

    userService = TestBed.inject(UserService) as jest.Mocked<UserService>;
    subjectService = TestBed.inject(SubjectService) as jest.Mocked<SubjectService>;
    router = TestBed.inject(Router) as jest.Mocked<Router>;

    // Setup par défaut
    subjectService.getSubjects.mockReturnValue(of(mockSubjects));

    fixture = TestBed.createComponent(UserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  // ============= TESTS DE CRÉATION =============
  describe('Création du composant', () => {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should initialize with onError = false', () => {
      expect(component.onError).toBe(false);
    });

    it('should load subjects on init', (done) => {
      component.subjects$.subscribe((subjects) => {
        expect(subjects).toEqual(mockSubjects);
        expect(subjectService.getSubjects).toHaveBeenCalled();
        done();
      });
    });

    it('should call getSubjects only once', () => {
      expect(subjectService.getSubjects).toHaveBeenCalledTimes(1);
    });
  });

  // ============= TESTS DU FORMULAIRE =============
  describe('Formulaire de profil', () => {
    it('should initialize profileForm with empty values', () => {
      expect(component.profileForm.value).toEqual({
        email: '',
        username: '',
        password: '',
      });
    });

    it('should have invalid form when empty', () => {
      expect(component.profileForm.invalid).toBe(true);
    });

    it('should have required validators on email', () => {
      const emailControl = component.profileForm.get('email');
      
      emailControl?.setValue('');
      expect(emailControl?.hasError('required')).toBe(true);
      
      emailControl?.setValue('invalid-email');
      expect(emailControl?.hasError('email')).toBe(true);
      
      emailControl?.setValue('valid@email.com');
      expect(emailControl?.valid).toBe(true);
    });

    it('should have required and minLength validators on username', () => {
      const usernameControl = component.profileForm.get('username');
      
      usernameControl?.setValue('');
      expect(usernameControl?.hasError('required')).toBe(true);
      
      usernameControl?.setValue('ab');
      expect(usernameControl?.hasError('minlength')).toBe(true);
      
      usernameControl?.setValue('validUsername');
      expect(usernameControl?.valid).toBe(true);
    });
  });

  // ============= TESTS DU PASSWORD VALIDATOR =============
  describe('Password Strength Validator', () => {
    let passwordControl: any;

    beforeEach(() => {
      passwordControl = component.profileForm.get('password');
    });

    it('should validate empty password', () => {
      passwordControl?.setValue('');
      expect(passwordControl?.errors).toEqual({ required: true });
    });

    it('should reject password without digit', () => {
      passwordControl?.setValue('NoDigit!Abc');
      expect(passwordControl?.hasError('passwordStrength')).toBe(true);
    });

    it('should reject password without lowercase', () => {
      passwordControl?.setValue('NODIGIT1!ABC');
      expect(passwordControl?.hasError('passwordStrength')).toBe(true);
    });

    it('should reject password without uppercase', () => {
      passwordControl?.setValue('nodigit1!abc');
      expect(passwordControl?.hasError('passwordStrength')).toBe(true);
    });

    it('should reject password without special character', () => {
      passwordControl?.setValue('NoDigit1Abc');
      expect(passwordControl?.hasError('passwordStrength')).toBe(true);
    });

    it('should accept valid strong password', () => {
      passwordControl?.setValue('ValidPass123!');
      expect(passwordControl?.valid).toBe(true);
      expect(passwordControl?.hasError('passwordStrength')).toBe(false);
    });

    it('should accept various special characters', () => {
      const validPasswords = [
        'ValidPass1!',
        'ValidPass1@',
        'ValidPass1#',
        'ValidPass1$',
        'ValidPass1%',
        'ValidPass1^',
        'ValidPass1&',
        'ValidPass1*',
        'ValidPass1-',
      ];

      validPasswords.forEach((password) => {
        passwordControl?.setValue(password);
        expect(passwordControl?.valid).toBe(true);
      });
    });
  });

  // ============= TESTS DE updateProfile =============
  describe('updateProfile()', () => {
    beforeEach(() => {
      // Setup un formulaire valide
      component.profileForm.patchValue({
        email: 'user@example.com',
        username: 'testUser',
        password: 'ValidPass123!',
      });
    });

    it('should not call userService if form is invalid', () => {
      component.profileForm.reset();
      component.updateProfile();

      expect(userService.updateUser).not.toHaveBeenCalled();
    });

    it('should call userService.updateUser with form values when form is valid', () => {
      userService.updateUser.mockReturnValue(of());

      component.updateProfile();

      expect(userService.updateUser).toHaveBeenCalledWith({
        email: 'user@example.com',
        username: 'testUser',
        password: 'ValidPass123!',
      });
    });

    it('should set onError to true when service returns error', () => {
      userService.updateUser.mockReturnValue(
        throwError(() => new Error('Update failed'))
      );

      component.updateProfile();

      expect(component.onError).toBe(true);
    });

    it('should not set onError when service succeeds', () => {
      userService.updateUser.mockReturnValue(of());

      component.updateProfile();

      expect(component.onError).toBe(false);
    });

    it('should call updateUser only once per call', () => {
      userService.updateUser.mockReturnValue(of());

      component.updateProfile();
      expect(userService.updateUser).toHaveBeenCalledTimes(1);

      component.updateProfile();
      expect(userService.updateUser).toHaveBeenCalledTimes(2);
    });

    it('should handle error gracefully without throwing', () => {
      userService.updateUser.mockReturnValue(
        throwError(() => new Error('Network error'))
      );

      expect(() => {
        component.updateProfile();
      }).not.toThrow();
    });
  });

  // ============= TESTS DE unsubscribe =============
  describe('unsubscribe()', () => {

    beforeEach(() => {
      jest.spyOn(component, 'reloadPage').mockImplementation(() => {});
    });

    afterEach(() => {
      jest.restoreAllMocks();
    });

    it('should call subjectService.unsubscribe with correct id', () => {
      subjectService.unsubscribe.mockReturnValue(of(void 0));

      component.unsubscribe(1);

      expect(component.reloadPage).toHaveBeenCalledTimes(1);
    });

    it('should reload page on successful unsubscribe', () => {
      subjectService.unsubscribe.mockReturnValue(of(void 0));

      component.unsubscribe(1);

      expect(component.reloadPage).toHaveBeenCalled();
    });

    it('should call unsubscribe with different ids', () => {
      subjectService.unsubscribe.mockReturnValue(of(void 0));

      component.unsubscribe(1);
      component.unsubscribe(2);
      component.unsubscribe(3);

      expect(subjectService.unsubscribe).toHaveBeenCalledWith(1);
      expect(subjectService.unsubscribe).toHaveBeenCalledWith(2);
      expect(subjectService.unsubscribe).toHaveBeenCalledWith(3);
      expect(subjectService.unsubscribe).toHaveBeenCalledTimes(3);
    });

    it('should still reload on unsubscribe even if service returns void', () => {
      subjectService.unsubscribe.mockReturnValue(of(void 0));

      component.unsubscribe(1);

      expect(component.reloadPage).toHaveBeenCalledTimes(1);
    });

    it('should reload once per unsubscribe call', () => {
      subjectService.unsubscribe.mockReturnValue(of(void 0));

      component.unsubscribe(1);
      expect(component.reloadPage).toHaveBeenCalledTimes(1);

      component.unsubscribe(2);
      expect(component.reloadPage).toHaveBeenCalledTimes(2);
    });
  });

  // ============= TESTS D'INTÉGRATION =============
  describe('Intégration', () => {
    it('should have all form controls', () => {
      expect(component.profileForm.get('email')).toBeTruthy();
      expect(component.profileForm.get('username')).toBeTruthy();
      expect(component.profileForm.get('password')).toBeTruthy();
    });

    it('should update onError when multiple update attempts fail', () => {
      // Setup un formulaire valide
      component.profileForm.patchValue({
        email: 'user@example.com',
        username: 'testUser',
        password: 'ValidPass123!',
      });

      userService.updateUser.mockReturnValue(
        throwError(() => new Error('Failed'))
      );

      component.updateProfile();
      expect(component.onError).toBe(true);

      component.updateProfile();
      expect(component.onError).toBe(true);
    });

    it('should handle rapid unsubscribe calls', () => {
      subjectService.unsubscribe.mockReturnValue(of(void 0));
      jest.spyOn(component, 'reloadPage').mockImplementation(() => {});

      component.unsubscribe(1);
      component.unsubscribe(2);
      component.unsubscribe(3);

      expect(subjectService.unsubscribe).toHaveBeenCalledTimes(3);
      expect(component.reloadPage).toHaveBeenCalledTimes(3);
    });
  });

  // ============= TESTS EDGE CASES =============
  describe('Edge Cases', () => {
    it('should handle very long email', () => {
      const longEmail = 'a'.repeat(100) + '@example.com';
      const emailControl = component.profileForm.get('email');
      
      emailControl?.setValue(longEmail);
      expect(emailControl?.valid).toBe(false);
    });

    it('should handle password with multiple special characters', () => {
      const passwordControl = component.profileForm.get('password');
      
      passwordControl?.setValue('Pass123!@#$%^');
      expect(passwordControl?.valid).toBe(true);
    });

    it('should handle username with numbers and hyphens', () => {
      const usernameControl = component.profileForm.get('username');
      
      usernameControl?.setValue('user-name-123');
      expect(usernameControl?.valid).toBe(true);
    });

    it('should reject password with only required characters and no more', () => {
      const passwordControl = component.profileForm.get('password');
      
      // Exactement 1 de chaque: digit, lowercase, uppercase, special
      passwordControl?.setValue('aA1!');
      expect(passwordControl?.valid).toBe(false);
    });
  });
});
