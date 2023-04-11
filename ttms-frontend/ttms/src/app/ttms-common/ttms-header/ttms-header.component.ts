import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Observer, Subscription, isEmpty } from 'rxjs';
import { TokenService } from 'src/app/auth/token.service';
import { AgentFullDetails } from 'src/app/classes/agent-full-details';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-ttms-header',
  templateUrl: './ttms-header.component.html',
  styleUrls: ['./ttms-header.component.scss']
})
export class TtmsHeaderComponent implements OnInit {

  userDetails$ : AgentFullDetails = new AgentFullDetails()

  constructor(private router: Router, private profileService : ProfileService, 
              private tokenService : TokenService){}

  ngOnInit(): void {
    this.profileService.fullDetailsObs$.subscribe({
      next : (details) => { this.userDetails$ = details },
      error : (err) => { console.log(err) }
    })
    
    if (this.userDetails$.user.username == undefined)
      this.profileService.getFullDetails();
  }

  goToMealsForm(){
    this.router.navigate(['/meals'])
  }

  goToDashboard(){
    this.router.navigate(['/'])
  }

  goToAgentsList(){
    this.router.navigate(['/agent'])
  }

  goToProfile(){
    this.router.navigate(['/profile'])
  }

  logoutUser(){
    this.tokenService.clearToken()
    this.router.navigate(['/login'])
  }
}
