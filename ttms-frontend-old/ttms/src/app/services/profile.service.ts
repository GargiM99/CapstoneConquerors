import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from '../auth/token.service';
import endPoints from '../../assets/data/endpoints.json'
import { AgentFullDetails } from '../classes/agent-full-details';
import { BehaviorSubject } from 'rxjs';

/*
* author: Hamza
* date: 2023/04/04
* description: Manages the profile of the logged in user 
*/

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private fullDetails$ : BehaviorSubject<AgentFullDetails> = new BehaviorSubject(new AgentFullDetails()); 

  fullDetailsObs$ = this.fullDetails$.asObservable()

  constructor(private http : HttpClient, private tokenService : TokenService) { }

  async getFullDetails () : Promise<number>{

    let token = this.tokenService.getToken()
    if (token.length < 150)
      return (0)

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.tokenService.getToken()}`
    });

    let response = this.http.get<AgentFullDetails>(endPoints.profile, { headers: headers })

    return new Promise((resolve)=>{
      response.subscribe({
        next: (fullDetails) => {
          this.fullDetails$.next(fullDetails)
          console.log(fullDetails)
          return (200)
        },
        error: (err : HttpErrorResponse) => {resolve(err.status)}
      })
    })
  }

  async changePassword (newPassword : string){
    let token = this.tokenService.getToken()
    let userId = this.fullDetails$.getValue().user.id

    let body = {"password" : newPassword}

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })

    let response = this.http.put<AgentFullDetails>(endPoints.password + `/${userId}`, JSON.stringify(body),{ headers: headers })

    console.log(token)

    return new Promise((resolve)=>{
      response.subscribe({
        next: () => {return (200)},
        error: (err : HttpErrorResponse) => {resolve(err.status)}
      })
    })
  }
}
