import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog'; 
import { Category } from 'src/app/models/category';
import { OrderedProduct } from 'src/app/models/orderedProduct';
import { Product } from 'src/app/models/product';
import { CategoryService } from 'src/app/services/category.service';
import { OrderedProductService } from 'src/app/services/ordered-product.service';
import { ProductService } from 'src/app/services/product.service'; 
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-product-to-order',
  templateUrl: './add-product-to-order.component.html',
  styleUrls: ['./add-product-to-order.component.scss']
})

export class AddProductToOrderComponent { 
  public categories!:Category[];
  public products!:Product[];
  public filteredProds!:Product[];
  public categoryControl!:FormControl;
  public productControl!:FormControl;
  public orderedProducts!:OrderedProduct[];
  private event!:number;

  constructor(
    @Inject(MAT_DIALOG_DATA) private orderId:string,
    private prodService:ProductService,private cateService:CategoryService,
    private orderedProductService:OrderedProductService,
    private userService:UserService,
    private ref:MatDialogRef<AddProductToOrderComponent>
  ){
    this.categoryControl=new FormControl('',Validators.required);
    this.productControl=new FormControl('',Validators.required);
    this.filteredProds=new Array<Product>();
  }

  ngOnInit(){
    // this.cateService.getActiveCategories().subscribe(response=>{
    //   this.categories=response; 
    // }); 
    // this.prodService.getActiveProducts().subscribe(response=>{
    //   this.products=response; 
    // });
    // this.orderedProductService.getOrderedProductsOrderId(this.orderId,this.userService.authDetails).subscribe(response=>{
    //   this.orderedProducts=response; 
    // });
    this.event=0;
  }

  filterProducts=()=>{ 
    this.productControl.setValue("");
    // this.filteredProds=this.products.filter(product=>{
    //   return product.categoryDTO._id===this.categoryControl.value.toString();
    // });
  }

  closeDialog(){
    let total=-1;
    if(this.event!==0){
      total=0;
      this.orderedProducts.forEach(item=>{
        // total=parseFloat((total+item.total).toFixed(2));
      });  
    }
    this.ref.close({total:total});
  }

  addProductToOrder(){
    const prodId=this.productControl.value.toString();
    let orderedProductDetails={
      'orderId':this.orderId,
      'prodId':prodId
    };

    this.orderedProductService.addOrderedProduct(orderedProductDetails).subscribe(()=>{
      const exists=this.orderedProducts.find(item=> item.prodId===prodId );
      if(exists!==undefined){ 
        const prodPrice=parseFloat((exists.total/exists.prodCount).toFixed(2));
        exists.total=parseFloat((exists.total+prodPrice).toFixed(2));
        exists.prodCount++;
      }
      else{
        const categoryId:number=this.categoryControl.value.toString(); 
        const categoryDTO=this.categories.find(item=> item.id===categoryId); 
        const productDTO=this.products.find(item=>item.id===prodId); 
        
        // let addedOrderedProduct=new OrderedProduct(
        //   this.orderId,prodId,productDTO?.name,categoryDTO?.name,1,productDTO?.price
        // ); 
        // this.orderedProducts.push(addedOrderedProduct);
      }
      if(this.event==0)
        this.event=1;
    });
  }

  plusOrderedProduct=(prodId:string)=>{
    let orderedProductDetails={
      'orderId':this.orderId,
      'prodId':prodId
    };

    this.orderedProductService.addOrderedProduct(orderedProductDetails).subscribe(()=>{
      this.orderedProducts.map((item)=>{
        if(item.prodId===prodId){
          const prodPrice=parseFloat((item.total/item.prodCount).toFixed(2));
          item.total=parseFloat((item.total+prodPrice).toFixed(2));
          item.prodCount++; 
        }   
      });
      if(this.event==0)
        this.event=1;
    });
  }

  minusOrderedProduct=(prodId:string)=>{
    let orderedProductDetails={
      'orderId':this.orderId,
      'prodId':prodId
    };

    this.orderedProductService.minusOrderedProductByOrderIdAndProdId(orderedProductDetails).subscribe(()=>{
      this.orderedProducts=this.orderedProducts.filter((item)=>{
        if(item.prodId===prodId){
          if(item.prodCount!==1){
            const prodPrice=parseFloat((item.total/item.prodCount).toFixed(2));
            item.total=parseFloat((item.total-prodPrice).toFixed(2));
          };
          item.prodCount--;
        };
        return item.prodCount!==0; 
      }); 
      if(this.event==0)
        this.event=1;
    });
  }

  deleteOrderedProduct=(prodId:string)=>{
    let orderedProductDetails={
      'orderId':this.orderId,
      'prodId':prodId
    };

    this.orderedProductService.deleteOrderedProductsByOrderIdAndProdId(orderedProductDetails).subscribe(()=>{
      this.orderedProducts=this.orderedProducts.filter(item=> item.prodId!==prodId);
      if(this.event==0)
        this.event=1;
    });
  }
}
