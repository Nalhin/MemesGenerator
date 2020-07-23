import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { CookieService } from '../../services/cookie/cookie.service';

@Injectable()
export class AuthHeaderInterceptor implements HttpInterceptor {
  constructor(private readonly cookiesService: CookieService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler,
  ): Observable<HttpEvent<any>> {
    const token = this.cookiesService.getAuthCookie();

    const apiReq = token
      ? request.clone({
          headers: new HttpHeaders({
            Authorization: `Bearer ${token}`,
          }),
        })
      : request;
    return next.handle(apiReq);
  }
}
