import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Store, select } from '@ngrx/store';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { ITokenDetail } from 'src/app/share/data-access/types/auth/token-details.interface';
import { Observable, map, mergeMap } from 'rxjs';
import { detailSelector } from 'src/app/share/data-access/redux/auth/token-selectors';
import endPoints from '../../../../assets/data/endpoints.json'
import { IClientDetails } from '../types/client-details.interface';
import { IClientBasics } from '../types/client-basic.inteface';
import { IAgentBasics } from 'src/app/agent/data-access/types/agent-basics.interface';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  tokenDetails$: Observable<ITokenDetail | null>

  createClient(details: IClientDetails): Observable<IClientBasics>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }
         
        return this.sendCreateClient(details, tokenDetails!.token)
      })
    )
  }

  sendCreateClient(details: IClientDetails, token: String){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })

    let response = this.http.post<IAgentBasics>(
      `${endPoints.client}`, details, { headers: headers }
    )

    return response
  }

  getClientBasics(): Observable<IClientBasics[]>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }

        return this.sendGetClientBasics(tokenDetails.token)
      })
    )
  }

  sendGetClientBasics(token: String){
    let headers = new HttpHeaders({'Authorization': `Bearer ${token}`})

    let response = this.http.get<IClientBasics[]>(
      `${endPoints.client}`, { headers: headers }
    )

    return response
  }

  getClientDetails(clientId: number): Observable<IClientDetails>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }

        return this.sendGetClientDetails(tokenDetails.token, clientId)
      })
    )
  }

  sendGetClientDetails(token: String, clientId: number){
    let headers = new HttpHeaders({'Authorization': `Bearer ${token}`})

    let response = this.http.get<IClientDetails>(
      `${endPoints.client}/${clientId}`, { headers: headers }
    )

    return response
  }

  updateClientDetails(clientId: number, clientDetails: IClientDetails): Observable<IClientDetails>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }

        return this.sendUpdateClientDetails(tokenDetails.token, clientId, clientDetails)
      })
    )
  }

  sendUpdateClientDetails(token: String, clientId: number, clientDetails: IClientDetails){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })

    let response = this.http.put<IClientDetails>(
      `${endPoints.client}/${clientId}`, clientDetails, { headers: headers }
    )

    return response
  }

  constructor(private store: Store<IAppState>, private http: HttpClient, private router: Router) { 
    this.tokenDetails$ = this.store.pipe(select(detailSelector))
  }
}
