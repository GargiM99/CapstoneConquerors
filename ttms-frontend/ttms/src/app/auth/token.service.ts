import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

/*
* author: Hamza
* date: 2023/03/09
* description: Service to decode token's details, and check expiration
*/

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private router: Router, private jwtHelper: JwtHelperService) { }

  private token !: string;
  private claims !: any;
  private expirationDate !: Date | null;

  //Sets the token and claims using the token
  setToken(token: string): void {
    token = JSON.stringify(token)
    this.token = token;
    this.claims = this.jwtHelper.decodeToken(<string>token);
    this.expirationDate = this.jwtHelper.getTokenExpirationDate(token);
  } 

  clearToken(){
    localStorage.clear()
    this.token = ""
  }

  getToken(): string {

    if (this.token == undefined || this.token.length < 150)
      this.setTokenFromLS()

    if (this.token != undefined && this.token.length > 150)
      return JSON.parse(JSON.parse(this.token)).token;
    
    return("");
  }

  getClaims(): any {
    return this.claims;
  }

  getSubject(): any{
    return this.claims.sub;
  }

  getRole(): any{
    return this.claims.Role;
  } 

  getExpirationDate(): Date | null {
    return this.expirationDate;
  }

  isTokenExpired(): boolean {
    return this.jwtHelper.isTokenExpired(this.token);
  }

  //Sets token from the localStorage
  setTokenFromLS(): boolean{
    let lsToken = localStorage.getItem("jwtoken")

    if (lsToken != null && lsToken.length > 150){
      this.setToken(lsToken)
      return true
    }
    
    else
      return false
  }

  setLSToken(): void{
    localStorage.setItem("jwtoken", this.token)
  }

  //Checks that the token is still valid
  canActivate(): boolean {
    if (this.getToken == null || this.getToken.length < 150)
      this.setTokenFromLS()

    if (this.getToken != null && !this.isTokenExpired())
      return true
      
    this.router.navigate(['/login']);
    return false
  }
} 
