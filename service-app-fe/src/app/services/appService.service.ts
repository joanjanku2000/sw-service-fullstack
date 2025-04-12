import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AppService } from "../models/service.model";
import { UUID } from "crypto";
import { AppServicePage } from "../models/servicePage.model";

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {
    private apiUrl = 'http://localhost:8080/v1/service';

    constructor(private http: HttpClient) {}
  
    createAppService(appService: AppService): Observable<AppService> {
      return this.http.post<AppService>(this.apiUrl, appService);
    }
  
    updateAppService(id: string, appService: AppService): Observable<AppService> {
      return this.http.put<AppService>(`${this.apiUrl}/${id}`, appService);
    }
  
    findById(id: string): Observable<AppService> {
        console.log("ID: " + id)
      return this.http.get<AppService>(`${this.apiUrl}/${id}`);
    }
  
    findAll(offset:number): Observable<AppServicePage> {
      return this.http.post<AppServicePage>(`${this.apiUrl}/all`, {"offset":offset, "pageSize":3});
    }
}