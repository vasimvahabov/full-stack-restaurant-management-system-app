import { Component, Inject } from '@angular/core';
import {  FormControl,Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CustomValidators } from 'src/app/helpers/customValidators'; 
import { UserService } from 'src/app/services/user.service';
import { User } from '../../../models/user';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent {

  public showResetInputs:boolean=false; 
  public msg:string|undefined;

  public validationFirstName!:FormControl;
  public validationLastName!:FormControl;
  public validationUsername!:FormControl;
  public validationPassword!:FormControl;
  public validationConfirmPassword!:FormControl;

  constructor(private dialogRef:MatDialogRef<EditUserComponent>,private router:Router,
             @Inject(MAT_DIALOG_DATA) public user:User,private userService:UserService){
    this.validationFirstName=new FormControl(this.user.firstName,[
      Validators.required,
      CustomValidators.whiteSpace
    ]);
    this.validationLastName=new FormControl(this.user.lastName,[
      Validators.required,
      Validators.maxLength(50),
      CustomValidators.whiteSpace
    ]);
    this.validationUsername=new FormControl(this.user.username,[
      Validators.required,
      Validators.maxLength(100),
      CustomValidators.whiteSpace
    ]);
    this.validationPassword=new FormControl('',[
      Validators.minLength(8),
      Validators.maxLength(100),
      Validators.required
    ]);
    this.validationConfirmPassword=new FormControl('',[
      Validators.minLength(8),
      Validators.maxLength(100),
      Validators.required
    ]);
  }
  
  cancel(){
    this.dialogRef.close();
  }

  showSaveBtn(){
    if(this.validationUsername.invalid===true ||
            this.validationFirstName.invalid===true || this.validationLastName.invalid===true)
      return true;    
    if(this.showResetInputs==true)
      if(this.validationPassword.value?.toString()!==this.validationConfirmPassword.value?.toString()
            || this.validationPassword.invalid===true || this.validationConfirmPassword.invalid===true)
        return true;
    return false;
  }

  showResetInputClicked(){
    this.showResetInputs=!this.showResetInputs;
  }

  onSubmit(){ 
    let user:User={
      id:this.user.id,
      firstName:this.validationFirstName.value?.toString()!,
      lastName:this.validationLastName.value?.toString()!,
      username:this.validationUsername.value?.toString()!,
      status:null,
      password:null
    };
    if(this.showResetInputs===true)
      user.password=this.validationPassword.value?.toString()!;
    this.userService.updateUser(user).subscribe(response=>{
      if(response===undefined){
        this.router.navigateByUrl("/error");
        this.dialogRef.close();
      }
      else if(typeof(response)==='string'){       
        const msg=response.replace('*','username');
        this.msg=msg;
        if(this.showResetInputs){
          this.validationPassword.setValue("");
          this.validationConfirmPassword.setValue(""); 
        }
      }
      else{
        this.user.firstName=user.firstName;
        this.user.lastName=user.lastName;
        this.user.username=user.username;
        this.dialogRef.close();
      }
    });
  }
}
