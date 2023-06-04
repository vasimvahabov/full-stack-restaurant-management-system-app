import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CategoriesComponent } from './components/categories/categories.component';
import { ProductsComponent } from './components/products/products.component';
import { CompletedOrdersComponent } from './components/completed-orders/completed-orders.component';
import { UsersComponent } from './components/users/users.component'; 
import { EmloyeesComponent } from './components/employees/employees.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EditUserComponent } from './components/users/edit-user/edit-user.component';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import { AddUserComponent } from './components/users/add-user/add-user.component';
import { AddEmployeeComponent } from './components/employees/add-employee/add-employee.component';
import { EditEmployeeComponent } from './components/employees/edit-employee/edit-employee.component';
import { AddCategoryComponent } from './components/categories/add-category/add-category.component';
import { EditCategoryComponent } from './components/categories/edit-category/edit-category.component';
import { AddProductComponent } from './components/products/add-product/add-product.component';
import { EditProductComponent } from './components/products/edit-product/edit-product.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { ProductsListComponent } from './components/completed-orders/products-list/products-list.component';
import { PositionsComponent } from './components/positions/positions.component';
import { AddPositionComponent } from './components/positions/add-position/add-position.component';
import { EditPositionComponent } from './components/positions/edit-position/edit-position.component';

@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    CategoriesComponent,
    ProductsComponent,
    CompletedOrdersComponent,
    UsersComponent, 
    EmloyeesComponent, 
    EditUserComponent, 
    AddUserComponent, 
    AddEmployeeComponent, 
    EditEmployeeComponent, 
    AddCategoryComponent, 
    EditCategoryComponent, 
    AddProductComponent, 
    EditProductComponent,  
    EditProfileComponent, 
    ProductsListComponent, 
    PositionsComponent, 
    AddPositionComponent, 
    EditPositionComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatSlideToggleModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule
  ]
})
export class AdminModule { }
