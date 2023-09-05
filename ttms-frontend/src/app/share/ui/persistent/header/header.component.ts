import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { IProfileDetails } from 'src/app/share/data-access/types/profile/profile-details.interface';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  @Input() profile: IProfileDetails | null = null
  constructor(private router: Router){
    console.log(this.profile)
  }

  redirectToHome(){
    this.router.navigate([""])
  } 
}
