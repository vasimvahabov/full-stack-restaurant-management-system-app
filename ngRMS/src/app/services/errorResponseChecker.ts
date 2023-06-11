import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { ErrorResponse } from "../models/errorResponse";
import { AuthService } from "./auth.service";

@Injectable({
  providedIn:'root'
})
export class  ErrorResponseService{

  constructor (private authService:AuthService,private router:Router){}
  
  check=(response:any):any=>{
    if(response!==null){
      if(response.errorResponse!==undefined){ 
        const errorResponse:ErrorResponse=response.errorResponse;
        if(errorResponse.statusCode===404 || errorResponse.statusCode===409)
          return errorResponse.msg;
        else{
          this.authService.setErrorResponse(errorResponse);
          this.router.navigateByUrl("/error");
          return -1;
        }    
      }   
      else 
        return response; 
    }
    else 
      return 0; 
  }
}