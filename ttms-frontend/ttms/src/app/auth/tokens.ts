import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

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