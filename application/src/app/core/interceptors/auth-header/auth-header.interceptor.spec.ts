import { TestBed } from '@angular/core/testing';
import { HttpRequest } from '@angular/common/http';
import { of } from 'rxjs';
import { AuthHeaderInterceptor } from './auth-header.interceptor';
import { CookieService } from '../../services/cookie/cookie.service';

describe('BaseUrlInterceptor', () => {
  let interceptor: AuthHeaderInterceptor;
  let cookiesService: CookieService;

  const next: any = {
    handle: (req: HttpRequest<any>) => of(req),
  };

  beforeEach(() =>
    TestBed.configureTestingModule({
      providers: [AuthHeaderInterceptor, CookieService],
    }),
  );

  beforeEach(() => {
    interceptor = TestBed.inject(AuthHeaderInterceptor);
    cookiesService = TestBed.inject(CookieService);
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });

  it('should forward request if auth cookie is not present', () => {
    jest.spyOn(cookiesService, 'getAuthCookie').mockReturnValue(undefined);
    const request = new HttpRequest('GET', '');

    interceptor.intercept(request, next).subscribe((res: any) => {
      expect(res).toBe(request);
    });
  });

  it('should add authorization headers if auth cookie is present', () => {
    const token = 'token';
    jest.spyOn(cookiesService, 'getAuthCookie').mockReturnValue(token);
    const request = new HttpRequest('GET', '');

    interceptor.intercept(request, next).subscribe((res: any) => {
      expect(res.headers.Authorization).toContain(token);
    });
  });
});
