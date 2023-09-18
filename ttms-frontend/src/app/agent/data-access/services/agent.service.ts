import { Injectable } from '@angular/core';
import { IAgentBasics } from '../types/agent-basics.interface';
import { Observable, map, mergeMap, of, tap } from 'rxjs';
import { ITokenDetail } from 'src/app/share/data-access/types/auth/token-details.interface';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IAddAgentAction, IAddAgentRes } from '../types/agent-responses.interface';
import { IProfileDetails } from 'src/app/share/data-access/types/profile/profile-details.interface';
import { Store, select } from '@ngrx/store';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { detailSelector } from 'src/app/share/data-access/redux/auth/token-selectors';
import { Router } from '@angular/router';
import { IAgentDetails } from '../types/agent-details.interface';

@Injectable({
  providedIn: 'root'
})
export class AgentService {
  endPoints = {agent: "http://localhost:8080/api/agent"}

  tokenDetails$: Observable<ITokenDetail | null>


  getAgentBasics(agentIds: number[] | null): Observable<IAgentBasics[]>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }
        
        if (agentIds == null)
          return this.sendGetAllAgents(tokenDetails!.token)
        return this.sendGetAgentsByIds(agentIds)
      })
    )
  }

  sendGetAllAgents(token: string): Observable<IAgentBasics[]>{
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })

    let response = this.http.get<IAgentBasics[]>(
      this.endPoints.agent,
      { headers: headers }
    )
    
    return response
  }

  sendGetAgentsByIds(agentIds: number[]): Observable<IAgentBasics[]>{
    //Add code for Ids
    return of([])
  }

  getAgentDetails(agentId: number | null): Observable<IAgentDetails>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }
        
        return this.sendGetAgentDetailsById(agentId, tokenDetails.token)
      })
    )
  }

  sendGetAgentDetailsById(agentId: number | null, token: string){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })

    let response = this.http.get<IAgentDetails>(
      `${this.endPoints.agent}/${agentId}`,
      { headers: headers }
    )
    
    return response
  }

  addAgent(profile: IProfileDetails): Observable<IAddAgentAction>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }
         
        return this.sendAddAgent(profile, tokenDetails!.token)
      })
    )
  }

  sendAddAgent(profile: IProfileDetails, token: string): Observable<IAddAgentAction>{
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })

    let response = this.http.post<IAddAgentRes>(
      `${this.endPoints.agent}`, profile, { headers: headers }
    )

    return response.pipe(map((res) =>{
      let updateProfile = {...profile}
      updateProfile.user = {
        username: res.username, 
        password: res.password, 
        role: 'AGENT'
      }
      updateProfile.profileId = res.id

      let agentBasics: IAgentBasics = {
        username: updateProfile.user.username ,
        firstname: updateProfile.person.firstname,
        lastname: updateProfile.person.lastname,
        id: updateProfile.profileId ?? 0
      }

      let action: IAddAgentAction = {profile: updateProfile, agentBasic: agentBasics}
      return action
    }))
  }

  constructor(private store: Store<IAppState>, private http: HttpClient, private router: Router) { 
    this.tokenDetails$ = this.store.pipe(select(detailSelector))
  }
}
