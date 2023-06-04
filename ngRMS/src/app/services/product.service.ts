import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';  
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private url!:string;
  constructor(private http:HttpClient){
    this.url="https://localhost:7088/product/";
  }

  getAllProducts=(token:string):Observable<Product[]>=>{
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.get(`${this.url}list/all`,{headers:header}).pipe(map((data:any)=>{
      return data.map((item:any)=>{
        const product:Product={
          id:item.id,
          name:item.name,
          price:item.price,
          status:item.status,
          cateId:item.cateId,
          cateName:item.cateName,
          cateStatus:item.cateStatus
        };  
        return product;        
      });
    }));
  }

  getActiveProducts=(token:string):Observable<Product[]>=>{
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.get(`${this.url}list/active`,{headers:header}).pipe(map((data:any)=>{
      return data.map((item:any)=>{
        const product:Product={
          id:item.id,
          name:item.name,
          price:item.price,
          status:item.status,
          cateId:item.cateId,
          cateName:item.cateName,
          cateStatus:item.cateStatus
        };  
        return product;  
      });
    }));
  } 

  changeProductStatus(token:string,prodId:number):Observable<void>{
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<void>(`${this.url}change-status`,prodId,{headers:header});
  }

  updateProduct(token:string,product:Product):Observable<void>{
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<void>(`${this.url}update`,product,{headers:header});
  }

  addProduct=(token:string,product:Product):Observable<Product>=>{
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    return this.http.post<Product>(`${this.url}add`,product,{headers:header}).pipe(map((data:any)=>{
      const product:Product={
        id:data.id,
        name:data.name,
        price:data.price,
        status:data.status,
        cateId:data.cateId,
        cateName:data.cateName,
        cateStatus:data.cateStatus
      };  
      return product;
    }));
  }
}
