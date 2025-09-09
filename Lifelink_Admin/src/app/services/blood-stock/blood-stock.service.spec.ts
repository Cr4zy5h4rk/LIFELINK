import { TestBed } from '@angular/core/testing';

import { BloodStockService } from './blood-stock.service';

describe('BloodStockService', () => {
  let service: BloodStockService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BloodStockService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
