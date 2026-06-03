import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { PostService } from './post.service';
import { Post } from '../models/post.interface';
import { CreatePostRequest } from '../models/createPostRequest.interface';
import { CommentRequest } from '../models/commentRequest.interface';

describe('SubjectService', () => {
    let service: PostService;
    let httpCtrl: HttpTestingController;
    const apiUrl = 'http://localhost:9090/api/articles';

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
            PostService,
            provideHttpClient(),
            provideHttpClientTesting()
                        ],
        });
        service = TestBed.inject(PostService);
        httpCtrl = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpCtrl.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    describe('createPost()', () => {
        it('should send POST request to /api/articles', () => {
            const createPostRequest : CreatePostRequest= {
                subjectId: 1,
                title: 'New Post',
                content: 'This is a new post'                
            };

            service.createPost(createPostRequest).subscribe();
            const req = httpCtrl.expectOne(`${apiUrl}`);
            req.flush(null)


            expect(req.request.method).toBe('POST');
            expect(req.request.body).toBe(createPostRequest);
        });

    });
   
    describe('getPost()', () => {

        it('should perfrom a GET request to /api/articles/:id and return the post', () => {
            const postId : string = '1';
            let getResult: Post | undefined;
            const mockPost:Post = {
                id: 1,
                title: 'Angular Testing',
                content: 'Testing Angular applications with HttpClientTestingModule',
                createdAt: new Date(),
                subject: {
                    id: 1,
                    name: 'Angular',
                    description: 'Framework JavaScript moderne',
                    subscribed: true,
                }
                ,author: {
                    userId: 1,
                    username: 'test',
                    email: 'test@example.com',
                    message: 'Login successful'
                },
                comments: []
            };

            service.getPost(postId).subscribe(data => getResult = data);
            const req = httpCtrl.expectOne(`${apiUrl}/${postId}`);
            req.flush(mockPost);

            expect(req.request.method).toBe('GET');
            expect(getResult).toEqual(mockPost);
        });
    });

    describe('createArticleComment()', () => {
        it('should send POST request to /api/articles', () => {
            const postId : string = '1';
            const createCommentRequest : CommentRequest= {
                content: 'This is a comment'                
            };

            service.createArticleComment(postId,createCommentRequest).subscribe();
            const req = httpCtrl.expectOne(`${apiUrl}/${postId}/comments`);
            req.flush(null)


            expect(req.request.method).toBe('POST');
            expect(req.request.body).toBe(createCommentRequest);
        });

    });
   
    describe('getAllPosts', () => {

        it('should perfrom a GET request to /api/articles/:id and return the post', () => {
            const filter : string = 'asc';
            let getResult: Post[] | undefined;
            const mockPost:Post = {
                id: 1,
                title: 'Angular Testing',
                content: 'Testing Angular applications with HttpClientTestingModule',
                createdAt: new Date(),
                subject: {
                    id: 1,
                    name: 'Angular',
                    description: 'Framework JavaScript moderne',
                    subscribed: true,
                }
                ,author: {
                    userId: 1,
                    username: 'test',
                    email: 'test@example.com',
                    message: 'Login successful'
                },
                comments: []
            };
            const mockPosts: Post[] = [mockPost, mockPost];

            service.getAllPosts(filter).subscribe(data => getResult = data);
            const req = httpCtrl.expectOne(`${apiUrl}?sort=${filter}`);
            req.flush(mockPosts);

            expect(req.request.method).toBe('GET');
            expect(getResult).toEqual(mockPosts);
        });
    });
});