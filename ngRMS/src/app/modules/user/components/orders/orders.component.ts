import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog'; 
import { Order } from '../../../../models/order';
import { OrderService } from '../../../../services/order.service';  
import { AddProductToOrderComponent } from './add-product-to-order/add-product-to-order.component';
import { EditOrderComponent } from './edit-order/edit-order.component';
import { AddOrderComponent } from './add-order/add-order.component';
import { OrderedProductService } from 'src/app/services/ordered-product.service'; 
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent {
  public orders!:Order[]; 

  constructor(private orderService:OrderService,private dialog:MatDialog,
    private orderedProductService:OrderedProductService,public userService:UserService){}

  ngOnInit(){ 
    this.getOrders();
  }

  getOrders(){   
    // this.orderService.getInCompleteOrder().subscribe(response=>{
      // this.orders=re/sponse;
    // }); 
  }

  addOrder(){
    const addOrderDialog=this.dialog.open(AddOrderComponent,{
      height:'270px',
      disableClose:true, 
    }); 
    addOrderDialog.afterClosed().subscribe((response)=>{
      console.log(response.data);
      if(response.data!==null){ 
        const order:Order=response.data;
        this.orders.push(order);
      }
    })
  }

  editOrder(order:Order){ 
    this.dialog.open(EditOrderComponent,{
      data:order,
      height:'270px',
      disableClose:true 
    }); 
  }

  deleteOrder(orderId:number){
    // this.orderService.deleteOrder(orderId).subscribe(()=>{
    //   this.orders=this.orders.filter((item)=>{
    //     return item.id!==orderId;
    //   });
    // });
  }

  openOrderedProductsList(orderId:number){
    const orderedProductListDialog=this.dialog.open(AddProductToOrderComponent,{
      data:orderId,
      width:'850px',
      disableClose:true 
    });
    orderedProductListDialog.afterClosed().subscribe((response)=>{
      if(response.total!==-1){
        const total=response.total;
        this.orders.forEach((item)=>{
          if(item.id===orderId)
           item.total=total;
        });
      }
    });
  }

  completeOrder=(orderId:number)=>{
    // this.orderService.completeOrder(orderId).subscribe(()=>{
    //   this.orders=this.orders.filter((item)=>{
    //     return item.id!==orderId;
    //   })
    // });
  }  

  getPDF=(orderId:number)=>{  
    // this.orderedProductService.getPDF(this.userService.userId,orderId,this.userService.authDetails).subscribe((blob)=>{
    //   const downloadURL = window.URL.createObjectURL(blob);
    //   const a = document.createElement('a');
    //   a.href = downloadURL;
    //   const dateFormat:string=formatDate(Date.now(),'yyyy_MM_dd_HH_mm_ss',"en-US");
    //   a.download=`bill_${dateFormat}.pdf`;
    //   a.click();
    //  });
  }

}