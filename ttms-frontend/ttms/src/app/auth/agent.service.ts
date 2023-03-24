import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AgentDetails } from '../classes/agent-details';
import { TokenService } from './token.service';

import endPoints from '../../assets/data/endpoints.json'
import { LoginDetails } from '../classes/login-details';

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

  async saveMealPrices(newAgentDetails: AgentDetails): Promise<LoginDetails | HttpErrorResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      //Authorization: `Bearer ${this.tokenService.getToken()}`,
    });

    console.log(newAgentDetails)

    let response = this.http.post<LoginDetails>(
      endPoints.register,
      JSON.stringify(newAgentDetails),
      { headers: headers }
    );

    return new Promise((resolve) => {
      response.subscribe({
        next: (loginDetails : LoginDetails) => {
          resolve(new LoginDetails(loginDetails.password, loginDetails.username))
        },
        error: (err : HttpErrorResponse) => {
          resolve(err)
        },
      });
    });
  }
}
