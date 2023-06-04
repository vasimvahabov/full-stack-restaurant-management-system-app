import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs'; 
import { ErrorResponseService } from 'src/app/services/errorResponseChecker';
import { Employee } from '../models/employee';  

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private url!:string;

  constructor(private http:HttpClient,private errorResponseService:ErrorResponseService){
    this.url="http://localhost:8080/employee/";
  }
  
  addEmployee(employee:Employee):Observable<any>{ 
    const url=`${this.url}add`;
    return this.http.post<any>(url,employee,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

  getAllEmployees():Observable<any>{ 
    const url=`${this.url}list/all`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  };

  updateEmployee(employee:Employee):Observable<any>{ 
    const url=`${this.url}update`;
    return this.http.put<any>(url,employee,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

  changeEmployeeStatus(empId:number):Observable<any>{ 
    const url=`${this.url}change-status/${empId}`;
    return this.http.put<any>(url,null,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

}
