import { Component, inject } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SubjectService } from '@app/core/service/subject.service';
import { Observable } from 'rxjs';
import { Subject } from 'src/app/core/models/subjects.interface';
import { AsyncPipe } from '@angular/common';
import { PostService } from "../../core/service/post.service";
import { CreatePostRequest } from '../../core/models/createPostRequest.interface';

@Component({
  selector: 'app-new-post',
  imports: [
    MaterialModule,
    ReactiveFormsModule,
    AsyncPipe],
  templateUrl: './new-post.component.html',
  styleUrl: './new-post.component.scss',
})
export class NewPostComponent {

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private subjectService = inject(SubjectService);
  private postService = inject(PostService);

  public onError = false;
  public subjects$: Observable<Subject[]> = this.subjectService.getSubjects();

  public postForm = this.fb.group({
      subject: ['', [Validators.required]],
      title: ['', [Validators.required, Validators.minLength(3)]],
      content: ['', [ Validators.required]]
  });


  backToFeed() {
    this.router.navigate(["/feed"]);
  }

  create() {
    if (this.postForm.invalid) return;
  
    const { subject, title, content } = this.postForm.value;
    const postRequest: CreatePostRequest = {
      subjectId: Number(subject),
      title: title!,
      content: content!
    };
    
    this.postService.createPost(postRequest).subscribe({
      next: (_: void) => this.router.navigate(["/feed"]),
    });
  }
  

}
