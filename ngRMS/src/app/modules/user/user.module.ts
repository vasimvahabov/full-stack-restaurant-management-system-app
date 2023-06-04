import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserRoutingModule } from './user-routing.module'; 
import {MatDialogModule} from '@angular/material/dialog'; 
import {MatButtonModule} from '@angular/material/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; 
import { UserComponent } from './user.component';
import { OrdersComponent } from './components/orders/orders.component';
import { AddOrderComponent } from './components/orders/add-order/add-order.component';
import { EditOrderComponent } from './components/orders/edit-order/edit-order.component';
import { AddProductToOrderComponent } from './components/orders/add-product-to-order/add-product-to-order.component';

@NgModule({
  declarations: [
    UserComponent,
    OrdersComponent,
    AddOrderComponent,
    EditOrderComponent,
    AddProductToOrderComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    MatDialogModule,
    MatButtonModule,
    ReactiveFormsModule,
    FormsModule 
  ]
})
export class UserModule { }
