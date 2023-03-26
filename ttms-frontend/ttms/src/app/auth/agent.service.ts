import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AgentDetails } from '../classes/agent-details';
import { TokenService } from './token.service';

import endPoints from '../../assets/data/endpoints.json'
import { LoginDetails } from '../classes/login-details';
import { BasicAgentDetails } from '../classes/basic-agent-details';

/*
* author: Hamza
* date: 2023/03/25
* description: Service to manage agents and there details
*/

@Injectable({
  providedIn: 'root',
})
export class AgentService {
  constructor(private tokenService: TokenService, private http: HttpClient) {}

  private _agentDetails: AgentDetails = new AgentDetails();

  get agentDetails() {
    return this._agentDetails;
  }

  set agentDetails(newAgentDetails: AgentDetails) {
    this._agentDetails = newAgentDetails;
  }

  async addAgent(newAgentDetails: AgentDetails) : Promise<LoginDetails | HttpErrorResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.tokenService.getToken()}`,
    })

    let response = this.http.post<LoginDetails>(
      endPoints.register, JSON.stringify(newAgentDetails), { headers: headers }
    )

    console.log(newAgentDetails)

    return new Promise((resolve) => {
      response.subscribe({
        next: (loginDetails : LoginDetails) => {
          resolve(new LoginDetails(loginDetails.password, loginDetails.username))
        },
        error: (err : HttpErrorResponse) => { resolve(err) }
      })
    })
  }

  async getAgents() : Promise<BasicAgentDetails[] | HttpErrorResponse>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.tokenService.getToken()}`,
    })

    let response = this.http.get<BasicAgentDetails[]>( endPoints.agent, { headers: headers } )

    return new Promise((resolve) => {
      response.subscribe({
        next: (agents : BasicAgentDetails[]) => {
          console.log(agents)
          resolve(agents)
        },
        error: (err : HttpErrorResponse) => { resolve(err) },
      })
    })
  } 
}
