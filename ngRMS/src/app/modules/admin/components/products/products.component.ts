import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Product } from '../../../../models/product';
import { ProductService } from '../../../../services/product.service';
import { EditProductComponent } from './edit-product/edit-product.component';
import { AddProductComponent } from './add-product/add-product.component';

@Component({
  selector: 'app-meals',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent {

  public products!:Product[];

  constructor(private productService:ProductService,private dialog:MatDialog){}

  ngOnInit(){
    this.productService.getAllProducts().subscribe(response=>{
      if(response!==-1)
        this.products=response;
    });
  }

  onSlideToogle(prodId:number){ 
    this.productService.changeProductStatus(prodId).subscribe();
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
    addProductDialog.afterClosed().subscribe(response=>{ 
      if(response.data!==null){ 
        const product:Product=response.data;
        this.products.push(product);
      }
    });
  }
}
