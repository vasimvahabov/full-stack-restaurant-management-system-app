import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'; 
import { AdminComponent } from './admin.component';
import { CompletedOrdersComponent } from './components/completed-orders/completed-orders.component';
import { CategoriesComponent } from './components/categories/categories.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { EmloyeesComponent } from './components/employees/employees.component';
import { ProductsComponent } from './components/products/products.component'; ''
import { UsersComponent } from './components/users/users.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { PositionsComponent } from './components/positions/positions.component';

const routes: Routes = [
  {
    path:'',
    component:AdminComponent,
    children:[
      {
        path:'edit-profile',
        component:EditProfileComponent
      },
      {
        path:'dashboard',
        component:DashboardComponent
      },
      {
        path:'positions',
        component:PositionsComponent
      }, 
      {
        path:'users',
        component:UsersComponent
      },
      {
        path:'completed-orders',
        component:CompletedOrdersComponent
      },
      {
        path:'categories',
        component:CategoriesComponent
      },
      {
        path:'products',
        component:ProductsComponent
      },
      {
        path:'employees',
        component:EmloyeesComponent
      }, 
      {
        path:'**',
        redirectTo:'dashboard'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
