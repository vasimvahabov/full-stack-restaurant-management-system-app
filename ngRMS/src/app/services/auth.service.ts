import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ErrorResponse } from '../models/errorResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private errorResponse!:BehaviorSubject<ErrorResponse>;
  private errorResponseAsObs!:Observable<ErrorResponse>;
  private logIn!:BehaviorSubject<number>;
  private logInAsSubs!:Observable<number>;
  
  constructor(){
    this.initErrorResponse();
    this.initLogIn();
  }

  initErrorResponse(){
    this.errorResponse=new BehaviorSubject<any>(null);
    this.errorResponseAsObs=this.errorResponse.asObservable();
  }
  
  initLogIn(){
    this.logIn=new BehaviorSubject<number>(0);
    this.logInAsSubs=this.logIn.asObservable();
  }

  setErrorResponse(errorResponse:ErrorResponse){
    this.errorResponse=new BehaviorSubject<ErrorResponse>(errorResponse);
    this.errorResponseAsObs=this.errorResponse.asObservable();
  }
  
  getErrorResponse(){
    return this.errorResponseAsObs;
  }

  setLogIn=()=>{
    this.logIn=new BehaviorSubject<number>(1);
    this.logInAsSubs=this.logIn.asObservable();
  }

  getLogIn=()=>{
    return this.logInAsSubs;
  }
}
