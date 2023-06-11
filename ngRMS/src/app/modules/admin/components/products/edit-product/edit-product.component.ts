import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Product } from '../../../../../models/product';
import { ProductService } from '../../../../../services/product.service';
import { CategoryService } from 'src/app/services/category.service'; 
import { CustomValidators } from 'src/app/helpers/customValidators';
import { Category } from 'src/app/models/category';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent {
  public categories!:Category[];
  public productGroup!:FormGroup;
  public msg:string|undefined;

  constructor(private formBuilder:FormBuilder,
    private ref:MatDialogRef<EditProductComponent>,
    private prodService:ProductService,
    private cateService:CategoryService,
    @Inject(MAT_DIALOG_DATA) private product:Product){
    this.productGroup=this.formBuilder.group({
      title:new FormControl(this.product.title,[
        Validators.required,
        Validators.maxLength(100),
        CustomValidators.onlyWhiteSpace
      ]),
      price:new FormControl(this.product.price.toFixed(2),[
        Validators.required,
        Validators.min(0.01),
        Validators.max(9999.99),
        Validators.pattern("(^[0-9]{1,4}\\.[0-9]{1,2}$)|(^[0-9]{1,4}$)")
      ]),
      category:new FormControl(this.product.cateId,Validators.required)
    });
  }

  ngOnInit(){
    this.cateService.getActiveCategories().subscribe(response=>{
      if(response===-1)
        this.ref.close();
      else
        this.categories=response;  
    });
  }

  cancel(){
    this.ref.close();
  }

  onSubmit(){ 
    const cateId=parseInt(this.productGroup.value.category?.toString()!);
    let product:Product={
      id:this.product.id,
      title:this.productGroup.value.title?.toString().trim()!,
      price:parseFloat(this.productGroup.value.price?.toString()!),
      status:null,
      cateId:cateId,
      cateTitle:null,
      cateStatus:null
    };
    
    this.prodService.updateProduct(product).subscribe(response=>{
      if(response===-1)
        this.ref.close();
      if(typeof(response)==='string')
        this.msg=response.replace("*","title");
      else{
        this.product.title=product.title;
        this.product.price=product.price;
        const category:Category=this.categories.find( item=>item.id===cateId )!;
        this.product.cateTitle=category.title;
        this.product.cateStatus=category.status; 
        this.ref.close();
      }
    });
  }

}

