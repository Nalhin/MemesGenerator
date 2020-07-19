import { TestBed } from '@angular/core/testing';

import { RequestLoadingService } from './request-loading.service';

describe('RequestLoadingService', () => {
  let service: RequestLoadingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RequestLoadingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
