import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { AnonymousUser, AuthenticatedUser, User } from './auth-user.model';
import { filter, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _user: BehaviorSubject<User> = new BehaviorSubject<User>(
    new AnonymousUser(),
  );

  constructor(private readonly httpClient: HttpClient) {}

  login(loginUser: Api.LoginUserDto) {
    return this.httpClient
      .post<Api.AuthResponseDto>('/auth/login', loginUser)
      .pipe(
        tap((res) => {
          this._user.next(new AuthenticatedUser(res));
        }),
      );
  }

  signIn(signUpUser: Api.SignUpUserDto) {
    return this.httpClient
      .post<Api.SignUpUserDto>('/auth/sign-up', signUpUser)
      .pipe(tap((user) => this._user.next(new AuthenticatedUser(user))));
  }

  get user() {
    return this._user.value;
  }

  get onAuth(): Observable<User> {
    return this._user.pipe(filter((user) => user.isAuthenticated));
  }

  logout() {
    this._user.next(new AnonymousUser());
  }
}
