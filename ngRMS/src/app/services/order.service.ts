import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs'; 
import { Order } from '../models/order';
import { ErrorResponseService } from './errorResponseChecker';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private url!:string; 

  constructor(
    private http:HttpClient,
    private errorResponseService:ErrorResponseService){
    this.url="http://localhost:8080/order/"
  }

  getInCompleteOrdersByUserId=():Observable<any>=>{  
    const url=`${this.url}list/incomplete`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));    
  }

  getCompletedOrders=():Observable<any>=>{ 
    const url=`${this.url}list/completed`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));     
  }

  addOrder=(order:Order):Observable<any>=>{ 
    const url=`${this.url}add`; 
    return this.http.post<any>(url,order,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));     
  }

  deleteOrder=(orderId:number):Observable<any>=>{
    const url=`${this.url}delete?orderId=${orderId}`;
    return this.http.delete<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));     
  }

  completeOrder=(orderId:number):Observable<any>=>{ 
    const url=`${this.url}complete?orderId=${orderId}`;
    return this.http.put<any>(url,null,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));     
  }

  updateOrder=(order:Order):Observable<any>=>{
    const url=`${this.url}update`;
    return this.http.put<any>(url,order,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));     
  }
}
