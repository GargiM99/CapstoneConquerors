export interface ITripType{
    typeName: string
    eventTypes: IEventType[]
}

export interface IEventType{
    eventName: string
    eventDescription: string
    dateDiff: number
}

export interface IRemoveEventType{
    eventIndex: number
    tripIndex: number
}

export interface IChangeTripType{
    trip: ITripType
    tripIndex: number
}

export interface IChangeEventType{
    event: IEventType
    eventIndex: number
}