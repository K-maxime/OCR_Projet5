import { Component, inject } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { AsyncPipe } from '@angular/common';
import { SubjectService } from '@app/core/service/subject.service';
import { Observable } from 'rxjs';
import { Subject } from 'src/app/core/models/subjects.interface';

@Component({
  selector: 'app-subjects',
  imports: [MaterialModule, AsyncPipe],
  templateUrl: './subjects.component.html',
  styleUrl: './subjects.component.scss',
})
export class SubjectsComponent {
  
  private subjectService = inject(SubjectService);

  public subjects$: Observable<Subject[]> = this.subjectService.getSubjects();  

  subscribe(id: number) {
    this.subjectService.subscribe(id).subscribe({
        next: (_: void) => location.reload()
    });
  }

}
