import { Address } from './address';
import { Contact } from './contact';
import { Person } from './person';

export class AgentDetails {
  person!: Person;
  contact!: Contact;
  address!: Address;
}
