import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { AdminService } from 'src/app/services/admin.service';
import { Position } from '../../../models/position';
import { PositionService } from '../../../services/position.service';

@Component({
  selector: 'app-edit-position',
  templateUrl: './edit-position.component.html',
  styleUrls: ['./edit-position.component.scss']
})
export class EditPositionComponent {
  public titleControl!:FormControl;
  public msg:string|undefined;

  constructor(@Inject(MAT_DIALOG_DATA) private position:Position,private router:Router,
    private positionService:PositionService,private dialogRef:MatDialogRef<EditPositionComponent>){}

  ngOnInit(){ 
    this.titleControl=new FormControl(this.position.title,[
      Validators.required,
      Validators.maxLength(100),
      CustomValidators.onlyWhiteSpace
    ]);
  }

  onSubmit=()=>{
    const title=this.titleControl.value.toString().trim(); 
    const position:Position={ id:this.position.id, title:title, status:null };

    this.positionService.updatePosition(position).subscribe(response=>{
      if(typeof(response)==='string'){
        const msg=response.replace('*','title'); 
        this.msg=msg; 
      } 
      else if(response===undefined){
        this.router.navigateByUrl('/error');
        this.dialogRef.close();
      }
      else{
        this.position.title=title;
        this.dialogRef.close();
      }
    });
  }

  cancel=()=>{
    this.dialogRef.close();
  }
}
