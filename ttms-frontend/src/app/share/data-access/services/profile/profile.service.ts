import { Injectable } from '@angular/core';
import { IProfileDetails } from '../../types/profile/profile-details.interface';
import { Observable, delay, mergeMap, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IAppState } from '../../types/app-state.interface';
import { Store, select } from '@ngrx/store';
import { Router } from '@angular/router';
import { detailSelector } from '../../redux/auth/token-selectors';
import { ITokenDetail } from '../../types/auth/token-details.interface';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  tokenDetails$: Observable<ITokenDetail | null>
  endpoints = { profile: "http://localhost:8080/api/profile" }

  getProfile(username: string | null): Observable<IProfileDetails>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (username == null){
          throw new Error("Null Username")
        }
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }
         
        return this.sendGetProfile(username, tokenDetails!.token)
      })
    )
  }

  sendGetProfile(username: string, token: string){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    })
    return  this.http.get<IProfileDetails>(this.endpoints.profile, { headers: headers })
  }

  updateProfile(updateDetails: IProfileDetails): Observable<IProfileDetails>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (updateDetails == null){
          throw new Error("Profile Username")
        }
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }
         
        return this.sendUpdateProfile(updateDetails, tokenDetails!.token)
      })
    )
  }

  sendUpdateProfile(updateDetails: IProfileDetails, token: string){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    })

    return this.http.put<IProfileDetails>(
      this.endpoints.profile, 
      JSON.stringify(updateDetails), 
      { headers: headers })
  }

  constructor(private http: HttpClient, private store: Store<IAppState>, private router: Router) {
    this.tokenDetails$ = this.store.pipe(select(detailSelector))
  }
}
