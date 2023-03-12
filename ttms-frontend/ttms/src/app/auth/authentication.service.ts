import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginDetails } from '../classes/login-details';
import { TokenService } from './token.service';

/*
* author: Hamza
* date: 2023/03/09
* description: Service to authenticate user using backend
*/

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  async authenticateUser(loginDetails: LoginDetails) : Promise<boolean> {
    let isAuthenticated : boolean = false

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    const data = {
      "username": `${loginDetails.username}`,
      "password": `${loginDetails.password}`
    };

    let response = this.http.post<string>('http://localhost:8080/api/auth/authenticate', JSON.stringify(data), { headers: headers })
    
    //Async promise which sets the token if password is valid 
    return new Promise((resolve)=>{
      response.subscribe({
        next: (token) => {
          this.tokenService.setToken(token)
          this.tokenService.setLSToken()
          resolve(true)
        },
        error: () => {resolve(false)}
      })
    })
  }
}
