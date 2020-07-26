import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class BaseUrlInterceptor implements HttpInterceptor {
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler,
  ): Observable<HttpEvent<any>> {
    const apiReq = request.clone({
      url: `${process.env.BACKEND_ADDRESS ?? 'http://localhost:8000'}/api/v1${
        request.url
      }`,
    });
    return next.handle(apiReq);
  }
}
