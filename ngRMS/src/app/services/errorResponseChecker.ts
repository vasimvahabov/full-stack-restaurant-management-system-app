import { Injectable } from "@angular/core";
import { ErrorResponse } from "../models/errorResponse";
import { AuthService } from "./auth.service";

@Injectable({
  providedIn:'root'
})
export class  ErrorResponseService{

  constructor (private authService:AuthService){}
  
  check=(response:any):any=>{
    if(response!==null){
      if(response.errorResponse!==undefined){ 
        const errorResponse:ErrorResponse=response.errorResponse;
        if(errorResponse.statusCode===404 || errorResponse.statusCode===409)
          return errorResponse.msg;
        else
         return this.authService.setErrorResponse(errorResponse); 
      }   
      return response; 
    }
    return null;
  }
}