import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';  
import { Product } from '../models/product';
import { ErrorResponseService } from './errorResponseChecker';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private url!:string;
  
  constructor(
    private http:HttpClient,
    private errorResponseService:ErrorResponseService){
    this.url="http://localhost:8080/product/";
  }

  getAllProducts=():Observable<any>=>{
    const url=`${this.url}list/all`;
    return this.http.get(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

  getActiveProducts=():Observable<any>=>{
    const url=`${this.url}list/active`;
    return this.http.get(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  } 

  changeProductStatus(prodId:number):Observable<any>{ 
    const url=`${this.url}change-status/${prodId}`;
    return this.http.put<any>(url,null,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

  updateProduct(product:Product):Observable<any>{
    const url=`${this.url}update`; 
    return this.http.put<any>(url,product,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

  addProduct=(product:Product):Observable<any>=>{ 
    const url=`${this.url}add`;
    return this.http.post<any>(url,product,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }
}
