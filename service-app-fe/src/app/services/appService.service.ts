import { HttpClient, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AppService } from "../models/service.model";
import { AppServicePage } from "../models/servicePage.model";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {
    private apiUrl = environment.apiUrl + '/v1/service';
    private defaultPageSize = 5;
   
    constructor(private http: HttpClient) {}
  
    createAppService(appService: AppService): Observable<HttpResponse<AppService>>{
      return this.http.post<HttpResponse<AppService>>(this.apiUrl, appService);
    }
  
    updateAppService(id: string, appService: AppService): Observable<AppService> {
      return this.http.put<AppService>(`${this.apiUrl}/${id}`, appService);
    }
  
    findById(id: string): Observable<AppService> {
      return this.http.get<AppService>(`${this.apiUrl}/${id}`);
    }
  
    findAll(offset:number): Observable<AppServicePage> {
      return this.http.post<AppServicePage>(`${this.apiUrl}/all`, {"offset":offset, "pageSize":this.defaultPageSize});
    }

    delete(id: string): Observable<HttpResponse<any>> {
      return this.http.delete<HttpResponse<any>>(`${this.apiUrl}/${id}`);
    }
}