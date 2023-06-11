import { formatDate } from '@angular/common';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Order } from 'src/app/models/order'; 
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

  constructor(
    private orderService:OrderService,
    private dialog:MatDialog,
    private opService:OrderedProductService){
    this.orderService.getCompletedOrders().subscribe(response=>{ 
      if(response!==-1)
        this.completedOrders=response;
    });
  }

  openProductsList=(orderId:number)=>{
    this.dialog.open(ProductsListComponent,{
      data:orderId,
      height:'228px',
      width:'620px',
      disableClose:true
    });
  }

  getPDF=(orderId:number)=>{ 
    this.opService.getPDF(orderId).subscribe(response=>{
      if(response!==-1){
        const utf_8=atob(response);
        const bytes=new Uint8Array(utf_8.length);
        for(let i=0; i<utf_8.length; i++)
          bytes[i]=(utf_8.charCodeAt(i));
        const blob=new Blob([bytes]);
        
        const downloadURL=window.URL.createObjectURL(blob);
        const a=document.createElement('a');
        a.href=downloadURL;
        const dateFormat=formatDate(Date.now(),'yyyy_MM_dd_HH_mm_ss','en-US');
        a.download=`bill_${dateFormat}.pdf`
        a.click();
      }
    });
  }
}
