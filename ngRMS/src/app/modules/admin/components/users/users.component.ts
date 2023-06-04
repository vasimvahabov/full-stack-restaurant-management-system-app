import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router'; 
import { UserService } from 'src/app/services/user.service';
import { User } from '../../models/user';
import { AddUserComponent } from './add-user/add-user.component';
import { EditUserComponent } from './edit-user/edit-user.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'] 
})
export class UsersComponent {
  public usersList!:User[];

  constructor(private userService:UserService,private dialog:MatDialog,private router:Router){
    this.userService.getAllUsers().subscribe((response)=>{
      if(response===undefined)
        this.router.navigateByUrl("/error");
      else
        this.usersList=response; 
    });
  } 

  onSlideToogle(userId:number){  
    this.userService.changeUserStatus(userId).subscribe(response=>{
      if(response===undefined)
        this.router.navigateByUrl("/error");
    });
  }

  addUser(){ 
    const addUserDialog=this.dialog.open(AddUserComponent,{ 
      height:'620px',
      disableClose:true
    });
    addUserDialog.afterClosed().subscribe((response:any)=>{
      if(response.data!==null){
        const user:User=response.data;
        this.usersList.push(user);
      }
    });
  }

  editUser(user:User){ 
    this.dialog.open(EditUserComponent,{
      data:user,
      height:'655px',
      disableClose:true
    });
  }
}
