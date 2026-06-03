import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { RegisterRequest } from "../models/registerRequest.interface";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = 'http://localhost:9090/api/users/me';

  constructor(private httpClient: HttpClient) { }

  public updateUser(RegisterRequest: RegisterRequest): Observable<void> {
    return this.httpClient.put<void>(`${this.pathService}`, RegisterRequest);
  }
}