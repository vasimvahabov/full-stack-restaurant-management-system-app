import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { OrderedProduct } from 'src/app/models/orderedProduct'; 
import { OrderedProductService } from 'src/app/services/ordered-product.service';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss']
})
export class ProductsListComponent {

  public productsList!:OrderedProduct[];

  constructor(
    @Inject(MAT_DIALOG_DATA) private orderId:number,
    private dialogRef:MatDialogRef<ProductsListComponent>,
    private opService:OrderedProductService){
    this.opService.getOPsByOrderId(this.orderId).subscribe(response=>{
      if(response!==-1)
        this.productsList=response;
      else
        this.close();
    });
  }

  close(){
    this.dialogRef.close();
  }

}
