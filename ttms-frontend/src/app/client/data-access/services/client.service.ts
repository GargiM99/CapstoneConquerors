import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Store, select } from '@ngrx/store';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { ITokenDetail } from 'src/app/share/data-access/types/auth/token-details.interface';
import { Observable, mergeMap } from 'rxjs';
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

  constructor(private store: Store<IAppState>, private http: HttpClient, private router: Router) { 
    this.tokenDetails$ = this.store.pipe(select(detailSelector))
  }
}
