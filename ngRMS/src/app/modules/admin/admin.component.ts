import { Component } from '@angular/core';
import { Router } from '@angular/router';  
import { AdminService } from 'src/app/services/admin.service'; 
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent { 

  private intervalId!:NodeJS.Timer;
   
  constructor(private router:Router,private adminService:AdminService,private authService:AuthService){
    this.authService.getLogIn().subscribe((response:number)=>{
      if(response===0){ 
        this.adminService.refreshToken().subscribe(response=>{
          if(response===undefined)
            this.router.navigateByUrl("/error");
        });
      }
      else
        this.authService.initLogIn();
    });
  }

  ngOnInit(){
    this.intervalId=setInterval(()=>{
      this.adminService.refreshToken().subscribe(response=>{
        if(response===undefined)
          this.router.navigateByUrl("/error");
      });
    },900000);  
  }

  ngOnDestroy(){
    clearInterval(this.intervalId);
  }

  logOut(){
    this.adminService.logOut().subscribe((response:any)=>{
      if(response===null)
        this.router.navigateByUrl(''); 
      else if(response===undefined)
        this.router.navigateByUrl("/error")
    });
  }
}
