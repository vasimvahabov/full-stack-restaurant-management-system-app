import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, retry } from 'rxjs'; 
import { ErrorResponseService } from './errorResponseChecker';
import { ErrorResponse } from '../models/errorResponse';
import { User } from '../modules/admin/models/user';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService{  
  private url!:string;

  constructor(private http:HttpClient,private authService:AuthService,
                               private errorResponseChecker:ErrorResponseService){
    this.url="http://localhost:8080/user/";
  }

  logIn(user:User):Observable<any>{
    return this.http.post<any>(`${this.url}logIn`,user,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  getAllUsers():Observable<any>{ 
    return this.http.get<any>(`${this.url}list/all`,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  changeUserStatus(userId:number):Observable<any>{ 
    const url=`${this.url}change-status/${userId}`; 
    return this.http.put<any>(url,null,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  addUser(user:User):Observable<any>{ 
    return this.http.post<any>(`${this.url}add`,user,{withCredentials:true}).pipe(map((response:any)=>{ 
      return this.errorResponseChecker.check(response);
    }));
  }

  updateUser(user:User):Observable<any>{    
    return this.http.put<any>(`${this.url}update`,user,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  // refreshToken():Observable<void>{ 
  //   return this.http.get<string>(`${this.url}refresh-token`,{withCredentials:true}).pipe(map((data:any)=>{
  //   }));
  // };
}
