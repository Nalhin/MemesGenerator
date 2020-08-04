import {
  async,
  ComponentFixture,
  fakeAsync,
  TestBed,
  tick,
} from '@angular/core/testing';

import { MemeTemplateDetailsComponent } from './meme-template-details.component';
import { MemeEditorModule } from '../meme-editor/meme-editor.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { TemplatesService } from '../templates.service';
import { of } from 'rxjs';
import { templateResponseDtoFactory } from '../../../../test/fixtures/meme-template.fixture';

describe('MemeTemplateDetailsComponent', () => {
  let component: MemeTemplateDetailsComponent;
  let fixture: ComponentFixture<MemeTemplateDetailsComponent>;
  let memeTemplatesService: TemplatesService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [MemeEditorModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [MemeTemplateDetailsComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemeTemplateDetailsComponent);
    component = fixture.componentInstance;
    memeTemplatesService = TestBed.inject(TemplatesService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should retrieve id from path and fetch template', fakeAsync(() => {
      jest
        .spyOn(memeTemplatesService, 'getOneById')
        .mockReturnValueOnce(of(templateResponseDtoFactory.buildOne()));

      component.ngOnInit();
      component.memeTemplateDetails$.subscribe();
      tick();

      expect(memeTemplatesService.getOneById).toBeCalledWith(0);
    }));
  });
});
