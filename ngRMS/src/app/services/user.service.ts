import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs'; 
import { ErrorResponseService } from './errorResponseChecker'; 
import { User } from '../modules/admin/models/user'; 
@Injectable({
  providedIn: 'root'
})
export class UserService{  
  private url!:string;

  constructor(
    private http:HttpClient,
    private errorResponseChecker:ErrorResponseService){
    this.url="http://localhost:8080/user/";
  }

  logIn(user:User):Observable<any>{
    const url=`${this.url}logIn`;
    return this.http.post<any>(url,user,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  logOut():Observable<any>{
    const url=`${this.url}logOut`;
    return this.http.delete<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  getAllUsers():Observable<any>{ 
    const url=`${this.url}list/all`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
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
    const url=`${this.url}add`;
    return this.http.post<any>(url,user,{withCredentials:true}).pipe(map((response:any)=>{ 
      return this.errorResponseChecker.check(response);
    }));
  }

  updateUser(user:User):Observable<any>{    
    const url=`${this.url}update`;
    return this.http.put<any>(url,user,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }

  refreshToken():Observable<any>{ 
    const url=`${this.url}refresh-token`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseChecker.check(response);
    }));
  }
}
