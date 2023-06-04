import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs'; 
import { Order } from '../models/order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private url!:string; 

  constructor(private http:HttpClient){
    this.url="https://localhost:7088/order/"
  }

  getInCompleteOrders=(token:string):Observable<Order[]>=>{
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    return this.http.get<Order[]>(`${this.url}list/incomplete`,{headers:header}).pipe(map((data:any)=>{
      return data.map((item:any)=>{
        console.log(data);
        const order:Order={
          id:item.id,
          title:item.title,
          userId:item.userId,
          userFullname:null,
          createdAt:null,
          updatedAt:null,
          total:item.total
        };
        return order; 
      });
    }));    
  }

  getCompletedOrders=(token:string):Observable<Order[]>=>{
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Order[]>("/list/dto/completed/all",{headers:header}).pipe(map((data:any)=>{
      return data.map((item:any)=>{
        const order:Order={
          id:data.id,
          title:data.title,
          userId:data.userId,
          userFullname:data.userFullname,
          createdAt:data.createdAt,
          updatedAt:data.updatedAt,
          total:data.total
        };
        return order;
      });
    }))
  }

  addOrder=(token:string,order:Order):Observable<Order>=>{ 
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.post<Order>(`${this.url}add`,order,{headers:header}).pipe(map((data:any)=>{
      const order:Order={
        id:data.id,
        title:data.title,
        userId:data.userId,
        userFullname:data.userFullname,
        createdAt:data.createdAt,
        updatedAt:data.updatedAt,
        total:data.total
      };
      return order;
    }));
  }

  deleteOrder=(token:string,orderId:number):Observable<void>=>{
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(`${this.url}delete`,{ body:orderId ,headers:header});
  }

  completeOrder=(token:string,orderId:number):Observable<void>=>{
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<void>(`${this.url}complete`,orderId,{headers:header});
  }

  updateOrder=(token:string,order:Order):Observable<void>=>{
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    return this.http.put<void>(`${this.url}update`,order,{headers:header});
  }
}
