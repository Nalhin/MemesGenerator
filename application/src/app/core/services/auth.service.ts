import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import {
  AnonymousUser,
  AuthenticatedUser,
  User,
} from '../../shared/models/auth-user.model';
import { filter, map, tap } from 'rxjs/operators';
import {
  AuthResponseDto,
  LoginUserDto,
  SignUpUserDto,
} from '../../shared/interfaces/api.interface';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _user: BehaviorSubject<User> = new BehaviorSubject<User>(
    new AnonymousUser(),
  );

  constructor(
    private readonly httpClient: HttpClient,
    private readonly userService: UserService,
  ) {}

  async onInit(): Promise<void> {
    const user = await this.userService.me().toPromise();
    this._user.next(new AuthenticatedUser(user));
  }

  login(loginUser: LoginUserDto) {
    return this.httpClient.post<AuthResponseDto>('/auth/login', loginUser).pipe(
      tap((res) => {
        this._user.next(new AuthenticatedUser(res));
      }),
    );
  }

  signUp(signUpUser: SignUpUserDto) {
    return this.httpClient
      .post<AuthResponseDto>('/auth/sign-up', signUpUser)
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
