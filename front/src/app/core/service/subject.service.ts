import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Subject as Subject } from "../models/subjects.interface";


@Injectable({
  providedIn: 'root'
})
export class SubjectService {

    private pathService = 'http://localhost:9090/api/subjects';

    constructor(private httpClient: HttpClient) { }

    public getSubjects(): Observable<Subject[]> {
        return this.httpClient.get<Subject[]>(`${this.pathService}`);
    }

    public unsubscribe(id : number) : Observable<void>{
        return this.httpClient.delete<void>(`${this.pathService}`+ "/"+ id +"/subscribe");
    }

    public subscribe(id : number) : Observable<void>{
        return this.httpClient.post<void>(`${this.pathService}`+ "/"+ id +"/subscribe","");
    }

}
