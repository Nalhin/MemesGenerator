import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { distinctUntilChanged, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class RequestLoadingService {
  private readonly _loadingCount: BehaviorSubject<number> = new BehaviorSubject(
    0,
  );

  incrementLoadingCount(): void {
    this._loadingCount.next(this._loadingCount.value + 1);
  }

  decrementLoadingCount(): void {
    this._loadingCount.next(this._loadingCount.value - 1);
  }

  isLoading(): Observable<boolean> {
    return this._loadingCount.pipe(map(Boolean), distinctUntilChanged());
  }
}
