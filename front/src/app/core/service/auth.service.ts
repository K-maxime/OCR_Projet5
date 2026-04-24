import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { LoginRequest } from "../models/loginRequest.interface";
import { RegisterRequest } from "../models/registerRequest.interface";
import { LoginResponse } from "../models/loginResponse.interface";
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'http://localhost:9090/api/auth';
  
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.hasToken());
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private httpClient: HttpClient,  private router: Router) { }

  public login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(`${this.pathService}/login`, loginRequest).pipe(
      tap((response: LoginResponse) => {
        // Stocker le token
        sessionStorage.setItem('token', response.token);
        this.isAuthenticatedSubject.next(true);
      }));
  }

  public register(RegisterRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/register`, RegisterRequest);
  }

  public logout(): void {
    sessionStorage.removeItem('token');
    this.isAuthenticatedSubject.next(false);
    this.router.navigate(['/home']);
  }

    private hasToken(): boolean {
    return !!sessionStorage.getItem('token');
  }
}