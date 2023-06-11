import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ProductService } from '../../../../../services/product.service';
import { CategoryService } from 'src/app/services/category.service'; 
import { CustomValidators } from 'src/app/helpers/customValidators';
import { Product } from '../../../../../models/product';
import { Category } from 'src/app/models/category'; 

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent {
  
  public categories!:Category[];
  public productGroup!:FormGroup;
  public msg:string|undefined;

  constructor(
    private formBuilder:FormBuilder,
    private prodService:ProductService,
    private cateService:CategoryService,
    private ref:MatDialogRef<AddProductComponent>,){
    this.productGroup=this.formBuilder.group({
      title:new FormControl('',[
      Validators.required,
      Validators.maxLength(100),
      CustomValidators.onlyWhiteSpace
      ]),
      price:new FormControl('',[
        Validators.required,
        Validators.min(0.01),
        Validators.max(9999.99),
        Validators.pattern("(^[0-9]{1,4}\\.[0-9]{1,2}$)|(^[0-9]{1,4}$)")
      ]),
      category:new FormControl('',Validators.required)
    });
  }

  ngOnInit(){
    this.cateService.getActiveCategories().subscribe(response=>{
      if(response!==-1)
        this.categories=response;
      else
        this.ref.close({data:null}); 
    });
  }

  cancel(){
    this.ref.close({data:null});
  }

  onSubmit(){
    const cateId=parseInt(this.productGroup.value.category?.toString()!);
    let product:Product={
      id:null,
      title:this.productGroup.value.title?.toString().trim()!,
      price:parseFloat(this.productGroup.value.price?.toString()!),
      status:null,
      cateId:cateId,
      cateTitle:null,
      cateStatus:null
    };
    this.prodService.addProduct(product).subscribe(response=>{ 
      if(response===-1)
        this.ref.close({data:null});
      if(typeof(response)==='string')
        this.msg=response.replace("*","title");
      else{
        product=response;
        const category:Category=this.categories.find( item=>item.id===cateId )!;
        product.cateTitle=category.title;
        product.cateStatus=category.status;
        this.ref.close({data:product});
      }
    });
  }

}
