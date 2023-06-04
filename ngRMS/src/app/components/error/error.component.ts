import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Console } from 'console';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent {
  public statusCode!:number;
  public msg!:string;

  public constructor(private authService:AuthService,private router:Router){
    this.authService.getErrorResponse().subscribe(response=>{ 
      // console.log(response);
      if(response===null){
        this.router.navigateByUrl(""); 
      }
      else{
        this.statusCode=response.statusCode;
        this.msg=response.msg;
        // this.authService.initErrorResponse();
      }
    });
  } 
}
