import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { inject } from "@angular/core";
import { TokenDetailsService } from "./token-details.service";
import { TRoles } from "../../types/auth/token-details.interface";

export const authGuardFn: CanActivateFn = (
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot) => {
        let tokenService = inject(TokenDetailsService)
        let route = inject(Router)
        let token = localStorage.getItem("jwtoken") ?? ""
        let authRole : TRoles[] = next.data["role"]

        let decodedToken = tokenService.decodeToken(token)

        if (decodedToken == null){
            route.navigate(["/login"])
            return false
        }else if (!authRole.includes(decodedToken.role)){
            route.navigate(["/login"])
            return false
        }
        return true
    }
