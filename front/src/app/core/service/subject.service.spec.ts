import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { SubjectService } from './subject.service';
import { Subject } from '../models/subjects.interface';
import { provideHttpClient } from '@angular/common/http';

describe('SubjectService', () => {
    let service: SubjectService;
    let httpCtrl: HttpTestingController;
    const apiUrl = 'http://localhost:9090/api/subjects';

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
            SubjectService,
            provideHttpClient(),
            provideHttpClientTesting()
                        ],
        });
        service = TestBed.inject(SubjectService);
        httpCtrl = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpCtrl.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    // ===== GET SUBJECTS TESTS =====
    describe('getSubjects()', () => {

        it('should perfrom a GET request to /api/subjects and return an array of subjects', () => {
            let getResult: Subject[] | undefined;
            const mockSubjects: Subject[] = [
                {
                    id: 1,
                    name: 'Angular',
                    description: 'Framework JavaScript moderne',
                    subscribed: true,
                },
                {
                    id: 2,
                    name: 'Spring Boot',
                    description: 'Framework backend Java',
                    subscribed: false,
                }
            ];

            service.getSubjects().subscribe(data => getResult = data);
            const req = httpCtrl.expectOne(apiUrl);
            req.flush(mockSubjects);

            expect(req.request.method).toBe('GET');
            expect(getResult).toEqual(mockSubjects);
        });
    });

    describe('subscribe()', () => {
        it('should send POST request to /api/subjects/:id/subscribe', () => {
            const subjectId = 1;

            service.subscribe(subjectId).subscribe();
            const req = httpCtrl.expectOne(`${apiUrl}/${subjectId}/subscribe`);
            req.flush(null)


            expect(req.request.method).toBe('POST');
            expect(req.request.body).toBe('');
        });

    });

    describe('unsubscribe()', () => {
        it('should send DELETE request to /api/subjects/:id/subscribe', () => {
            const subjectId = 1;

            service.unsubscribe(subjectId).subscribe();
            const req = httpCtrl.expectOne(`${apiUrl}/${subjectId}/subscribe`);
            req.flush(null);

            expect(req.request.method).toBe('DELETE');
            
        });
    });
});