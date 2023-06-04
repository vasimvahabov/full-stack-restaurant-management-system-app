import { Component } from '@angular/core'; 
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})

export class UserComponent {

  private intervalId!:NodeJS.Timer;

  constructor(private router:Router,private userService:UserService){
    // if(this.userService.token===undefined){
    //   if(localStorage.getItem("token2")!==null){
    //     const token=localStorage.getItem("token2")!;
    //     this.userService.token=token;
    //     if(localStorage.getItem("token1")!==null)
    //       localStorage.removeItem("token1")
    //   }
    //   else 
    //     router.navigateByUrl(''); 
    // }
    // else if(localStorage.getItem("token1")!==null){
    //   if(this.userService.token===null)
    //     router.navigateByUrl('/admin')
    //   else 
    //     localStorage.removeItem("token1")
    // }
  }

  ngOnInit(){
    this.intervalId=setInterval(()=>{
      //this.userService.refreshToken().subscribe();
    },1000);
  }

  ngOnDestroy(){
    clearInterval(this.intervalId);
  }

  logOut=()=>{
    localStorage.removeItem('token2');
    this.router.navigateByUrl('');
  }
}
