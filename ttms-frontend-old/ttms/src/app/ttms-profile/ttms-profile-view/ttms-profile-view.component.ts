import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AgentDetails } from 'src/app/classes/agent-details';
import { AgentFullDetails } from 'src/app/classes/agent-full-details';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-ttms-profile-view',
  templateUrl: './ttms-profile-view.component.html',
  styleUrls: ['./ttms-profile-view.component.scss']
})
export class TtmsProfileViewComponent implements OnInit{

  userDetails$ : AgentFullDetails = new AgentFullDetails()

  constructor(private router : Router, private profileService : ProfileService){}

  ngOnInit(): void {
    this.profileService.fullDetailsObs$.subscribe({
      next : (details) => { this.userDetails$ = details },
      error : (err) => { console.log(err) }
    })
  }

  goToPasswordForm(){
    this.router.navigate(['/password/change'])
  }
}
