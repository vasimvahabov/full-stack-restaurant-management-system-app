import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Category } from 'src/app/models/category';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { AdminService } from 'src/app/services/admin.service';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.scss']
})
export class AddCategoryComponent{
  public nameControl!:FormControl;

  constructor(
    private ref:MatDialogRef<AddCategoryComponent>,
    private adminService:AdminService,
    private cateService:CategoryService){
    this.nameControl=new FormControl('',[Validators.required,Validators.maxLength(100),CustomValidators.onlyWhiteSpace]);
  }

  cancel(){
    this.ref.close({data:null});
  }

  onSubmit(){
    let category:Category={
      id:null,
      name:this.nameControl.value.toString().trim(),
      status:null,
      prodCount:null
    };
    // this.cateService.addCategory(this.adminService.token,category).subscribe((response)=>{
    //   category=response;
    //   this.ref.close({data:category})
    // });
  }
}
