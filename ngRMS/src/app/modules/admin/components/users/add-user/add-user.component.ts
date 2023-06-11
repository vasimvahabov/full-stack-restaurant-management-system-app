import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms'; 
import { MatDialogRef } from '@angular/material/dialog';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { UserService } from 'src/app/services/user.service';
import { User } from '../../../models/user';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent {
  public showResetInputs:boolean=false;
  public showValidationErrors:boolean=false;
  public userGroup!:FormGroup;
  public msg:string|undefined; 

  constructor(
    private userService:UserService,
    private dialogRef:MatDialogRef<AddUserComponent>){
    this.userGroup=new FormGroup({
      firstName:new FormControl('',[
        Validators.required,Validators.maxLength(50),
        CustomValidators.whiteSpace
      ]),
      lastName:new FormControl('',[
        Validators.required,
        Validators.maxLength(50),
        CustomValidators.whiteSpace
      ]),
      username:new FormControl('',[
        Validators.required,
        Validators.maxLength(100),
        CustomValidators.whiteSpace
      ]),
      password:new FormControl('',[
        Validators.minLength(8),
        Validators.required,
        Validators.maxLength(100)
      ]),
      confirmPassword:new FormControl('',[
        Validators.minLength(8),
        Validators.required,
        Validators.maxLength(100)
      ])
    }); 
  }

  cancel(){
    this.dialogRef.close({data:null});
  }

  showResetInputClicked(){
    this.showResetInputs=!this.showResetInputs;
  }

  onSubmit(form:FormGroup){   
    let user:User={
      id:null,
      username:form.value.username.toString(),
      firstName:form.value.firstName.toString(),
      lastName:form.value.lastName.toString(),
      password:form.value.password.toString(),
      status:null
    };       
    this.userService.addUser(user).subscribe((response)=>{
      if(response===-1)
        this.dialogRef.close({data:null});
      else if(typeof(response)==='string'){
        this.msg=response.replace('*','username');
        this.userGroup.controls["password"].setValue("");
        this.userGroup.controls["confirmPassword"].setValue(""); 
      }
      else{
        user=response;  
        this.dialogRef.close({data:user});
      }
    });
  }
}
