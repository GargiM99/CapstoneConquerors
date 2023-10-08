export interface ITripDetails{
    id : number;
	tripName : String; 
	tripStartDate : Date;
	tripEndDate : Date;
	status : TTripStatus;
	clientId : number;
}

export type TTripStatus = "INPROGRESS" | "COMPLETE" | "INCOMPLETE"