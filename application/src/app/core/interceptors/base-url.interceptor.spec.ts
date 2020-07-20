import { TestBed } from '@angular/core/testing';

import { HttpRequest } from '@angular/common/http';
import { of } from 'rxjs';
import { BaseUrlInterceptor } from './base-url.interceptor';

describe('BaseUrlInterceptor', () => {
  let interceptor: BaseUrlInterceptor;
  const next: any = {
    handle: (req: HttpRequest<any>) => of(req),
  };

  beforeEach(() =>
    TestBed.configureTestingModule({
      providers: [BaseUrlInterceptor],
    }),
  );

  beforeEach(() => {
    interceptor = TestBed.inject(BaseUrlInterceptor);
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });

  it('should append base url to the request url', () => {
    const url = '/data';
    const request = new HttpRequest('GET', url);

    interceptor.intercept(request, next).subscribe((res: any) => {
      expect(res.url).not.toBe(request.url);
      expect(res.url).toContain(url);
    });
  });
});
