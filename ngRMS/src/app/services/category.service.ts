import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs'; 
import { Category } from '../models/category';
import { ErrorResponseService } from './errorResponseChecker';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private url!:string;
  
  constructor(private http:HttpClient,private errorResponseService:ErrorResponseService){
    this.url="http://localhost:8080/category/";
  }

  getAllCategories=():Observable<any>=>{
    const url=`${this.url}list/all`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

  getActiveCategories=():Observable<any>=>{ 
    const url=`${this.url}list/active`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

  changeCategoryStatus=(cateId:number):Observable<any>=>{
    const url=`${this.url}change-status/${cateId}`;
    return this.http.put<any>(url,null,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

  updateCategory=(category:Category):Observable<any>=>{
    const url=`${this.url}update`;
    return this.http.put<any>(url,category,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

  addCategory=(category:Category):Observable<any>=>{ 
    return this.http.post<any>(`${this.url}add`,category,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }
}
