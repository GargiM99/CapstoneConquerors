import { Address } from "./address";
import { Contact } from "./contact";
import { Person } from "./person";
import { User } from "./user";

export class AgentFullDetails {
    user !: User
    person !: Person
    contact !: Contact
    address !: Address

    constructor (){
        this.user = new User()
        this.person = new Person()
        this.contact = new Contact()
        this.address = new Address()
    }
  }