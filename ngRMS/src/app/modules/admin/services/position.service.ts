import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs'; 
import { ErrorResponseService } from 'src/app/services/errorResponseChecker';
import { Position } from '../models/position';

@Injectable({
  providedIn: 'root'
})
export class PositionService {

  private url!:string;
  constructor(private http:HttpClient,private errorResponseService:ErrorResponseService) {
    this.url="http://localhost:8080/position/"
   }

  getAllPositions():Observable<any>{ 
    const url=`${this.url}list/all`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

  getActivePositions():Observable<any>{
    const url=`${this.url}list/active`;
    return this.http.get<any>(url,{withCredentials:true}).pipe(map((response:any)=>{
      return this.errorResponseService.check(response);
    }));
  }

  addPosition(position:Position):Observable<any>{
    const url=`${this.url}add`;
    return this.http.post<any>(url,position,{withCredentials:true}).pipe(map((response:any)=>{ 
      return this.errorResponseService.check(response);  
    }));
  }

  updatePosition(position:Position):Observable<any>{
    const url=`${this.url}update`; 
    return this.http.put<any>(url,position,{withCredentials:true}).pipe(map((response:any)=>{ 
      return this.errorResponseService.check(response);  
    }));
  }

  changePositionStatus(posId:number):Observable<any>{
    const url=`${this.url}change-status/${posId}`;
    return this.http.put<any>(url,null,{withCredentials:true}).pipe(map((response:any)=>{ 
      return this.errorResponseService.check(response);  
    }));
  }
}
