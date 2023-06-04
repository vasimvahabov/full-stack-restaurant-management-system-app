import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { OrderedProduct } from 'src/app/models/orderedProduct';
import { AdminService } from 'src/app/services/admin.service';
import { OrderedProductService } from 'src/app/services/ordered-product.service';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss']
})
export class ProductsListComponent {

  public productsList!:OrderedProduct[];

  constructor(
    @Inject(MAT_DIALOG_DATA) private orderId:string,
    private adminService:AdminService,
    private dialogRef:MatDialogRef<ProductsListComponent>,
    private orderedProductService:OrderedProductService){
    // this.orderedProductService.getOrderedProductDTOsByOrderId(this.orderId).subscribe(response=>{
      // this.productsList=response;
    // });
  }

  close(){
    this.dialogRef.close();
  }

}
