import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import {MatIconModule} from '@angular/material/icon'; 
import { AppComponent } from './app.component';
import {MatButtonModule} from '@angular/material/button'; 
import {MatToolbarModule} from '@angular/material/toolbar';
import { AdminLoginComponent } from './components/admin-login/admin-login.component'; 
import {MatInputModule} from '@angular/material/input';  
import {MatFormFieldModule} from '@angular/material/form-field'; 
import { MatDialogModule } from '@angular/material/dialog';
import { UserLoginComponent } from './components/admin-login/user-login/user-login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ErrorComponent } from './components/error/error.component';

@NgModule({
  declarations: [
    AppComponent, 
    AdminLoginComponent, 
    UserLoginComponent, ErrorComponent 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatIconModule,
    MatButtonModule,
    MatToolbarModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatDialogModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
