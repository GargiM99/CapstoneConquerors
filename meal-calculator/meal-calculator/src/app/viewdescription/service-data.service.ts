import { Injectable } from '@angular/core';
import { Service } from './service.model';
import { CalculatePrice } from './calculatePrice';

@Injectable({
  providedIn: 'root'
})
export class ServiceDataService {
  selectedService: Service = { name: '', description: '', snackPreference: '' };
  QS_PRICE_CHILDREN : number = 15
  FS_PRICE_CHILDREN : number = 25
  QS_PRICE_ADULT : number = 15
  FS_PRICE_ADULT : number = 30
  SNACK_PRICE : number = 10

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

  calculateMealPrice(numberQuick : number, numberFull : number, 
    numberChildren : number, numberAdult : number) : CalculatePrice {

    let calculatePrice : CalculatePrice = new CalculatePrice()
    calculatePrice.totalSnackPrice = (numberQuick + numberFull) * this.SNACK_PRICE

    calculatePrice.totalPrice = 
    (numberQuick * this.QS_PRICE_CHILDREN * numberChildren) +
    (numberQuick * this.QS_PRICE_ADULT * numberAdult) +
    (numberFull * this.FS_PRICE_CHILDREN * numberChildren) +
    (numberFull * this.FS_PRICE_ADULT * numberAdult) + calculatePrice.totalSnackPrice

    calculatePrice.pricePerDay = calculatePrice.totalPrice / (numberQuick + numberFull)

    if ((numberQuick + numberFull) < 1)
      calculatePrice.pricePerDay = 0

    return calculatePrice
  }
}



