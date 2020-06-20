import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { AnonymousUser, AuthenticatedUser } from './auth-user.model';

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
    const mockLoginUser = {
      email: 'email',
      password: 'password',
    };
    const response = { token: 'ddd' };

    it('should ', (done) => {
      service.signIn(mockLoginUser).subscribe((response) => {
        expect(response).toEqual(response);
        done();
      });

      const req = httpTestingController.expectOne('/auth/sign-up');
      req.flush(response);
    });
  });

  describe('signIn', () => {
    const mockSignUpUser = {
      email: 'email',
      password: 'password',
      username: 'username',
    };
    const response = { token: 'ddd' };

    it('should send signIn request', (done) => {
      service.signIn(mockSignUpUser).subscribe((response) => {
        expect(response).toEqual(response);
        done();
      });

      const req = httpTestingController.expectOne('/auth/sign-up');
      req.flush(response);
    });
  });

  describe('onAuth', () => {
    it('should pipe user after successful authentication', (done) => {
      service.signIn({}).subscribe();
      service.onAuth.subscribe((authUser) => {
        expect(authUser).toBeInstanceOf(AuthenticatedUser);
        done();
      });

      const req = httpTestingController.expectOne('/auth/sign-up');
      req.flush({});
    });
  });

  describe('logout', () => {
    it('should logout current user', () => {
      service.signIn({}).subscribe();
      const req = httpTestingController.expectOne('/auth/sign-up');
      req.flush({});

      service.logout();

      expect(service.user).toBeInstanceOf(AnonymousUser);
    });
  });
});
