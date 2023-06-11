import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CustomValidators } from 'src/app/helpers/customValidators';
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

  constructor(
    @Inject(MAT_DIALOG_DATA) private position:Position,
    private positionService:PositionService,
    private ref:MatDialogRef<EditPositionComponent>){
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
      if(typeof(response)==='string')
        this.msg=response.replace('*','title');
      else if(response===-1)
        this.ref.close();
      else{
        this.position.title=title;
        this.ref.close();
      }
    });
  }

  cancel=()=>{
    this.ref.close();
  }
}
