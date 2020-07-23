import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';

import { AuthService } from './auth.service';
import {
  authResponseDtoFactory,
  loginUserDtoFactory,
  signUpUserDtoFactory,
} from '../../../../../test/fixtures/auth.fixture';
import { skip } from 'rxjs/operators';
import { CookieService } from '../cookie/cookie.service';
import { UserService } from '../user/user.service';
import { of, throwError } from 'rxjs';
import { userResponseDtoFactory } from '../../../../../test/fixtures/users.fixture';

describe('AuthService', () => {
  let service: AuthService;
  let cookieService: CookieService;
  let userService: UserService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(AuthService);
    cookieService = TestBed.inject(CookieService);
    userService = TestBed.inject(UserService);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  function randomAuth() {
    const response = authResponseDtoFactory.buildOne();
    service.login(loginUserDtoFactory.buildOne()).subscribe();
    const req = httpTestingController.expectOne('/auth/login');
    req.flush(response);

    return response;
  }

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('currentUser$', () => {
    it('should return unauthenticated user as first value', (done) => {
      service.user$.subscribe((user) => {
        expect(user.isAuthenticated).toBeFalsy();
        done();
      });
    });

    it('should return user after authentication', (done) => {
      const response = randomAuth();

      service.user$.subscribe((user) => {
        expect(user.username).toBe(response.user.username);
        done();
      });
    });
  });

  describe('isAuthenticated$', () => {
    it('should return false as first value', (done) => {
      service.isAuthenticated$.subscribe((isAuthenticated) => {
        expect(isAuthenticated).toBeFalsy();
        done();
      });
    });

    it('should return true after successful auth', (done) => {
      service.isAuthenticated$.pipe(skip(1)).subscribe((isAuthenticated) => {
        expect(isAuthenticated).toBeTruthy();
        done();
      });

      randomAuth();
    });

    it('should not duplicate values', (done) => {
      service.isAuthenticated$.pipe(skip(1)).subscribe((isAuthenticated) => {
        expect(isAuthenticated).toBeTruthy();
        done();
      });

      service.logout();
      service.logout();

      randomAuth();
    });
  });

  describe('onInit', () => {
    it('should return early if token is not present', async () => {
      jest.spyOn(cookieService, 'getAuthCookie').mockReturnValueOnce('');
      jest.spyOn(userService, 'me');

      await service.onInit();

      expect(cookieService.getAuthCookie).toBeCalledTimes(1);
      expect(userService.me).not.toBeCalled();
    });

    it('should authenticate user if user data is returned', async (done) => {
      jest.spyOn(cookieService, 'getAuthCookie').mockReturnValueOnce('token');
      const response = userResponseDtoFactory.buildOne();
      jest.spyOn(userService, 'me').mockReturnValueOnce(of(response));

      await service.onInit();

      service.user$.subscribe((user) => {
        expect(user.username).toBe(response.username);
        done();
      });
      expect(cookieService.getAuthCookie).toBeCalledTimes(1);
      expect(userService.me).toBeCalledTimes(1);
    });

    it('should remove auth cookie if api request is unsuccessful', async () => {
      jest.spyOn(cookieService, 'getAuthCookie').mockReturnValueOnce('token');
      jest.spyOn(cookieService, 'removeAuthCookie');
      jest.spyOn(userService, 'me').mockReturnValueOnce(throwError('das'));

      await service.onInit();

      expect(cookieService.getAuthCookie).toBeCalledTimes(1);
      expect(userService.me).toBeCalledTimes(1);
      expect(cookieService.removeAuthCookie).toBeCalledTimes(1);
    });
  });

  describe('login', () => {
    const loginUserDto = loginUserDtoFactory.buildOne();
    const response = authResponseDtoFactory.buildOne();

    it('should login user correctly', (done) => {
      service.user$.pipe(skip(1)).subscribe((user) => {
        expect(user.isAuthenticated).toBeTruthy();
        done();
      });

      service.login(loginUserDto).subscribe();

      const req = httpTestingController.expectOne('/auth/login');
      req.flush(response);
    });
  });

  describe('signIn', () => {
    const mockSignUpUser = signUpUserDtoFactory.buildOne();
    const response = authResponseDtoFactory.buildOne();

    it('should signIn user correctly', (done) => {
      service.signUp(mockSignUpUser).subscribe(() => {
        expect(response).toEqual(response);
        done();
      });

      const req = httpTestingController.expectOne('/auth/sign-up');
      req.flush(response);
    });
  });

  describe('logout', () => {
    it('should logout the current user', (done) => {
      randomAuth();

      service.user$.pipe(skip(1)).subscribe((user) => {
        expect(user.isAuthenticated).toBeFalsy();
        done();
      });

      service.logout();
    });
  });
});
