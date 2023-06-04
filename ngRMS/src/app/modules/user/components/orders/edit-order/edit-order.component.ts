import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Order } from '../../../../../models/order';
import { OrderService } from '../../../../../services/order.service';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-order',
  templateUrl: './edit-order.component.html',
  styleUrls: ['./edit-order.component.scss']
})
export class EditOrderComponent {
  public titleControl!:FormControl;

  constructor(@Inject(MAT_DIALOG_DATA) private order:Order,
              private userService:UserService,
              private ref:MatDialogRef<EditOrderComponent>,private orderService:OrderService){
     this.titleControl=new FormControl(this.order .title,[Validators.required,Validators.maxLength(100),CustomValidators.onlyWhiteSpace]);
  }
  
  cancel(){
    this.ref.close();
  }

  onSubmit(){
    const title=this.titleControl.value.toString().trim()
    const order:Order={
      id:this.order.id,
      title:this.titleControl.value.toString().trim(),
      userId:-1,
      userFullname:null,
      createdAt:null,
      updatedAt:null,
      total:null
    };
    console.log(order);
    // this.orderService.updateOrder(order).subscribe(()=>{
    //   this.order.title=title;
    //   this.ref.close();
    // });
  }
}
