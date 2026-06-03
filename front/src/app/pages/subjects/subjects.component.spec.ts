import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SubjectsComponent } from './subjects.component';
import { SubjectService } from '@app/core/service/subject.service';
import { of } from 'rxjs';
import { Subject } from 'src/app/core/models/subjects.interface';

describe('SubjectsComponent', () => {
  let component: SubjectsComponent;
  let fixture: ComponentFixture<SubjectsComponent>;
  let subjectServiceMock: jest.Mocked<SubjectService>;

  const mockSubjects: Subject[] = [
    { id: 1, name: 'Angular', description: 'Learn Angular', subscribed: false },
    { id: 2, name: 'TypeScript', description: 'Learn TypeScript', subscribed: true }
  ];

  beforeEach(async () => {
    subjectServiceMock = {
      getSubjects: jest.fn().mockReturnValue(of(mockSubjects)),
      subscribe: jest.fn().mockReturnValue(of(void 0))
    } as unknown as jest.Mocked<SubjectService>;

    await TestBed.configureTestingModule({
      imports: [SubjectsComponent],
      providers: [{ provide: SubjectService, useValue: subjectServiceMock }]
    }).compileComponents();

    fixture = TestBed.createComponent(SubjectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should load and display subjects', async () => {
    await fixture.whenStable();
    fixture.detectChanges();

    const cards = fixture.nativeElement.querySelectorAll('mat-card');
    expect(cards.length).toBe(2);
  });

  it('should display subscribe button for unsubscribed subject', async () => {
    await fixture.whenStable();
    fixture.detectChanges();

    const subscribeBtn = fixture.nativeElement.querySelector('.subscribe-btn');
    expect(subscribeBtn).toBeTruthy();
  });

  it('should display subscribed button for subscribed subject', async () => {
    await fixture.whenStable();
    fixture.detectChanges();

    const subscribedBtn = fixture.nativeElement.querySelector('.subscribed-btn');
    expect(subscribedBtn).toBeTruthy();
  });

  it('should call subscribe and reload on button click', async () => {
    await fixture.whenStable();
    fixture.detectChanges();

    const subscribeBtn = fixture.nativeElement.querySelector('.subscribe-btn');
    subscribeBtn.click();

    expect(subjectServiceMock.subscribe).toHaveBeenCalledWith(1);
  });
});