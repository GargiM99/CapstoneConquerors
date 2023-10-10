export interface IEventDetails{
    id: number;
	eventName: String; 
	eventDate: Date;
	status: TEventStatus;
	tripId: number;
}

export type TEventStatus = "COMPLETE" | "INCOMPLETE"