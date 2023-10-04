import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ILoginDetails, ILoginResponse } from '../../types/auth/login-details.interface';
import { Observable, map } from 'rxjs';
import endPoints from '../../../../../assets/data/endpoints.json'

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {
  login(loginDetails: ILoginDetails): Observable<ILoginResponse>{
    const loginData = {
      "username": `${loginDetails.username}`,
      "password": `${loginDetails.password}`
    }

    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    })

    let response = this.http.post<ILoginResponse>(
          endPoints.authenticate, 
          JSON.stringify(loginData), 
          { headers: headers }) 
    
    return response
  }

  constructor(private http: HttpClient) { }
}
