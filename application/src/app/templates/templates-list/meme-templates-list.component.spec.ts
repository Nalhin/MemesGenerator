import {
  async,
  ComponentFixture,
  TestBed,
  fakeAsync,
  tick,
} from '@angular/core/testing';

import { MemeTemplatesListComponent } from './meme-templates-list.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TemplatesService } from '../templates.service';
import { of } from 'rxjs';
import { pageTemplateResponseDtoFactory } from '../../../../test/fixtures/meme-template.fixture';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';

describe('MemeTemplatesListComponent', () => {
  let component: MemeTemplatesListComponent;
  let fixture: ComponentFixture<MemeTemplatesListComponent>;
  let memeTemplatesService: TemplatesService;
  let router: Router;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
      declarations: [MemeTemplatesListComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemeTemplatesListComponent);
    component = fixture.componentInstance;
    memeTemplatesService = TestBed.inject(TemplatesService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should fetch data and display templates', () => {
      const items = pageTemplateResponseDtoFactory.buildOne();
      jest.spyOn(memeTemplatesService, 'getAll').mockReturnValueOnce(of(items));

      component.ngOnInit();
      fixture.detectChanges();

      expect(fixture.debugElement.queryAll(By.css('img')).length).toBe(
        items.content.length,
      );
    });
  });

  describe('component template', () => {
    it('should redirect after a template is clicked', fakeAsync(() => {
      jest.spyOn(router, 'navigateByUrl').mockResolvedValueOnce(true);
      component.memeTemplates$ = of(pageTemplateResponseDtoFactory.buildOne());
      fixture.detectChanges();

      fixture.debugElement.query(By.css('img')).nativeElement.click();
      tick();

      expect(router.navigateByUrl).toHaveBeenCalledTimes(1);
    }));
  });
});
