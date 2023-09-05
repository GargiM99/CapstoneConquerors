import { Injectable } from '@angular/core';
import { ILoginDetails, ILoginResponse } from '../../types/auth/login-details.interface';
import { Observable, catchError, delay, map, of } from 'rxjs';
import { ITokenDecoded, ITokenDetail } from '../../types/auth/token-details.interface';
import jwt_decode from 'jwt-decode'
import { AuthenticateService } from './authenticate.service';

@Injectable({
  providedIn: 'root'
})
export class TokenDetailsService {

  getToken(loginDetails: ILoginDetails | null ): Observable<ITokenDetail>{
    let decodedToken : ITokenDecoded | null = null
    let jwtoken = localStorage.getItem("jwtoken") ?? null

    if (jwtoken != null)
      decodedToken = this.decodeToken(jwtoken)
    
    if (decodedToken == null && loginDetails != null){
      let loginResponse = this.auth.login(loginDetails)
      let tokenDetails$ = loginResponse.pipe(
        map((response : ILoginResponse) => {
          const jwtoken = response.token
          const decodedToken = this.decodeToken(jwtoken)
          localStorage.setItem("jwtoken", jwtoken)

          if (decodedToken == null)
            throw new Error("Received invalid token.")

          const tokenDetails: ITokenDetail ={
            token: jwtoken,
            decoded: decodedToken
          } 
          return tokenDetails
        })
      )
      return(tokenDetails$)
    }
    return of(<ITokenDetail>{token: jwtoken, decoded: decodedToken})
  } 

  decodeToken(jwtoken: string): ITokenDecoded | null{
    try {
      const decodedToken: ITokenDecoded = jwt_decode(jwtoken)
      const currDateTime = Date.now() / 1000

      if (decodedToken && decodedToken.exp && decodedToken.exp > currDateTime)
        return decodedToken
      
      localStorage.removeItem("jwtoken")
      return null
    } catch (error) { 
      localStorage.removeItem("jwtoken")
      return null
    }

  } 

  constructor(private auth: AuthenticateService) { }
}
