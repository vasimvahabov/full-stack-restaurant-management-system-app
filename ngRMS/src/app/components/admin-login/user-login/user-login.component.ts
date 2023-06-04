import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog'; 
import { CustomValidators } from 'src/app/helpers/customValidators';
import { User } from 'src/app/modules/admin/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent{

  public loginGroup!:FormGroup;
  public msg:string|undefined;

  constructor(private builder:FormBuilder,private userService:UserService,
     private dialogRef:MatDialogRef<UserLoginComponent>,private authService:AuthService){
    this.loginGroup=this.builder.group({
      username:new FormControl('',[Validators.required,Validators.maxLength(100),CustomValidators.whiteSpace]),
      password:new FormControl('',[Validators.required,Validators.minLength(8),Validators.maxLength(100)])
    })
  }

  close(){
    this.dialogRef.close({event:0});
  }

  showLoginBtn=()=>{
    if(this.loginGroup.controls['username'].invalid===true || this.loginGroup.controls['password'].invalid==true)
      return true;
    return false;
  }

  onSubmit=()=>{
    const user:User={
      id:null,
      username:this.loginGroup.controls['username'].value.toString(),
      password:this.loginGroup.controls['password'].value.toString(),
      firstName:null,
      lastName:null,
      status:null
    };  
    this.userService.logIn(user).subscribe((response:any)=>{
      this.loginGroup.controls['username'].setValue("");
      this.loginGroup.controls['password'].setValue("");

      if(response===null)
        this.dialogRef.close({event:1}) 
      else if(response===undefined)
        this.dialogRef.close({event:-1})
      else
        this.msg=response;
    });
  }
}
