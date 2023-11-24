import { ITripDetails } from "../trip/trip-details.interface"
import { IClientNotes } from "./client-note.interface"

export interface IClientDetails{
    user: User
    contact: Contact
    person: Person
    tripDetails: ITripDetails[]
    clientNotes: IClientNotes[]
}

interface User{
   id: number
   username: string
   agentId?: number 
}

interface Contact{
    primaryPhoneNumber: string
    secondaryPhoneNumber: string | null
    email: string
}

interface Person{
    firstname : string
    lastname : string
}

export interface IClientUpdated{
    isUpdated: boolean
}