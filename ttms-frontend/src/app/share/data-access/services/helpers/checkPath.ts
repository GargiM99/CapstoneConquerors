import { inject } from "@angular/core"
import { Location } from '@angular/common';

export const checkPath = (checkPaths: string[]) =>{
    let location = inject(Location)
    let currPath = location.path();

    if (currPath.charAt(0) == '/')
        currPath = currPath.substring(1);

    for(let path of checkPaths){
        if (currPath.includes(path))
            return true
    }

    return false
}