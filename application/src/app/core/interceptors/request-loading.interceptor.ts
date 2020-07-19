import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { RequestLoadingService } from '../services/request-loading.service';
import { finalize } from 'rxjs/operators';

@Injectable()
export class RequestLoadingInterceptor implements HttpInterceptor {
  constructor(private readonly requestLoadingService: RequestLoadingService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler,
  ): Observable<HttpEvent<unknown>> {
    this.requestLoadingService.incrementLoadingCount();
    return next
      .handle(request)
      .pipe(finalize(() => this.requestLoadingService.decrementLoadingCount()));
  }
}
