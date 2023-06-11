import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { Admin } from '../models/admin';   
import { ErrorResponseService } from './errorResponseChecker';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  public url!:string; 

  constructor(
    private http:HttpClient,
    private errorResponseChecker:ErrorResponseService){
    this.url="http://localhost:8080/admin/";
  }

  logIn=(admin:Admin):Observable<any>=>{  
    const url=`${this.url}logIn`;
    return this.http.post<any>(url,admin,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  logOut=():Observable<any>=>{   
    const url=`${this.url}logOut`
    return this.http.delete<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
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
    const url=`${this.url}dashboard`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  getAdmin=():Observable<any>=>{ 
    const url=`${this.url}get`; 
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);  
    }));
  } 

  updateAdmin=(admin:Admin):Observable<any>=>{
    const url=`${this.url}update`;
    return this.http.put<any>(url,admin,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }
}
