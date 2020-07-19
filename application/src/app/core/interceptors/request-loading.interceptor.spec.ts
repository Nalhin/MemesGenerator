import { TestBed } from '@angular/core/testing';

import { RequestLoadingInterceptor } from './request-loading.interceptor';
import { HttpRequest } from '@angular/common/http';
import { RequestLoadingService } from '../services/request-loading.service';
import { of } from 'rxjs';

describe('RequestLoadingInterceptor', () => {
  let interceptor: RequestLoadingInterceptor;
  let requestLoadingService: RequestLoadingService;

  beforeEach(() =>
    TestBed.configureTestingModule({
      providers: [RequestLoadingInterceptor],
    }),
  );

  beforeEach(() => {
    interceptor = TestBed.inject(RequestLoadingInterceptor);
    requestLoadingService = TestBed.inject(RequestLoadingService);
  });

  const next: any = {
    handle: (req: HttpRequest<any>) => of(req),
  };
  const request = new HttpRequest('GET', '');

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });

  it('should increment loading count when initialized', (done) => {
    jest.spyOn(requestLoadingService, 'incrementLoadingCount');

    interceptor.intercept(request, next).subscribe(() => {
      expect(requestLoadingService.incrementLoadingCount).toBeCalledTimes(1);
      done();
    });
  });

  it('should decrement loading count after finalization', () => {
    jest.spyOn(requestLoadingService, 'incrementLoadingCount');
    jest.spyOn(requestLoadingService, 'decrementLoadingCount');

    interceptor.intercept(request, next).subscribe(() => {
      expect(requestLoadingService.decrementLoadingCount).toBeCalledTimes(1);
    });

    expect(requestLoadingService.incrementLoadingCount).toBeCalledTimes(1);
  });
});
