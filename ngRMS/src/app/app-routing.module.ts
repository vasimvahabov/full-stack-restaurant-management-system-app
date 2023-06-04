import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminLoginComponent } from './components/admin-login/admin-login.component'; 
import { ErrorComponent } from './components/error/error.component';

const routes: Routes = [
  {
    path:'admin',
    loadChildren:()=>import('./modules/admin/admin.module').then(m=>m.AdminModule) 
  },
  {
    path:'user',
    loadChildren:()=>import('./modules/user/user.module').then(m=>m.UserModule) 
  },
  {
    path:'error',
    component:ErrorComponent
  },
  {
    path:"**",
    redirectTo:'',
    component:AdminLoginComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
