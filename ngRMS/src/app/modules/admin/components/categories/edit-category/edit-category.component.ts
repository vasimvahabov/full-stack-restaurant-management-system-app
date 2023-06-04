import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Category } from 'src/app/models/category';
import { CustomValidators } from 'src/app/helpers/customValidators';
import { AdminService } from 'src/app/services/admin.service';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.scss']
})
export class EditCategoryComponent {
  public nameControl!:FormControl;

  constructor(
    private ref:MatDialogRef<EditCategoryComponent>,
    private cateService:CategoryService,
    private adminService:AdminService,
    @Inject(MAT_DIALOG_DATA) private category:Category){
      this.nameControl=new FormControl(this.category.name,[Validators.required,Validators.maxLength(100),CustomValidators.onlyWhiteSpace]);
  }

  cancel(){
    this.ref.close();
  }

  onSubmit(){
    let category:Category={
      id:this.category.id,
      name:this.nameControl.value.toString().trim(),
      status:null,
      prodCount:null
    }; 
    // this.cateService.updateCategory(this.adminService.token,category).subscribe(()=>{
    //   this.category.name=category.name;
    //   this.ref.close();
    // });
  }
}
