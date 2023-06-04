import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { UserService } from 'src/app/services/user.service';
import { OrderService } from '../../../../../services/order.service';
import { Order } from 'src/app/models/order';
import { title } from 'process';

@Component({
  selector: 'app-add-order',
  templateUrl: './add-order.component.html',
  styleUrls: ['./add-order.component.scss']
})
export class AddOrderComponent {
  public titleControl!:FormControl;

  constructor(private ref:MatDialogRef<AddOrderComponent>,
  private orderService:OrderService,private userService:UserService){
    this.titleControl=new FormControl('',[Validators.required,Validators.maxLength(100),CustomValidators.onlyWhiteSpace]);
  }

  cancel(){
    this.ref.close({data:null});
  }

  onSubmit(){ 
    const order:Order={
      id:null,
      title:this.titleControl.value.toString().trim(),
      userId:-1,
      userFullname:null,
      createdAt:null,
      updatedAt:null,
      total:null
    };
    console.log(order);
    // this.orderService.addOrder(order).subscribe((response)=>{
      // const order=response;
      // this.ref.close({data:order});  
    // });
  }
}
