import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { Admin } from 'src/app/models/admin'; 
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent {

  public adminGroup!:FormGroup;
  private admin!:Admin;
  public showResetInputs!:boolean;

  constructor(private builder:FormBuilder,private adminService:AdminService){ 
    this.showResetInputs=false;
  }

  ngOnInit(){
    this.adminService.getAdmin().subscribe(response=>{ 
      if(response!==-1){
        this.admin=response;
        this.adminGroup=this.builder.group({
          username:new FormControl(this.admin.username,[  
            Validators.required,
            Validators.maxLength(100),
            CustomValidators.whiteSpace
          ]),
          password:new FormControl('',[
            Validators.required,
            Validators.minLength(8),
            Validators.maxLength(100)
          ]),
          confirmPassword:new FormControl('',[
            Validators.required,
            Validators.minLength(8),
            Validators.maxLength(100)
          ])
        });
      }
    }); 
  }

  showSaveBtn(){
    if(this.showResetInputs===true)
      if(this.adminGroup.controls['password'].value?.toString()!== 
                                                      this.adminGroup.controls['confirmPassword'].value?.toString() || 
        this.adminGroup.controls['password'].invalid===true || 
        this.adminGroup.controls['confirmPassword'].invalid===true)
        return true;
    if(this.adminGroup.controls['username'].invalid===true)
      return true;
    return false;
  }

  onSubmit(){ 
    let admin:Admin={
      id:this.admin.id,
      username:this.adminGroup.controls['username'].value.toString(),
      password:null
    };
    if(this.showResetInputs===true){
      const password=this.adminGroup.controls['password'].value.toString(); 
      admin.password=password;
    };  
    this.adminService.updateAdmin(admin).subscribe((response)=>{
      if(response===0){
        this.admin.username=admin.username;
        this.showResetInputs=false;
      }
    });
  }

  showResetInputsClicked=()=>{
    this.showResetInputs=!this.showResetInputs;
    this.adminGroup.controls['password'].reset();
    this.adminGroup.controls['confirmPassword'].reset();
  }

}
