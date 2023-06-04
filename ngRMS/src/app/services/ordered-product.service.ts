import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { OrderedProduct } from '../models/orderedProduct';

@Injectable({
  providedIn: 'root'
})
export class OrderedProductService {

  constructor(private http:HttpClient){}

  // getOrderedProductDTOsByOrderId=(orderId:string):Observable<OrderedProduct[]>=>{
  //   const encodedOrderId=encodeURIComponent(orderId);
  //   const header=new HttpHeaders({
  //     'Content-Type'  : 'application/json',
  //     // 'Authorization' : 'Basic '+window.btoa(`${authDetails.username}:${authDetails.password}`)
  //   });

  //   return this.http.get<OrderedProduct[]>(`http://localhost:8080/ordered-products/list/dto/getBy?orderId=${encodedOrderId}`,{headers:header})
  //   .pipe(map((data:any)=>{
  //     return data.map((item:any)=>{
  //       return new OrderedProduct(
  //         item.orderId,item.prodId,item.prodName,
  //         item.cateName,item.prodCount,item.total
  //       );
  //     });
  //   }));
  // }

  getPDF=(userId:number,orderId:number):Observable<Blob>=>{
    const orderDetails:object={ userId, orderId };
    const header=new HttpHeaders({
      'Content-Type':  'application/json',
      // 'Authorization': `Bearer ${this.token}`,
      'orderDetails':JSON.stringify(orderDetails)
    });
    return this.http.get<Blob>("http://localhost:8080/ordered-products/get-pdf",{
      headers:header,
      responseType: 'blob' as 'json'
    });
  }

  addOrderedProduct(orderedProductDetails:object):Observable<void>{
    const header=new HttpHeaders({
      'Content-Type'  : 'application/json',
      // 'Authorization' : 'Basic '+window.btoa(`${authDetails.username}:${authDetails.password}`),
    });
    return this.http.post<void>("http://localhost:8080/ordered-products/add",orderedProductDetails,{headers:header});
  }

  minusOrderedProductByOrderIdAndProdId(orderedProductDetails:object){
    const header=new HttpHeaders({
      'Content-Type'  : 'application/json',
      // 'Authorization' : 'Basic '+window.btoa(`${authDetails.username}:${authDetails.password}`)
    });
    return this.http.delete("http://localhost:8080/ordered-products/minus",{
      body:orderedProductDetails,
      headers:header
    });
  } 

  deleteOrderedProductsByOrderIdAndProdId(orderedProductDetails:object){
    const header=new HttpHeaders({
      'Content-Type'  : 'application/json',
      // 'Authorization' : 'Basic '+window.btoa(`${authDetails.username}:${authDetails.password}`)
    });
    return this.http.delete("http://localhost:8080/ordered-products/delete",{
      body:orderedProductDetails
    });
  }
}
