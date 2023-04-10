import { Injectable } from '@angular/core';
import { Service } from './service.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceDataService {
  selectedService: Service = { name: '', description: '', snackPreference: '' };

  // constructor to initialize selectedService
  constructor() {
    this.selectedService = {
      name: 'Full Service',
      description: 'Our Full Service.',
      snackPreference: 'Appetizers and desserts are available.'
    };
  }

  // method to set selectedService
  setSelectedService(service: Service) {
    this.selectedService = service;
  }

  // method to get selectedService
  getSelectedService(): Service {
    return this.selectedService;
  }
}



