import { TestBed } from '@angular/core/testing';

import { CarnetService } from './carnet.service';

describe('CarnetService', () => {
  let service: CarnetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarnetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
