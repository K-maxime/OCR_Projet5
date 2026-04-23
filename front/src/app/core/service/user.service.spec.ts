import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { UserService } from './user.service';
import { RegisterRequest } from '../models/registerRequest.interface';

describe('SubjectService', () => {
    let service: UserService;
    let httpCtrl: HttpTestingController;
    const apiUrl = 'http://localhost:9090/api/users/me';

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
            UserService,
            provideHttpClient(),
            provideHttpClientTesting()
                        ],
        });
        service = TestBed.inject(UserService);
        httpCtrl = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpCtrl.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    
    describe('updateUser()', () => {

        it('should perfrom a PUT request to /api/users/me', () => {
            const mockRegisterRequest: RegisterRequest = {
                email: 'test@example.com',
                username: 'testuser',
                password: 'password123'
            }

            service.updateUser(mockRegisterRequest).subscribe();
            const req = httpCtrl.expectOne(apiUrl);
            req.flush(null);

            expect(req.request.method).toBe('PUT');
            expect(req.request.body).toBe(mockRegisterRequest);
        });
    });
});