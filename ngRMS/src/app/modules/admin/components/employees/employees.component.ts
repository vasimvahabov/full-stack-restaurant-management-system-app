import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog'; 
import { Employee } from '../../models/employee';  
import { EmployeeService } from '../../services/employee.service';
import { AddEmployeeComponent } from './add-employee/add-employee.component';
import { EditEmployeeComponent } from './edit-employee/edit-employee.component';

@Component({
  selector: 'app-emloyees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.scss']
})
export class EmloyeesComponent {

  public emps!:Employee[];

  constructor(private empService:EmployeeService,private dialog:MatDialog){} 

  ngOnInit(){
    this.empService.getAllEmployees().subscribe(response=>{
      if(response!==-1)
        this.emps=response;
    });
  }

  addEmployee(){ 
    const addEmployeeDialog=this.dialog.open(AddEmployeeComponent,{
      height:'625px',
      disableClose:true
    });
    addEmployeeDialog.afterClosed().subscribe((response:any)=>{
      if(response.data!==null){
        const employeeDTO:Employee=response.data;
        this.emps.push(employeeDTO);
      }
    });
  }

  editEmployee(employee:Employee){
    this.dialog.open(EditEmployeeComponent,{
      data:employee,
      height:'625px',
      disableClose:true
    });
  }

  onSlideToogle=(empId:number)=>{ 
    this.empService.changeEmployeeStatus(empId).subscribe();
  }
}
