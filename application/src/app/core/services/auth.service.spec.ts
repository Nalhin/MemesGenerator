import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import {
  AnonymousUser,
  AuthenticatedUser,
} from '../../shared/models/auth-user.model';
import { AuthService } from './auth.service';
import {
  authResponseDtoFactory,
  loginUserDtoFactory,
  signUpUserDtoFactory,
} from '../../../../test/fixtures/auth.fixture';

describe('AuthService', () => {
  let service: AuthService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(AuthService);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('login', () => {
    const loginUserDto = loginUserDtoFactory.buildOne();
    const response = authResponseDtoFactory.buildOne();

    it('should login user correctly', (done) => {
      service.login(loginUserDto).subscribe(() => {
        expect(service.user).toBeInstanceOf(AuthenticatedUser);
        done();
      });

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

  describe('onAuth', () => {
    it('should activate after successful authentication', (done) => {
      service.signUp(signUpUserDtoFactory.buildOne()).subscribe();
      service.onAuth().subscribe((authUser) => {
        expect(authUser).toBeInstanceOf(AuthenticatedUser);
        done();
      });

      const req = httpTestingController.expectOne('/auth/sign-up');
      req.flush(authResponseDtoFactory.buildOne());
    });
  });

  describe('logout', () => {
    it('should logout the current user', () => {
      service.signUp(signUpUserDtoFactory.buildOne()).subscribe();

      const req = httpTestingController.expectOne('/auth/sign-up');
      req.flush(authResponseDtoFactory.buildOne());

      service.logout();

      expect(service.user).toBeInstanceOf(AnonymousUser);
    });
  });
});
