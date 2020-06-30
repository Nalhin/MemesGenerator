import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MemeCanvasComponent } from './meme-canvas.component';

describe('MemeCanvasComponent', () => {
  let component: MemeCanvasComponent;
  let fixture: ComponentFixture<MemeCanvasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MemeCanvasComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemeCanvasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
