import { Component, Input } from '@angular/core';
import { MemeText, Size } from './meme-text/meme-text.model';
import { TemplateResponseDto } from '../../shared/interfaces/api.interface';

@Component({
  selector: 'app-meme-editor',
  templateUrl: './meme-editor.component.html',
})
export class MemeEditorComponent {
  @Input() memeTemplateDetails: TemplateResponseDto;

  memeTexts: MemeText[] = [
    new MemeText({
      id: 1,
      position: { x: 1, y: 1 },
      size: new Size({ width: 80, height: 15 }),
      text: 'top',
    }),
    new MemeText({
      id: 2,
      position: { x: 1, y: 200 },
      size: new Size({ width: 80, height: 15 }),
      text: 'bottom',
    }),
  ];

  memeTextChange(newMemeText: MemeText) {
    this.memeTexts = this.memeTexts.map((memeText) =>
      memeText.id === newMemeText.id ? newMemeText : memeText,
    );
  }

  trackByFn(index: number, memeText: MemeText) {
    return memeText.id;
  }
}
