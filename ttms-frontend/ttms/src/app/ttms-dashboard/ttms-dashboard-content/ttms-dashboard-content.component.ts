import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ttms-dashboard-content',
  templateUrl: './ttms-dashboard-content.component.html',
  styleUrls: ['./ttms-dashboard-content.component.scss']
})
export class TtmsDashboardContentComponent {

  displayStyle : String = "none"

  constructor (private router : Router){}

  goToAddAgent(){
    this.router.navigate(['/agent/add']);
  }
  
  openPopup() {
    this.displayStyle = "block";
  }
  closePopup() {
    this.displayStyle = "none";
  }
}
