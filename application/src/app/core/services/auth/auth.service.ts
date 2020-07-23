import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import {
  AnonymousUser,
  AuthenticatedUser,
  User,
} from '../../../shared/models/auth-user.model';
import { distinctUntilChanged, map, tap } from 'rxjs/operators';
import {
  AuthResponseDto,
  LoginUserDto,
  SignUpUserDto,
  UserResponseDto,
} from '../../../shared/interfaces/api.interface';
import { UserService } from '../user/user.service';
import { CookieService } from '../cookie/cookie.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _currentUser: BehaviorSubject<User> = new BehaviorSubject<User>(
    new AnonymousUser(),
  );

  constructor(
    private readonly httpClient: HttpClient,
    private readonly userService: UserService,
    private readonly cookieService: CookieService,
  ) {}

  get user$(): Observable<User> {
    return this._currentUser;
  }

  get currentUser(): User {
    return this._currentUser.value;
  }

  get isAuthenticated$(): Observable<boolean> {
    return this._currentUser.pipe(
      map((u) => u.isAuthenticated),
      distinctUntilChanged(),
    );
  }

  public async onInit(): Promise<void> {
    const token = this.cookieService.getAuthCookie();

    if (!token) {
      return;
    }

    try {
      const user = await this.userService.me().toPromise();
      this._currentUser.next(new AuthenticatedUser(user));
    } catch (e) {
      this.cookieService.removeAuthCookie();
    }
  }

  public login(loginUser: LoginUserDto) {
    return this.httpClient
      .post<AuthResponseDto>('/auth/login', loginUser)
      .pipe(tap(({ user, token }) => this.authenticate(user, token)));
  }

  public signUp(signUpUser: SignUpUserDto) {
    return this.httpClient
      .post<AuthResponseDto>('/auth/sign-up', signUpUser)
      .pipe(tap(({ user, token }) => this.authenticate(user, token)));
  }

  private authenticate(user: UserResponseDto, token: string) {
    this._currentUser.next(new AuthenticatedUser(user));
    this.cookieService.setAuthCookie(token);
  }

  public logout() {
    this._currentUser.next(new AnonymousUser());
    this.cookieService.removeAuthCookie();
  }
}
