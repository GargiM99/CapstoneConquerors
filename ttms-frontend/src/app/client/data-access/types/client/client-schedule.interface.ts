export interface IClientSchedule{
    clientId: number
    username: string
    firstname: string
    lastname: string
    trips: ITripSchedule | null
}

export interface ITripSchedule{
    clientId: number | null
    tripId: number
    tripName: string
    tripType: string
    tripStartDate: Date
    tripEndDate: Date
    events: IEventSchedule | null
}

export interface IEventSchedule{
    eventId: number
    eventName: string
    eventDate: Date
    eventDescription: string | null
}