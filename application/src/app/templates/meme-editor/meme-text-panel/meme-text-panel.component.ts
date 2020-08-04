import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MemeText } from '../meme-text/meme-text.model';

@Component({
  selector: 'app-meme-text-panel',
  templateUrl: './meme-text-panel.component.html',
})
export class MemeTextPanelComponent {
  @Input() memeText: MemeText;

  @Output() memeTextChange = new EventEmitter<MemeText>();

  change(event: any) {
    const newMemeText = new MemeText({
      ...this.memeText,
      text: event.target.value,
    });
    this.memeTextChange.emit(newMemeText);
  }
}
