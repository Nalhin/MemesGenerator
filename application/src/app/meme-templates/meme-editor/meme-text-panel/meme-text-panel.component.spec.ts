import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MemeTextPanelComponent } from './meme-text-panel.component';
import { MemeText, Size } from '../meme-text/meme-text.model';

describe('MemeTextPanelComponent', () => {
  let component: MemeTextPanelComponent;
  let fixture: ComponentFixture<MemeTextPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MemeTextPanelComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemeTextPanelComponent);
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
