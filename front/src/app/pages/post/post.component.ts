import { Component, inject, OnInit } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { AsyncPipe } from '@angular/common';
import { PostService } from 'src/app/core/service/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/core/models/post.interface';
import { Observable } from 'rxjs';
import { error } from 'console';
import { FormBuilder, Validators } from '@angular/forms';
import { CommentRequest } from 'src/app/core/models/commentRequest.interface';

@Component({
  selector: 'app-post',
  imports: [
    MaterialModule, 
    AsyncPipe],
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss',
})
export class PostComponent implements OnInit {

  private postService = inject(PostService);
  private router = inject(Router);
  private route = inject(ActivatedRoute); 
  private fb = inject(FormBuilder);

  public postId!: string;
  public post$!: Observable<Post> ;

  ngOnInit(): void {
    this.postId = this.route.snapshot.paramMap.get('id')!;
    this.post$ = this.postService.getPost(this.postId);
    
    this.post$.subscribe({
      error: (error) => {
         this.router.navigate(["not-found"])
      }
    });
  }
  
  backToFeed() {
    this.router.navigate(['/feed'])
  }

  public commentForm = this.fb.group({
    content: ['', [Validators.required]]
  });
  
  comment() {

    const content = this.commentForm.value as CommentRequest;

    this.postService.createArticleComment(this.postId, content).subscribe({
      next: (_: void) => location.reload()
    });
  }

}
