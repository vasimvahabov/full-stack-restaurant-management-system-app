import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { CustomValidators } from 'src/app/helpers/customValidators'; 
import { Position } from '../../../models/position';
import { PositionService } from '../../../services/position.service';

@Component({
  selector: 'app-add-position',
  templateUrl: './add-position.component.html',
  styleUrls: ['./add-position.component.scss']
})
export class AddPositionComponent {

  public titleControl!:FormControl;
  public msg!:string|undefined;

  constructor(
    private positionService:PositionService,
    private dialogRef:MatDialogRef<AddPositionComponent>){
    this.titleControl=new FormControl('',[
      Validators.required,
      Validators.maxLength(100),
      CustomValidators.onlyWhiteSpace
    ]);  
  }

  onSubmit=()=>{
    const title:string=this.titleControl.value.toString().trim();
    let position:Position={ id:null, title:title, status:null };

    this.positionService.addPosition(position).subscribe((response)=>{ 
      if(typeof(response)==='string')
        this.msg=response.replace('*','title');
      else if(response===-1)
        this.dialogRef.close({data:null})
      else{
        position=response;
        this.dialogRef.close({data:position});
      }
    });
  }

  cancel=()=>{
    this.dialogRef.close({data:null});
  }
}
