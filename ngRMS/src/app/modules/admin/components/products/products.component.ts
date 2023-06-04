import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Product } from '../../../../models/product';
import { ProductService } from '../../../../services/product.service';
import { EditProductComponent } from './edit-product/edit-product.component';
import { AddProductComponent } from './add-product/add-product.component';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-meals',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent {

  public products!:Product[];

  constructor(
    private productService:ProductService,private dialog:MatDialog,private adminService:AdminService){
    // this.productService.getAllProducts(this.adminService.token).subscribe(response=>{
      // this.products=response;
    // });
  }

  onSlideToogle(prodId:number){ 
    // this.productService.changeProductStatus(this.adminService.token,prodId).subscribe();
  }

  editProduct(product:Product){
    this.dialog.open(EditProductComponent,{
      data:product,
      height:'450px',
      disableClose:true
    });
  }

  addProduct(){ 
    const addProductDialog=this.dialog.open(AddProductComponent,{
      height:'450px',
      disableClose:true
    });
    addProductDialog.afterClosed().subscribe((response:any)=>{ 
      if(response.data!==null){ 
        const product:Product=response.data;
        this.products.push(product);
      }
    });
  }
}
