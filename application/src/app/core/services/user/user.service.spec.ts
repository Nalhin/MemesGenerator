import { TestBed } from '@angular/core/testing';
import { UserService } from './user.service';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { userResponseDtoFactory } from '../../../../../test/fixtures/users.fixture';

describe('UserService', () => {
  let service: UserService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(UserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('me', () => {
    it('should fetch current user', (done) => {
      const response = userResponseDtoFactory.buildOne();

      service.me().subscribe((result) => {
        expect(result).toBe(response);
        done();
      });

      const req = httpTestingController.expectOne('/api/users/me');
      req.flush(response);
    });
  });
});
