import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Category } from 'src/app/models/category';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.scss']
})
export class AddCategoryComponent{
  public titleControl!:FormControl;
  public msg!:string|undefined;

  constructor(
    private ref:MatDialogRef<AddCategoryComponent>,
    private cateService:CategoryService){
    this.titleControl=new FormControl('',[
      Validators.required,
      Validators.maxLength(100),
      CustomValidators.onlyWhiteSpace
    ]);
  }

  cancel(){
    this.ref.close({data:null});
  }

  onSubmit(){
    let category:Category={
      id:null,
      title:this.titleControl.value.toString().trim(),
      status:null,
      prodCount:null
    }; 
    this.cateService.addCategory(category).subscribe(response=>{
      if(response===-1)
        this.ref.close({data:null});
      else if(typeof(response)==='string'){
        this.msg=response.replace("*","title");
      }
      else{
        category=response; 
        this.ref.close({data:category})
      }
    });
  }
}
