import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CustomValidators } from 'src/app/helpers/customValidators'; 
import { Employee } from '../../../models/employee';  
import { Position } from '../../../models/position';
import { EmployeeService } from '../../../services/employee.service';
import { PositionService } from '../../../services/position.service';

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.scss']
})
export class EditEmployeeComponent {

  public positions!:Position[];
  public emailMsg:string|undefined;
  public phoneNumberMsg:string|undefined;
  public empGroup:FormGroup;

  constructor(private ref:MatDialogRef<EditEmployeeComponent>,
      private posService:PositionService,private empService:EmployeeService,
       private router:Router,@Inject(MAT_DIALOG_DATA) public employee:Employee){
    this.empGroup=new FormGroup({
      firstName:new FormControl(this.employee.firstName,[
        Validators.required,
        Validators.maxLength(50),
        CustomValidators.whiteSpace
      ]),
      lastName:new FormControl(this.employee.lastName,[
        Validators.required,
        Validators.maxLength(50),
        CustomValidators.whiteSpace
      ]),
      email:new FormControl(this.employee.email,[
        Validators.required,
        Validators.email,
        Validators.maxLength(100)
      ]),
      phoneNumber:new FormControl(this.employee.phone.slice(4),[
        Validators.required,
        Validators.pattern("^[0-9]{1,12}$")
      ]),
      position:new FormControl(this.employee.posId.toString(),[
        Validators.required
      ])
    });
    this.posService.getActivePositions().subscribe(response=>{
      if(response===undefined)
        this.router.navigateByUrl("/error");
      else 
        this.positions=response; 
    });
  } 

  cancel=()=>{
    this.ref.close();
  }

  onSubmit(){
    const posId=parseInt(this.empGroup.value.position.toString());
    let employee:Employee={ 
      id:this.employee.id,
      firstName:this.empGroup.value.firstName.toString(),
      lastName:this.empGroup.value.lastName.toString(),
      email:this.empGroup.value.email.toString(),
      phone:this.empGroup.value.phoneNumber.toString(),
      status:null,
      posId:posId,
      posTitle:null,
      posStatus:null
    };     
    console.log(employee);
    this.empService.updateEmployee(employee).subscribe(response=>{
      if(response===undefined)
        this.router.navigateByUrl("/error");
      else if(typeof(response)==='string'){
        const msg=response; 
        if(msg.includes("email")){
          this.emailMsg=msg;
          if(this.phoneNumberMsg)
            this.phoneNumberMsg=undefined;
        }
        else{
          this.phoneNumberMsg=msg;
          if(this.emailMsg)
            this.emailMsg=undefined;
        }
      }
      else{
        this.employee.firstName=employee.firstName;
        this.employee.lastName=employee.lastName;
        this.employee.email=employee.email;
        this.employee.phone=`+994${employee.phone}`

        const position:Position=this.positions.find( item=> item.id===posId )!;
        this.employee.posTitle=position.title;
        this.employee.posStatus=position.status; 
        this.ref.close();
      }
    });
  }
}
