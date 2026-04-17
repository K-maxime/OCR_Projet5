import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginRequest } from "../models/loginRequest.interface";
import { UserInformation } from "../models/userInformation.interface";


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'http://localhost:9090/api/auth';

  constructor(private httpClient: HttpClient) { }

  public login(loginRequest: LoginRequest): Observable<UserInformation> {
    return this.httpClient.post<UserInformation>(`${this.pathService}/login`, loginRequest);
  }
}