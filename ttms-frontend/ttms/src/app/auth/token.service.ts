import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

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

  getToken(): string {
    return this.token;
  }

  getClaims(): any {
    return this.claims;
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
