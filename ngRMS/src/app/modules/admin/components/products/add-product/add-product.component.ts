import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ProductService } from '../../../../../services/product.service';
import { CategoryService } from 'src/app/services/category.service'; 
import { CustomValidators } from 'src/app/helpers/customValidators';
import { Product } from '../../../../../models/product';
import { Category } from 'src/app/models/category';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent {
  
  public categories!:Category[];
  public productGroup=this.formBuilder.group({
    name:new FormControl('',[Validators.required,Validators.maxLength(100),CustomValidators.onlyWhiteSpace]),
    price:new FormControl('',[Validators.required,Validators.min(0.01),Validators.max(9999.99),Validators.pattern("(^[0-9]{1,4}\\.[0-9]{1,2}$)|(^[0-9]{1,4}$)")]),
    category:new FormControl('',Validators.required)
  });

  constructor(
    private formBuilder:FormBuilder,
    private ref:MatDialogRef<AddProductComponent>,
    private prodService:ProductService,
    private adminService:AdminService,
    private cateService:CategoryService){
    // this.cateService.getActiveCategories(this.adminService.token).subscribe(response=>{
    //   this.categories=response; 
    //   console.log(this.categories);
    // });
  }

  cancel(){
    this.ref.close({data:null});
  }

  onSubmit(){
    const cateId=parseInt(this.productGroup.value.category?.toString()!);
    let product:Product={
      id:null,
      name:this.productGroup.value.name?.toString().trim()!,
      price:parseFloat(this.productGroup.value.price?.toString()!),
      status:null,
      cateId:cateId,
      cateName:null,
      cateStatus:null
    };
    // this.prodService.addProduct(this.adminService.token,product).subscribe(response=>{ 
    //   product=response;
    //   const category:Category=this.categories.find( item=>item.id===cateId )!;
    //   product.cateName=category.name;
    //   product.cateStatus=category.status;
    //   this.ref.close({data:product});
    // });
  }

}
