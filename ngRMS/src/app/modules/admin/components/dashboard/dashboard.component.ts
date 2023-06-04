import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service'; 
import { AdminDashboard } from '../../models/adminDashboard'; 

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  public adminDasboard!:AdminDashboard;

  constructor(private adminService:AdminService,private router:Router){
    this.adminService.getAdminDasboard().subscribe(response=>{
      if(response===undefined)
        this.router.navigateByUrl('/error');
      else 
        this.adminDasboard=response; 
    });
  }
}
