import { Component } from '@angular/core';  
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { Admin } from 'src/app/models/admin'; 
import { AdminService } from 'src/app/services/admin.service';
import { AuthService } from 'src/app/services/auth.service';
import { UserLoginComponent } from './user-login/user-login.component';

@Component({
  selector: 'app-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.scss']
})
export class AdminLoginComponent {

  public loginGroup!:FormGroup;
  public msg:string|undefined;

  constructor(private router:Router,private dialog:MatDialog,
                          private adminService:AdminService,private authService:AuthService){
    this.loginGroup=new FormGroup({
      username:new FormControl('',[Validators.required,Validators.maxLength(100),CustomValidators.whiteSpace]),
      password:new FormControl('',[Validators.required,Validators.minLength(8),Validators.maxLength(100)])
    });
  }

  showLoginBtn(){
    if(this.loginGroup.controls['username'].invalid==true || this.loginGroup.controls['password'].invalid==true)
      return true;
    return false;
  }

  onSubmit(){  
    let admin:Admin={
      username:this.loginGroup.value.username. toString(),
      password:this.loginGroup.value.password.toString(),
      id:null
    };  
    this.adminService.logIn(admin).subscribe(response=>{ 
      if(response===null){   
        this.authService.setLogIn();
        this.router.navigateByUrl("/admin"); 
      } 
      else if(response===undefined)
        this.router.navigateByUrl("/error"); 
      else{
        this.loginGroup.controls['username'].setValue("");
        this.loginGroup.controls['password'].setValue("");
        this.msg=response;
      }
        
    }); 
  }

  userLogin(){
    const userLoginDialog=this.dialog.open(UserLoginComponent,{
      disableClose:true
    });
    userLoginDialog.afterClosed().subscribe(response=>{
      if(response.event===1)
        this.router.navigateByUrl("/user");
      if(response.event===-1)
        this.router.navigateByUrl("/error");
    });
  }

}
