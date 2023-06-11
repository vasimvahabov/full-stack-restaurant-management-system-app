import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { Employee } from '../../../models/employee';
import { Position } from '../../../models/position';
import { EmployeeService } from '../../../services/employee.service';
import { PositionService } from '../../../services/position.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss']
})
export class AddEmployeeComponent {
  public positions!:Position[];
  public empGroup!:FormGroup; 
  public emailMsg:string|undefined;
  public phoneNumberMsg:string|undefined;

  constructor(
    private empService:EmployeeService,
    private posService:PositionService,
    private ref:MatDialogRef<AddEmployeeComponent>){
    this.empGroup=new FormGroup({
      firstName:new FormControl('',[
        Validators.required,
        Validators.maxLength(50),
        CustomValidators.whiteSpace
      ]),
      lastName:new FormControl('',[
        Validators.required,
        Validators.maxLength(50),
        CustomValidators.whiteSpace
      ]),
      email:new FormControl('',[
        Validators.required,
        Validators.email,
        Validators.maxLength(100)
      ]), 
      phoneNumber:new FormControl('',[
        Validators.required,
        Validators.pattern("^[0-9]{1,12}$")
      ]),
      position:new FormControl('',[Validators.required]),
    });
  }

  ngOnInit(){
    this.posService.getActivePositions().subscribe((response)=>{
      if(response!==-1)
        this.positions=response;
      else 
        this.ref.close({data:null});
    });
  }

  cancel=()=>{
    this.ref.close({data:null});
  }

  onSubmit(){ 
    const posId=parseInt(this.empGroup.value.position.toString());
    let employee:Employee={ 
      id:null,
      firstName:this.empGroup.value.firstName.toString(),
      lastName:this.empGroup.value.lastName.toString(),
      email:this.empGroup.value.email.toString(),
      phone:this.empGroup.value.phoneNumber.toString(),
      status:null,
      posId:posId,
      posTitle:null,
      posStatus:null
    };    
    this.empService.addEmployee(employee).subscribe(response=>{ 
      if(typeof(response)==='string'){
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
      else if(response===-1)
        this.ref.close({data:null}); 
      else{
        employee=response;
        employee.phone=`+994${employee.phone}`;
        
        const position:Position=this.positions.find( item=> item.id===posId )!;
        employee.posTitle=position.title;
        employee.posStatus=position.status; 
        this.ref.close({data:employee});
      }
    });
  } 
}
