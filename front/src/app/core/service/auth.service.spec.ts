import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { UserInformation } from '../models/userInformation.interface';
import { LoginRequest } from '../models/loginRequest.interface';
import { RegisterRequest } from '../models/registerRequest.interface';

describe('AuthService', () => {
    let service: AuthService;
    let httpCtrl: HttpTestingController;
    const apiUrl = 'http://localhost:9090/api/auth';

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
            AuthService,
            provideHttpClient(),
            provideHttpClientTesting()
                        ],
        });
        service = TestBed.inject(AuthService);
        httpCtrl = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpCtrl.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    describe('login()', () => {

        it('should perfrom a Post request to /api/auth and user information', () => {
            let postResult: UserInformation | undefined;
            const fakeLoginRequest :LoginRequest= {
                login: 'test',
                password: 'password'
            };
            const mockUserInformation:UserInformation = {
                userId: 1,
                username: 'test',
                email: 'test@example.com',
                message: 'Login successful'
            };

            service.login(fakeLoginRequest).subscribe(data => postResult = data);
            const req = httpCtrl.expectOne(`${apiUrl}/login`);
            req.flush(mockUserInformation);

            expect(req.request.method).toBe('POST');
            expect(req.request.body).toBe(fakeLoginRequest);
            expect(postResult).toEqual(mockUserInformation);
        });
    });

    describe('register()', () => {
        it('should send POST request to /api/auth/register', () => {
            const fakeRegisterRequest :RegisterRequest = {
                username: 'test',
                email: 'test@example.com',
                password: 'password'
            };

            service.register(fakeRegisterRequest).subscribe();
            const req = httpCtrl.expectOne(`${apiUrl}/register`);
            req.flush(null)


            expect(req.request.method).toBe('POST');
            expect(req.request.body).toBe(fakeRegisterRequest);
        });

    });

    describe('logout()', () => {
        it('should send POST request to /api/auth/logout', () => {

            service.logout().subscribe();
            const req = httpCtrl.expectOne(`${apiUrl}/logout`);
            req.flush(null);

            expect(req.request.method).toBe('POST');
            
        });
    });
});