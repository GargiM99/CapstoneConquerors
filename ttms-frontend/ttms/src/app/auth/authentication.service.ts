import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { LoginDetails } from '../classes/login-details';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient, private tokenService : TokenService) { }

  authenticateUser(loginDetails : LoginDetails) {

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    const data = {
      "username": `${loginDetails.username}`,
      "password": `${loginDetails.password}`
    };

    let jwtoken = this.http.post<string>('http://localhost:8080/api/auth/authenticate', JSON.stringify(data), { headers: headers })
    
    jwtoken.subscribe({
      next : (value) => {this.tokenService.setToken(value); this.tokenService.setLSToken()},
      error : console.log
    })
  }
}
