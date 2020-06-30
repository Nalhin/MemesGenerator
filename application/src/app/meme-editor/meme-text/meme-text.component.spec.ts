import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MemeTextComponent } from './meme-text.component';
import { MemeText, Size } from './meme-text.model';

describe('MemeTextComponent', () => {
  let component: MemeTextComponent;
  let fixture: ComponentFixture<MemeTextComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MemeTextComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemeTextComponent);
    component = fixture.componentInstance;
    component.memeText = new MemeText({
      id: 1,
      position: { x: 1, y: 1 },
      size: new Size({ width: 80, height: 15 }),
      text: 'top',
    });
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
