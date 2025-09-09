import { TestBed } from '@angular/core/testing';

import { ShareInfoRequestServiceService } from './share-info-request-service.service';

describe('ShareInfoRequestServiceService', () => {
  let service: ShareInfoRequestServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShareInfoRequestServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
