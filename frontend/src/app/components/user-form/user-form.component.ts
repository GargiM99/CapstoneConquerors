import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent{
  user !: User;

  constructor(
    private router : Router,
    private route : ActivatedRoute,
    private userService : UserService
  ){
    this.user = new User();
  }

  onSubmitUser() {
    this.userService.save(this.user).subscribe(result => this.gotoUserList())
  }

  gotoUserList(): void {
    this.router.navigate(['/users']);
  }
}
