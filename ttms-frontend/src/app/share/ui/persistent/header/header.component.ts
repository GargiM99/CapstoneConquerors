import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { TRoles } from 'src/app/share/data-access/types/auth/token-details.interface';
import { IProfileDetails } from 'src/app/share/data-access/types/profile/profile-details.interface';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  @Input() profile: IProfileDetails | null = null

  clickedClient = false
  clickedAdmin = false

  clientOptions = ["Add Client", "View Clients"]
  adminOptions = ["Add Agent", "Trip Types", "Update Meals"]

  constructor(private router: Router){}

  redirectToHome(){
    this.router.navigate([""])
  } 

  handleAdminDropdownChange(selectedPage: string) {

    if (selectedPage == 'Add Agent'){
      this.router.navigate(['/agent/add']);
    }else if (selectedPage === 'Trip Types') {
      this.router.navigate(['/trip/type']);
    } else if (selectedPage === 'Update Meals') {
      this.router.navigate(['/meal']);
    }
    this.clickedAdmin = false
  }

  handleClientDropdownChange(selectedPage: string){

    if (selectedPage === 'Add Client') {
      this.router.navigate(['/client/create']);
    } else if (selectedPage === 'View Clients') {
      this.router.navigate(['/client']);
    }
    this.clickedClient = false
  }
}

