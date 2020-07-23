import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { distinctUntilChanged, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class LoadingService {
  private readonly _loadingRequests: BehaviorSubject<
    number
  > = new BehaviorSubject(0);

  incrementLoadingRequests(): void {
    this._loadingRequests.next(this._loadingRequests.value + 1);
  }

  decrementLoadingRequests(): void {
    this._loadingRequests.next(this._loadingRequests.value - 1);
  }

  isLoading(): Observable<boolean> {
    return this._loadingRequests.pipe(map(Boolean), distinctUntilChanged());
  }
}
