import { formatDate } from '@angular/common';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Order } from 'src/app/models/order';
import { AdminService } from 'src/app/services/admin.service';
import { OrderService } from 'src/app/services/order.service';
import { OrderedProductService } from 'src/app/services/ordered-product.service';
import { ProductsListComponent } from './products-list/products-list.component';

@Component({
  selector: 'app-completed-orders',
  templateUrl: './completed-orders.component.html',
  styleUrls: ['./completed-orders.component.scss']
})
export class CompletedOrdersComponent {
  
  public completedOrders!:Order[];

  constructor(private orderService:OrderService,private adminService:AdminService,
  private dialog:MatDialog,private orderedProductService:OrderedProductService){
    
      // this.orderService.getAllCompletedOrders().subscribe(response=>{
      //   this.completedOrders=response;
      // });
  }

  openProductsList=(orderId:number)=>{
    this.dialog.open(ProductsListComponent,{
      data:orderId,
      height:'228px',
      width:'620px',
      disableClose:true
    });
  }

  getPDF=(userId:number,orderId:number)=>{ 
    this.orderedProductService.getPDF(userId,orderId).subscribe(blob=>{
      const downloadURL=window.URL.createObjectURL(blob);
      const a=document.createElement('a');
      a.href=downloadURL;
      const dateFormat=formatDate(Date.now(),'yyyy_MM_dd_HH_mm_ss','en-US');
      a.download=`bill_${dateFormat}.pdf`
      a.click();
    });
  }
}
