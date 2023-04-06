import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-ttms-dashboard-content',
  templateUrl: './ttms-dashboard-content.component.html',
  styleUrls: ['./ttms-dashboard-content.component.scss']
})
export class TtmsDashboardContentComponent implements OnInit{

  constructor (private router : Router, private profileService : ProfileService){}

  ngOnInit(): void {
    this.profileService.getFullDetails()
  }

  goToAddAgent(){
    this.router.navigate(['/agent/add']);
  }
  
}
