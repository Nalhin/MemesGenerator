import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MemesListComponent } from './memes-list.component';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MemesService } from '../memes.service';
import { pageMemeResponseDtoFactory } from '../../../../test/fixtures/meme.fixture';

describe('MemesListComponent', () => {
  let component: MemesListComponent;
  let fixture: ComponentFixture<MemesListComponent>;
  let memesService: MemesService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
      declarations: [MemesListComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemesListComponent);
    component = fixture.componentInstance;
    memesService = TestBed.inject(MemesService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should fetch data and display templates', () => {
      const items = pageMemeResponseDtoFactory.buildOne();
      jest.spyOn(memesService, 'getAll').mockReturnValueOnce(of(items));

      component.ngOnInit();
      fixture.detectChanges();

      expect(fixture.debugElement.queryAll(By.css('img')).length).toBe(
        items.content.length,
      );
    });
  });
});
