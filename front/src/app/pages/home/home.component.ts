import { Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatButtonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  
  private router = inject(Router);

  constructor() {}

  ngOnInit(): void {}

  register() {
    throw new Error('Method not implemented.');
  }

  login() {
    this.router.navigate(['/login']);
  }
}
