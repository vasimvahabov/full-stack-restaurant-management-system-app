import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog'; 
import { Category } from 'src/app/models/category';
import { OrderedProduct } from 'src/app/models/orderedProduct';
import { Product } from 'src/app/models/product';
import { CategoryService } from 'src/app/services/category.service';
import { OrderedProductService } from 'src/app/services/ordered-product.service';
import { ProductService } from 'src/app/services/product.service'; 

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
    @Inject(MAT_DIALOG_DATA) private orderId:number,
    private prodService:ProductService,private cateService:CategoryService,
    private opService:OrderedProductService,
    private ref:MatDialogRef<AddProductToOrderComponent>){
    this.categoryControl=new FormControl('',Validators.required);
    this.productControl=new FormControl('',Validators.required);
    this.filteredProds=new Array<Product>();
  }

  ngOnInit(){
    this.cateService.getActiveCategories().subscribe(response=>{
      if(response===-1)
        this.ref.close({total:-1});
      else
        this.categories=response;
    }); 
    this.prodService.getActiveProducts().subscribe(response=>{ 
      if(response===-1)
        this.ref.close({total:-1});
      else
        this.products=response; 
    });
    this.opService.getOPsByOrderId(this.orderId).subscribe(response=>{ 
      if(response===-1)
        this.ref.close({total:-1});
      else
        this.orderedProducts=response; 
    });
    this.event=0;
  }

  filterProducts=()=>{ 
    this.productControl.setValue("");
    this.filteredProds=this.products.filter(product=>{
      return product.cateId===parseInt(this.categoryControl.value.toString());
    });
  }

  closeDialog(){
    let total=-1;
    if(this.event!==0){
      total=0;
      this.orderedProducts.forEach(item=>{
        total=parseFloat((total+item.total!).toFixed(2));
      });  
    }
    this.ref.close({total:total});
  }

  addProductToOrder(){
    const prodId=parseInt(this.productControl.value.toString()); 
    const orderedProduct:OrderedProduct={
      orderId:this.orderId,
      prodId:prodId,
      prodTitle:null,
      cateTitle:null,
      prodCount:null,
      total:null
    }; 
    this.opService.addOP(orderedProduct).subscribe(response=>{
      if(response===-1)
        this.ref.close({total:-1});
      else{
        const exists=this.orderedProducts.find(item=> item.prodId===prodId );
        if(exists!==undefined){ 
          const prodPrice=parseFloat((exists.total!/exists.prodCount!).toFixed(2));
          exists.total=parseFloat((exists.total!+prodPrice).toFixed(2));
          exists.prodCount!++;
        }
        else{
          const categoryId:number=parseInt(this.categoryControl.value.toString()); 
          const category=this.categories.find(item=> item.id===categoryId)!; 
          const product=this.products.find(item=>item.id===prodId)!; 
          
          let addedOrderedProduct:OrderedProduct={
            orderId:this.orderId,
            prodId:prodId,
            prodTitle:product.title,
            cateTitle:category.title,
            prodCount:1,
            total:product.price
          };
          this.orderedProducts.push(addedOrderedProduct);
        }
        if(this.event==0)
          this.event=1;
      }
    });
  }

  plusOrderedProduct=(prodId:number)=>{
    const orderedProduct:OrderedProduct={
      orderId:this.orderId,
      prodId:prodId,
      prodTitle:null,
      cateTitle:null,
      prodCount:null,
      total:null
    };
    this.opService.addOP(orderedProduct).subscribe(response=>{
      if(response===-1)
        this.ref.close({total:-1});        
      else{
        this.orderedProducts.map((item)=>{
          if(item.prodId===prodId){
            const prodPrice=parseFloat((item.total!/item.prodCount!).toFixed(2));
            item.total=parseFloat((item.total!+prodPrice).toFixed(2));
            item.prodCount!++; 
          }   
        });
        if(this.event==0)
          this.event=1;
      }
    });
  }

  minusOrderedProduct=(prodId:number)=>{
    const orderedProduct:OrderedProduct={
      orderId:this.orderId,
      prodId:prodId,
      prodTitle:null,
      cateTitle:null,
      prodCount:null,
      total:null
    };
    this.opService.minusOPByOrderIdAndProdId(orderedProduct).subscribe(response=>{
      if(response===-1)
        this.ref.close({total:-1});
      else{
        this.orderedProducts=this.orderedProducts.filter((item)=>{
          if(item.prodId===prodId){
            if(item.prodCount!==1){
              const prodPrice=parseFloat((item.total!/item.prodCount!).toFixed(2));
              item.total=parseFloat((item.total!-prodPrice).toFixed(2));
            };
            item.prodCount!--;
          };
          return item.prodCount!==0; 
        }); 
        if(this.event==0)
          this.event=1;
      }
    });
  }

  deleteOrderedProduct=(prodId:number)=>{
    const orderedProduct:OrderedProduct={
      orderId:this.orderId,
      prodId:prodId,
      prodTitle:null,
      cateTitle:null,
      prodCount:null,
      total:null
    };

    this.opService.deleteOPsByOrderIdAndProdId(orderedProduct).subscribe(response=>{
      if(response===-1)
        this.ref.close({total:-1});
      else{
        this.orderedProducts=this.orderedProducts.filter(item=> item.prodId!==prodId);
        if(this.event==0)
          this.event=1;
      }
    });
  }
}
