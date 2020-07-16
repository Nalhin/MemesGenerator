import { TestBed } from '@angular/core/testing';

import { NoAuthGuard } from './no-auth.guard';
import { AuthService } from '../services/auth.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import {
  AnonymousUser,
  AuthenticatedUser,
} from '../../shared/models/auth-user.model';

describe('NoAuthGuard', () => {
  let guard: NoAuthGuard;
  let authService: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    guard = TestBed.inject(NoAuthGuard);
    authService = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  describe('canActivate', () => {
    it('should be falsy if user is authenticated', () => {
      jest
        .spyOn(authService, 'user', 'get')
        .mockReturnValueOnce(new AuthenticatedUser({}));

      const result = guard.canActivate({} as any, null as any);

      expect(result).toBeFalsy();
    });

    it('should be truthy if user is not authenticated', () => {
      jest
        .spyOn(authService, 'user', 'get')
        .mockReturnValueOnce(new AnonymousUser());

      const result = guard.canActivate({} as any, null as any);

      expect(result).toBeTruthy();
    });
  });
});
