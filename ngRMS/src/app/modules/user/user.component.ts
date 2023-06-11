import { Component } from '@angular/core'; 
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})

export class UserComponent {

  private intervalId!:NodeJS.Timer;

  constructor(
    private router:Router,
    private userService:UserService,
    private authService:AuthService
  ){}

  ngOnInit(){
    this.authService.getLogIn().subscribe(response=>{
      if(response===0)
        this.userService.refreshToken().subscribe();
      else 
        this.authService.initLogIn();
    });
    this.intervalId=setInterval(()=>{
      this.userService.refreshToken().subscribe();
    },900000);
  }

  ngOnDestroy(){
    clearInterval(this.intervalId);
  }

  logOut=()=>{
    this.userService.logOut().subscribe(response=>{
      if(response!==-1)
        this.router.navigateByUrl('');
    });
  }
}
