import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Category } from 'src/app/models/category';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.scss']
})
export class EditCategoryComponent {
  public titleControl!:FormControl;
  public msg:string|undefined;

  constructor(
    private ref:MatDialogRef<EditCategoryComponent>,
    private cateService:CategoryService, 
    @Inject(MAT_DIALOG_DATA) private category:Category){
    this.titleControl=new FormControl(this.category.title,[
      Validators.required,
      Validators.maxLength(100),
      CustomValidators.onlyWhiteSpace
    ]);
  }

  cancel(){
    this.ref.close();
  }

  onSubmit(){
    let category:Category={
      id:this.category.id,
      title:this.titleControl.value.toString().trim(),
      status:null,
      prodCount:null
    }; 
    this.cateService.updateCategory(category).subscribe(response=>{
      if(response===-1)
        this.ref.close();
      else if(typeof(response)==='string')
        this.msg=response.replace("*","title");
      else{
        this.category.title=category.title;
        this.ref.close();
      }
    });
  }
}
