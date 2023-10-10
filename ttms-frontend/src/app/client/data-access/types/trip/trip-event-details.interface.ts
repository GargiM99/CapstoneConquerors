import { IEventDetails } from "./event-details.interface";
import { ITripDetails } from "./trip-details.interface";

export interface ITripEventDetails{
    tripDetails: ITripDetails
    eventDetails: IEventDetails[]
}
