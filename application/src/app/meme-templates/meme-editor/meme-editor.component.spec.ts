import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MemeEditorComponent } from './meme-editor.component';
import { MemeCanvasComponent } from './meme-canvas/meme-canvas.component';
import { MemeTextComponent } from './meme-text/meme-text.component';
import { MemeTextPanelComponent } from './meme-text-panel/meme-text-panel.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('EditorComponent', () => {
  let component: MemeEditorComponent;
  let fixture: ComponentFixture<MemeEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [
        MemeEditorComponent,
        MemeCanvasComponent,
        MemeTextComponent,
        MemeTextPanelComponent,
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemeEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
