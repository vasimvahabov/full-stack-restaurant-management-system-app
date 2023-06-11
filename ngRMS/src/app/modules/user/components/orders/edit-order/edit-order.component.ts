import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Order } from '../../../../../models/order';
import { OrderService } from '../../../../../services/order.service';
import { CustomValidators } from 'src/app/helpers/customValidators';

@Component({
  selector: 'app-edit-order',
  templateUrl: './edit-order.component.html',
  styleUrls: ['./edit-order.component.scss']
})
export class EditOrderComponent {
  public titleControl!:FormControl;

  constructor(
    @Inject(MAT_DIALOG_DATA) private order:Order, 
    private ref:MatDialogRef<EditOrderComponent>,
    private orderService:OrderService){
    this.titleControl=new FormControl(this.order .title,[
      Validators.required,
      Validators.maxLength(100),
      CustomValidators.onlyWhiteSpace 
    ]);
  }
  
  cancel(){
    this.ref.close();
  }

  onSubmit(){
    const title=this.titleControl.value.toString().trim()
    const order:Order={
      id:this.order.id,
      title:this.titleControl.value.toString().trim(),
      userId:null,
      userFullName:null,
      createdAt:null,
      updatedAt:null,
      total:null
    }; 
    this.orderService.updateOrder(order).subscribe(response=>{
      if(response!==-1)
        this.order.title=title; 
      this.ref.close(); 
    });
  }
}
