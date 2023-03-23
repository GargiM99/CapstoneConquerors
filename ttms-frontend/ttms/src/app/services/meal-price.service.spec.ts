import { TestBed } from '@angular/core/testing';

import { MealPriceService } from './meal-price.service';

describe('MealPriceService', () => {
  let service: MealPriceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MealPriceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
