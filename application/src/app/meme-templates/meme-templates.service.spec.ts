import { TestBed } from '@angular/core/testing';

import { MemeTemplatesService } from './meme-templates.service';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import {
  pageTemplateResponseDtoFactory,
  saveTemplateDtoFactory,
  templateResponseDtoFactory,
} from '../../../test/fixtures/meme-template.fixture';
import { memeResponseDtoFactory } from '../../../test/fixtures/meme.fixture';

describe('TemplatesService', () => {
  let service: MemeTemplatesService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(MemeTemplatesService);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getAll', () => {
    const response = pageTemplateResponseDtoFactory.buildOne();

    it('should return paginated meme templates', (done) => {
      const page = 1;

      service.getAll(page).subscribe((res) => {
        expect(res).toBe(response);
        done();
      });

      const req = httpTestingController.expectOne(
        `/api/templates?page=${page}`,
      );
      req.flush(response);
    });
  });

  describe('getOneById', () => {
    const response = templateResponseDtoFactory.buildOne();

    it('should return user with given id', (done) => {
      service.getOneById(response.id).subscribe((res) => {
        expect(res).toBe(response);
        done();
      });

      const req = httpTestingController.expectOne(
        `/api/templates/${response.id}`,
      );
      req.flush(response);
    });
  });

  describe('save', () => {
    const saveTemplateDto = saveTemplateDtoFactory.buildOne();
    const response = memeResponseDtoFactory.buildOne();

    it('should send save request and return saved template', (done) => {
      service.save(saveTemplateDto).subscribe((res) => {
        expect(res).toBe(response);
        done();
      });

      const req = httpTestingController.expectOne('/api/templates/save');
      req.flush(response);
    });
  });
});
