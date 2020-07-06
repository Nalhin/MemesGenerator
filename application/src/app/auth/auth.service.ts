import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { AnonymousUser, AuthenticatedUser, User } from './auth-user.model';
import { filter, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _user: BehaviorSubject<User> = new BehaviorSubject<User>(
    new AnonymousUser(),
  );

  constructor(private readonly httpClient: HttpClient) {}

  login(loginUser: Api.LoginUsingPOST.Parameters.LoginUserDto) {
    return this.httpClient
      .post<Api.LoginUsingPOST.Responses.$200>('/auth/login', loginUser)
      .pipe(
        tap((res) => {
          this._user.next(new AuthenticatedUser(res));
        }),
      );
  }

  signIn(signUpUser: Api.SignUpUsingPOST.Parameters.SignUpUserDto) {
    return this.httpClient
      .post<Api.SignUpUsingPOST.Responses.$200>('/auth/sign-up', signUpUser)
      .pipe(tap((user) => this._user.next(new AuthenticatedUser(user))));
  }

  get user() {
    return this._user.value;
  }

  onAuth(): Observable<User> {
    return this._user.pipe(filter((user) => user.isAuthenticated));
  }

  isAuthenticated(): Observable<boolean> {
    return this._user.pipe(map((u) => u.isAuthenticated));
  }

  logout() {
    this._user.next(new AnonymousUser());
  }
}
