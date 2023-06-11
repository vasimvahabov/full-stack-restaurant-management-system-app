import { Component } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service'; 
import { AdminDashboard } from '../../models/adminDashboard'; 

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  public adminDasboard!:AdminDashboard;

  constructor(private adminService:AdminService){}

  ngOnInit(){
    this.adminService.getAdminDasboard().subscribe(response=>{
      if(response!==-1) 
        this.adminDasboard=response; 
    });
  }
}
