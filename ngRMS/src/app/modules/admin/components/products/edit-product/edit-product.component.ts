import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Product } from '../../../../../models/product';
import { ProductService } from '../../../../../services/product.service';
import { CategoryService } from 'src/app/services/category.service'; 
import { CustomValidators } from 'src/app/helpers/customValidators';
import { Category } from 'src/app/models/category';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent {
  public categories!:Category[];
  public productGroup=this.formBuilder.group({
    name:new FormControl(this.product.name,[Validators.required,Validators.maxLength(100),CustomValidators.onlyWhiteSpace]),
    price:new FormControl(this.product.price.toFixed(2),[Validators.required,Validators.min(0.01),Validators.max(9999.99),Validators.pattern("(^[0-9]{1,4}\\.[0-9]{1,2}$)|(^[0-9]{1,4}$)")]),
    category:new FormControl(this.product.cateId,Validators.required)
  });

  constructor(
    private formBuilder:FormBuilder,
    private ref:MatDialogRef<EditProductComponent>,
    private prodService:ProductService,
    private cateService:CategoryService,
    private adminService:AdminService,
    @Inject(MAT_DIALOG_DATA) private product:Product){
    // this.cateService.getActiveCategories(this.adminService.token).subscribe(response=>{
      // this.categories=response; 
      // console.log(this.product);
    // })   
  }

  cancel(){
    this.ref.close();
  }

  onSubmit(){ 
    const cateId=parseInt(this.productGroup.value.category?.toString()!);
    let product:Product={
      id:this.product.id,
      name:this.productGroup.value.name?.toString().trim()!,
      price:parseFloat(this.productGroup.value.price?.toString()!),
      status:null,
      cateId:cateId,
      cateName:null,
      cateStatus:null
    };
    
    // this.prodService.updateProduct(this.adminService.token,product).subscribe(()=>{
    //   this.product.name=product.name;
    //   this.product.price=product.price;
    //   const category:Category=this.categories.find( item=>item.id===cateId )!;
    //   this.product.cateName=category.name;
    //   this.product.cateStatus=category.status; 
    //   this.ref.close();
    // });
  }

}

