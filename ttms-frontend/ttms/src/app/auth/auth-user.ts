import { inject, Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";

@Injectable()
export class PermissionsService {

    constructor(private router : Router){}

    canActivate(): boolean{
        let token = localStorage.getItem("token")

        if (token != null)
            return true
        
        this.router.navigate(['/login']);
        return false
    }
}

export const canActivateUser : CanActivateFn =
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(PermissionsService).canActivate();
    };