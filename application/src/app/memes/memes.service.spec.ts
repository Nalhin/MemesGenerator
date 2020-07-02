import { TestBed } from '@angular/core/testing';

import { MemesService } from './memes.service';
import {
  memeResponseDtoFactory,
  pageMemeResponseDtoFactory,
  saveMemeDtoFactory,
} from '../../../test/fixtures/meme.fixture';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';

describe('MemesServiceService', () => {
  let service: MemesService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(MemesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getAll', () => {
    const response = pageMemeResponseDtoFactory.buildOne();

    it('should return paginated meme templates', (done) => {
      const page = 1;

      service.getAll(page).subscribe((res) => {
        expect(res).toBe(response);
        done();
      });

      const req = httpTestingController.expectOne(`/api/memes?page=${page}`);
      req.flush(response);
    });
  });

  describe('getOneById', () => {
    const response = memeResponseDtoFactory.buildOne();

    it('should return user with given id', (done) => {
      service.getOneById(response.id).subscribe((res) => {
        expect(res).toBe(response);
        done();
      });

      const req = httpTestingController.expectOne(`/api/memes/${response.id}`);
      req.flush(response);
    });
  });

  describe('save', () => {
    const saveMemeDto = saveMemeDtoFactory.buildOne();
    const response = memeResponseDtoFactory.buildOne();

    it('should send save request and return saved template', (done) => {
      service.save(new Blob(), saveMemeDto).subscribe((res) => {
        expect(res).toBe(response);
        done();
      });

      const req = httpTestingController.expectOne('/api/memes/save');
      req.flush(response);
    });
  });
});
