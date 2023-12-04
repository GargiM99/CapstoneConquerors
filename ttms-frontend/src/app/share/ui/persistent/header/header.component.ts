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

  handleAdminDropdownChange(event: any) {
    const selectedPage = event.target.value;

    // Implement logic to navigate to the selected page or perform other actions
    console.log('Selected Page:', selectedPage);

    // Example navigation based on the selected page
    if (selectedPage === 'trip-type') {
      this.router.navigate(['/trip/type']);
    } else if (selectedPage === 'meal-price') {
      this.router.navigate(['/meal']);
    }
  }
}

