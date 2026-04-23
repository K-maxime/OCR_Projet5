import { Component, inject } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { AsyncPipe } from '@angular/common';
import { PostService } from '@app/core/service/post.service';
import { Observable } from 'rxjs';
import { Post } from 'src/app/core/models/post.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-feed',
  imports: [MaterialModule, AsyncPipe],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss',
})
export class FeedComponent {

  private postService = inject(PostService);
  private router = inject(Router);
  
  private filter :string = "desc";

  public posts$: Observable<Post[]> = this.postService.getAllPosts(this.filter);

  
  goToCreatePost() {
    this.router.navigate(['/new-post']);
  }

  switchFilter(){
    this.filter = this.filter === "desc" ? "asc" : "desc";
    this.posts$ = this.postService.getAllPosts(this.filter);
  }

}
