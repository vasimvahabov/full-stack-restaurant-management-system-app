import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';  
import { Category } from 'src/app/models/category'; 
import { CategoryService } from 'src/app/services/category.service';
import { AddCategoryComponent } from './add-category/add-category.component';
import { EditCategoryComponent } from './edit-category/edit-category.component';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent {
  public categories!:Category[];

  constructor(private dialog:MatDialog,private cateService:CategoryService){}

  ngOnInit(){
    this.cateService.getAllCategories().subscribe(response=>{
      if(response!==-1)
        this.categories=response; 
    });
  }

  editCategory(category:Category){
    this.dialog.open(EditCategoryComponent,{
      data:category,
      height:'300px',
      disableClose:true
    });
  }

  addCategory(){ 
    const addCategoryDialog=this.dialog.open(AddCategoryComponent,{
      height:'300px',
      disableClose:true
    });
    addCategoryDialog.afterClosed().subscribe((response)=>{
      if(response.data!==null){
        const category:Category=response.data;
        this.categories.push(category);
      }
    });
  }  

  onSlideToogle(categoryId:number){  
    this.cateService.changeCategoryStatus(categoryId).subscribe();
  }
}
