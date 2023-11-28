export interface ITripDetails{
    id : number;
	tripName : string; 
	tripStartDate : Date;
	tripEndDate : Date;
	status : TTripStatus;
	clientId : number;
} 

export type TTripStatus = "INPROGRESS" | "COMPLETE" | "INCOMPLETE"