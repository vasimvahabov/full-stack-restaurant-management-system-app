import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs'; 
import { OrderedProduct } from '../models/orderedProduct';
import { ErrorResponseService } from './errorResponseChecker';

@Injectable({
  providedIn: 'root'
})
export class OrderedProductService {

  private url:string;

  constructor(
    private http:HttpClient,
    private errorResponseService:ErrorResponseService){
    this.url="http://localhost:8080/ordered-product/";
  }

  getOPsByOrderId=(orderId:number):Observable<any>=>{
    const url=`${this.url}list?orderId=${orderId}`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map(response=>{
      return this.errorResponseService.check(response);
    }));
  }

  getPDF=(orderId:number):Observable<any>=>{
    const url=`${this.url}get-pdf?orderId=${orderId}`;
    return this.http.get<any>(url,{
      withCredentials:true
    }).pipe(map((response)=>{ 
      return this.errorResponseService.check(response);
    }));
  }

  addOP(op:OrderedProduct):Observable<any>{ 
    const url=`${this.url}add`;
    return this.http.post<any>(url,op,{withCredentials:true}).pipe(map(response=>{
      return this.errorResponseService.check(response);
    }));
  }

  minusOPByOrderIdAndProdId(orderedProduct:OrderedProduct){
    const url=`${this.url}minus`;
    return this.http.delete(url,{body:orderedProduct,withCredentials:true}).pipe(map(response=>{
      return this.errorResponseService.check(response);
    }));
  } 

  deleteOPsByOrderIdAndProdId(orderedProduct:OrderedProduct){ 
    const url=`${this.url}delete`;
    return this.http.delete(url,{body:orderedProduct,withCredentials:true}).pipe(map(response=>{
      return this.errorResponseService.check(response);
    }));
  }
}
