import { TestBed } from '@angular/core/testing';
import { LoadingInterceptor } from './loading.interceptor';
import { HttpRequest } from '@angular/common/http';
import { LoadingService } from '../services/loading.service';
import { of } from 'rxjs';

describe('LoadingInterceptor', () => {
  let interceptor: LoadingInterceptor;
  let loadingService: LoadingService;

  beforeEach(() =>
    TestBed.configureTestingModule({
      providers: [LoadingInterceptor],
    }),
  );

  beforeEach(() => {
    interceptor = TestBed.inject(LoadingInterceptor);
    loadingService = TestBed.inject(LoadingService);
  });

  const next: any = {
    handle: (req: HttpRequest<any>) => of(req),
  };
  const request = new HttpRequest('GET', '');

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });

  it('should increment loading count when initialized', (done) => {
    jest.spyOn(loadingService, 'incrementLoadingRequests');

    interceptor.intercept(request, next).subscribe(() => {
      expect(loadingService.incrementLoadingRequests).toBeCalledTimes(1);
      done();
    });
  });

  it('should decrement loading count after finalization', () => {
    jest.spyOn(loadingService, 'incrementLoadingRequests');
    jest.spyOn(loadingService, 'decrementLoadingRequests');

    interceptor.intercept(request, next).subscribe(() => {
      expect(loadingService.decrementLoadingRequests).toBeCalledTimes(1);
    });

    expect(loadingService.incrementLoadingRequests).toBeCalledTimes(1);
  });
});
