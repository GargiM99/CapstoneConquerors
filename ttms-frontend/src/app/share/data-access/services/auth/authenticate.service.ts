import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ILoginDetails, ILoginResponse } from '../../types/auth/login-details.interface';
import { Observable, map } from 'rxjs';
import { TokenDetailsService } from './token-details.service';
import { TRoles } from '../../types/auth/token-details.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {
  endPoints = {authenticate: "http://localhost:8080/api/auth/authenticate"}

  login(loginDetails: ILoginDetails): Observable<ILoginResponse>{
    const loginData = {
      "username": `${loginDetails.username}`,
      "password": `${loginDetails.password}`
    }

    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    })

    let response = this.http.post<ILoginResponse>(
          this.endPoints.authenticate, 
          JSON.stringify(loginData), 
          { headers: headers }) 
    
    return response
  }

  constructor(private http: HttpClient) { }
}
