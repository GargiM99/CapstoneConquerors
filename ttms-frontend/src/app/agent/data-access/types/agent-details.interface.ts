export interface IAgentDetails{
    user: User
    contact: Contact
    person: Person
}

interface User{
   id: number
   username: string
   role?: roles
   password?: string  
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

type roles = "ADMIN" | "AGENT"