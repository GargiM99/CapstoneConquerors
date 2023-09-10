export interface IAgentDetails{
    user: User
    contact: Contact
    address: Address
    person: Person
}

interface User{
   id: number
   username: string
   password?: string  
}

interface Contact{
    primaryPhoneNumber: string
    secondaryPhoneNumber: string | null
    email: string
}

interface Address {
    addressLine: string
    postalCode: string
    city: string
    province: string
    country: string
}

interface Person{
    firstname : string
    lastname : string
    birthDate : Date
}