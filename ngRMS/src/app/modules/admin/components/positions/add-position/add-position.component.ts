import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
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
    private dialogRef:MatDialogRef<AddPositionComponent>,
    private router:Router
  ){}

  ngOnInit(){
    this.titleControl=new FormControl('',[
      Validators.required,
      Validators.maxLength(100),
      CustomValidators.onlyWhiteSpace
    ]);
  }

  onSubmit=()=>{
    const title:string=this.titleControl.value.toString().trim();
    let position:Position={ id:null, title:title, status:null };
    console.log(position);

    this.positionService.addPosition(position).subscribe((response)=>{ 
      if(typeof(response)==='string'){
        const msg=response.replace('*','title'); 
        this.msg=msg; 
      } 
      else if(response===undefined){
        this.router.navigateByUrl('/error');
        this.dialogRef.close({data:null})
      }
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
