import { Observable } from "rxjs";
import { ITripType } from "./trip-type.interface";

export interface ITripCreateDetails{
	tripName : String | undefined; 
	tripEndDate : Date | undefined;
	tripType: String | undefined
	clientId : number | undefined;
}  

export interface ITripCreateModalDetails{
	clientId: number
	tripType$: Observable<ITripType[]>
}