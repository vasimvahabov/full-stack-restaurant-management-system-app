import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { Admin } from '../models/admin';  
import { AuthService } from './auth.service';
import { ErrorResponseService } from './errorResponseChecker';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  public url!:string; 

  constructor(private http:HttpClient,private authService:AuthService,
                               private errorResponseChecker:ErrorResponseService){
    this.url="http://localhost:8080/admin/";
  }

  logIn=(admin:Admin):Observable<any>=>{  
    return this.http.post<any>(`${this.url}logIn`,admin,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  logOut=():Observable<any>=>{   
    return this.http.delete<any>(`${this.url}logOut`,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  refreshToken=():Observable<any>=>{  
    const url=`${this.url}refresh-token`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  } 

  getAdminDasboard=():Observable<any>=>{ 
    return this.http.get<any>(`${this.url}dashboard`,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  getAdmin=():Observable<any>=>{  
    return this.http.get<any>(`${this.url}get`,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);  
    }));
  } 

  updateAdmin=(admin:Admin):Observable<any>=>{
    return this.http.put<any>(`${this.url}update`,admin,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }
}
