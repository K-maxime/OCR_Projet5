import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedComponent } from './feed.component';
import { Post } from '@app/core/models/post.interface';
import { PostService } from '@app/core/service/post.service';
import { of } from 'rxjs';
import { Router } from '@angular/router';

describe('FeedComponent', () => {
  let fixture: ComponentFixture<FeedComponent>;
  let postServiceMock: jest.Mocked<PostService>;
  let routerMock: jest.Mocked<Router>;


   const mockPosts: Post[] = [
    {
      id: 1,
      title: 'First Article',
      content: 'Content 1',
      createdAt: new Date('2024-01-15'),
      subject: { id: 1, name: 'Subject 1', description: 'Description 1', subscribed: true },
      author: { userId: 1, username: 'Userexample', email: 'userexample@example.com', message: 'Author of the first article' },
      comments: [],
    },
    {
      id: 2,
      title: 'Second Article',
      content: 'Content 2',
      createdAt: new Date('2024-01-10'),
      subject: { id: 2, name: 'Subject 2', description: 'Description 2', subscribed: false },
      author: { userId: 2, username: 'AnotherUser', email: 'anotheruser@example.com', message: 'Author of the second article' },
      comments: [],
    },
  ];

  beforeEach(async () => {
    postServiceMock = {
      getAllPosts: jest.fn().mockReturnValue(of(mockPosts)),
    } as unknown as jest.Mocked<PostService>;

    routerMock = {
      navigate: jest.fn(),
      url: '/sessions/create'
    } as unknown as jest.Mocked<Router>;


    await TestBed.configureTestingModule({
      imports: [FeedComponent],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: Router,useValue: routerMock }
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(FeedComponent);
    fixture.detectChanges();
  });

  it('should display the list of sessions', () => {
    const sessionItems = fixture.nativeElement.querySelectorAll('[data-testid="post-item"]');
    expect(sessionItems.length).toBe(1);
  });

  it('should display the button "Créer un article" and it should be clickable to navigate to the create post page', () => {
    const createPostButton = fixture.nativeElement.querySelector('[data-testid="create-post-button"]');

    expect(createPostButton).toBeTruthy();

    createPostButton.click();

    expect(routerMock.navigate).toHaveBeenCalledWith(['/new-post']);

  });

  it('should display the button "filter" and it should be clickable to reload the posts in new order', () => {
    const filterButton = fixture.nativeElement.querySelector('[data-testid="filter-button"]');

    expect(filterButton).toBeTruthy();

    filterButton.click();

    expect(postServiceMock.getAllPosts).toHaveBeenCalledWith('asc');


  });


});
