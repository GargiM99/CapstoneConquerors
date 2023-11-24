import { TRoles } from "../auth/token-details.interface"

export interface IProfileDetails{
    profileId: number | null
    user: User | null
    person: Person
    contact: Contact
}

export interface Contact{
    email : string 
    primaryPhoneNumber : string
    secondaryPhoneNumber : string | null
}

export interface Person{
    firstname : string
    lastname : string
}

export interface User {
    username: string
    role: TRoles
    id?: number
    password?: string
}