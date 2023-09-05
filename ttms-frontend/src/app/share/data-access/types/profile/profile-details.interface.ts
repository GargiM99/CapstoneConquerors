import { TRoles } from "../auth/token-details.interface"

export interface IProfileDetails{
    profileId: number | null
    user: User | null
    person: Person
    address: Address
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
    birthDate : Date
}

export interface Address {
    addressLine: string
    postalCode: string
    city: string
    province: string
    country: string
}

export interface User {
    username: string
    role: TRoles
    password?: string
}