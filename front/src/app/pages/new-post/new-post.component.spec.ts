import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewPostComponent } from './new-post.component';
import { Router } from '@angular/router';
import { PostService } from '@app/core/service/post.service';
import { of } from 'rxjs';
import { SubjectService } from '@app/core/service/subject.service';
import { provideNoopAnimations } from '@angular/platform-browser/animations';

describe('NewPostComponent', () => {
  let component: NewPostComponent;
  let fixture: ComponentFixture<NewPostComponent>;
  let routerMock: jest.Mocked<Router>;
  let postServiceMock: jest.Mocked<PostService>;
  let subjectsServiceMock: jest.Mocked<SubjectService>;


  beforeEach(async () => {

    routerMock = {
      navigate: jest.fn(),
      url: '/sessions/create'
    } as unknown as jest.Mocked<Router>;
    postServiceMock = {
      createPost: jest.fn().mockReturnValue(of(void 0))
    } as unknown as jest.Mocked<PostService>;

    subjectsServiceMock = {
      getSubjects: jest.fn().mockReturnValue(of([
        { id: 1, name: 'Subject 1', description: 'Description 1', subscribed: true },
        { id: 2, name: 'Subject 2', description: 'Description 2', subscribed: false },
      ]))
    } as unknown as jest.Mocked<SubjectService>;


    await TestBed.configureTestingModule({
      imports: [NewPostComponent],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: SubjectService, useValue: subjectsServiceMock },
        { provide: Router,useValue: routerMock },
        provideNoopAnimations()
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewPostComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    const backButton = fixture.nativeElement.querySelectorAll('[data-testid="back-button"]');

    expect(component).toBeTruthy();
    expect(backButton).toBeTruthy();
  
  });

  it('should display the button "Back" and it should be clickable to navigate to the feed page', () => {
    const backButton = fixture.nativeElement.querySelector('[data-testid="back-button"]');

    expect(backButton).toBeTruthy();

    backButton.click();

    expect(routerMock.navigate).toHaveBeenCalledWith(['/feed']);

  });

  it('should create a new post successfully', () => {
    fixture.detectChanges();
    const titleInput = fixture.nativeElement.querySelector('[data-testid="title-input"]');
    const contentInput = fixture.nativeElement.querySelector('[data-testid="content-input"]');
    const createButton = fixture.nativeElement.querySelector('[data-testid="create-button"]');

    

    titleInput.value = 'New Post Title';
    titleInput.dispatchEvent(new Event('input'));
    contentInput.value = 'New Post Content';
    contentInput.dispatchEvent(new Event('input'));
    
    fixture.detectChanges();

    fixture.componentInstance.postForm!.get('subject')!.setValue('1');
    fixture.detectChanges();

    createButton.click();

    expect(component.postForm.valid).toBe(true);
    
    expect(postServiceMock.createPost).toHaveBeenCalledTimes(1);
    expect(postServiceMock.createPost).toHaveBeenCalledWith({ subjectId: 1, title: 'New Post Title', content: 'New Post Content' });
    expect(routerMock.navigate).toHaveBeenCalledWith(['/feed']);
  });


});
