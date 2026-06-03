import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { CreatePostRequest } from "../models/createPostRequest.interface"
import { Post } from "../models/post.interface";
import { CommentRequest } from "../models/commentRequest.interface";



@Injectable({
  providedIn: 'root'
})
export class PostService {

    private pathService = 'http://localhost:9090/api/articles';

    constructor(private httpClient: HttpClient) { }

    public createPost(createPostRequest: CreatePostRequest): Observable<void> {
        return this.httpClient.post<void>(`${this.pathService}`, createPostRequest);
    }

    public getPost(id: string): Observable<Post> {
        return this.httpClient.get<Post>(`${this.pathService}` + "/" + id).pipe(
            catchError(error => {
                return throwError(() => error);
                })
            );
    }

    public createArticleComment(postId: string, commentRequest: CommentRequest): Observable<void> {
        return this.httpClient.post<void>(`${this.pathService}`+ "/" + postId +"/comments", commentRequest);
    }

    public getAllPosts(filter: string): Observable<Post[]> {
        return this.httpClient.get<Post[]>(`${this.pathService}`, {
            params : {sort : filter}
        });
    }



}
