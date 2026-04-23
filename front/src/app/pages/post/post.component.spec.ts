import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostComponent } from './post.component';
import { Post } from '@app/core/models/post.interface';
import { PostService } from '@app/core/service/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { UserInformation } from '@app/core/models/userInformation.interface';

describe('PostComponent', () => {
  let component: PostComponent;
  let fixture: ComponentFixture<PostComponent>
  let postServiceMock: jest.Mocked<PostService>;
  let routerMock: jest.Mocked<Router>;

  const mockAuthor : UserInformation= { userId: 1, username: 'Userexample', email: 'userexample@example.com', message: 'Author of the first article'}

  const mockPost : Post=  {
    id: 1,
    title: 'First Article',
    content: 'Content 1',
    createdAt: new Date('2024-01-15'),
    subject: { id: 1, name: 'Subject 1', description: 'Description 1', subscribed: true },
    author: mockAuthor,
    comments: [{id: 1, content: 'Comment 1', author: mockAuthor, createdAt: new Date('2024-01-16')}],
  };

  beforeEach(async () => {
    postServiceMock = {
      getPost: jest.fn().mockReturnValue(of(mockPost)),
      createArticleComment: jest.fn().mockReturnValue(of(void 0))
    } as unknown as jest.Mocked<PostService>;

    routerMock = {
      navigate: jest.fn(),
      url: '/sessions/create'
      } as unknown as jest.Mocked<Router>;

    const activatedRouteMock = {
      snapshot: {
        paramMap: {
          get: jest.fn().mockReturnValue('1')
        }
      }
    };
    
    await TestBed.configureTestingModule({
      imports: [PostComponent],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: Router,useValue: routerMock },
        { provide: ActivatedRoute,useValue: activatedRouteMock }
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create and load the post data', () => {
    const backButton = fixture.nativeElement.querySelector('[data-testid="back-button"]');
    const titlePost  = fixture.nativeElement.querySelector('[data-testid="title-post"]');
    const datePost    = fixture.nativeElement.querySelector('[data-testid="created-at"]');
    const authorPost = fixture.nativeElement.querySelector('[data-testid="author"]');
    const autorComment    = fixture.nativeElement.querySelector('[data-testid="comment-author"]');
    const contentComment = fixture.nativeElement.querySelector('[data-testid="comment-content"]');

    expect(component).toBeTruthy();
    expect(backButton).toBeTruthy();
    expect(titlePost.textContent).toContain(mockPost.title);
    expect(datePost.textContent).toContain(mockPost.createdAt.toString());
    expect(authorPost.textContent).toContain(mockPost.author.username);
    expect(autorComment.textContent).toContain(mockPost.comments[0].author.username);
    expect(contentComment.textContent).toContain(mockPost.comments[0].content);
  });


  it('should display the button "Back" and it should be clickable to navigate to the feed page', () => {
    const backButton = fixture.nativeElement.querySelector('[data-testid="back-button"]');

    expect(backButton).toBeTruthy();

    backButton.click();

    expect(routerMock.navigate).toHaveBeenCalledWith(['/feed']);

  });

  it('should create a new comment successfully', () => {
    const commentContentInput = fixture.nativeElement.querySelector('[data-testid="comment-content-input"]');
    const submitCommentButton = fixture.nativeElement.querySelector('[data-testid="submit-comment-button"]');
    
    commentContentInput.value = 'This is a new comment';
    commentContentInput.dispatchEvent(new Event('input'));
    fixture.detectChanges();
    submitCommentButton.click();

    expect(postServiceMock.createArticleComment).toHaveBeenCalledWith('1', { content: 'This is a new comment' });
  });



});
