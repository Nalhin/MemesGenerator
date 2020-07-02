import {
  async,
  ComponentFixture,
  fakeAsync,
  TestBed,
  tick,
} from '@angular/core/testing';

import { MemeDetailsComponent } from './meme-details.component';
import { of } from 'rxjs';
import { MemesService } from '../memes.service';
import { memeResponseDtoFactory } from '../../../../test/fixtures/meme.fixture';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('MemeDetailsComponent', () => {
  let component: MemeDetailsComponent;
  let fixture: ComponentFixture<MemeDetailsComponent>;
  let memesService: MemesService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      declarations: [MemeDetailsComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemeDetailsComponent);
    component = fixture.componentInstance;
    memesService = TestBed.inject(MemesService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should retrieve id from path and fetch template', fakeAsync(() => {
      jest
        .spyOn(memesService, 'getOneById')
        .mockReturnValueOnce(of(memeResponseDtoFactory.buildOne()));

      component.ngOnInit();
      component.meme$.subscribe();
      tick();

      expect(memesService.getOneById).toBeCalledWith(0);
    }));
  });
});
