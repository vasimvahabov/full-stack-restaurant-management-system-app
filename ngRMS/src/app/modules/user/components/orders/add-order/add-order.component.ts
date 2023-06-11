import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { OrderService } from '../../../../../services/order.service';
import { Order } from 'src/app/models/order';

@Component({
  selector: 'app-add-order',
  templateUrl: './add-order.component.html',
  styleUrls: ['./add-order.component.scss']
})
export class AddOrderComponent {
  public titleControl!:FormControl;
  public msg:string|undefined;

  constructor(
    private ref:MatDialogRef<AddOrderComponent>,
    private orderService:OrderService){
    this.titleControl=new FormControl('',[
      Validators.required,
      Validators.maxLength(100),
      CustomValidators.onlyWhiteSpace
    ]);
  }

  cancel(){
    this.ref.close({data:null});
  }

  onSubmit(){ 
    const order:Order={
      id:null,
      title:this.titleControl.value.toString().trim(),
      userId:null,
      userFullName:null,
      createdAt:null,
      updatedAt:null,
      total:null
    }; 
    this.orderService.addOrder(order).subscribe((response)=>{
      if(response===-1)
        this.ref.close({data:null}); 
      else{
        const order=response;
        this.ref.close({data:order});  
      }
    });
  }
}
