import { TestBed } from '@angular/core/testing';

import { AuthGuard } from './auth.guard';
import { AuthService } from '../services/auth.service';
import {
  AnonymousUser,
  AuthenticatedUser,
} from '../../shared/models/auth-user.model';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let authService: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    guard = TestBed.inject(AuthGuard);
    authService = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  describe('canActivate', () => {
    it('should be truthy if user is authenticated', () => {
      jest
        .spyOn(authService, 'user', 'get')
        .mockReturnValueOnce(new AuthenticatedUser({}));

      const result = guard.canActivate({} as any, null as any);

      expect(result).toBeTruthy();
    });

    it('should be falsy if user is not authenticated', () => {
      jest
        .spyOn(authService, 'user', 'get')
        .mockReturnValueOnce(new AnonymousUser());

      const result = guard.canActivate({} as any, null as any);

      expect(result).toBeFalsy();
    });
  });
});
