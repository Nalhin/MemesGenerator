import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { MemeEditorComponent } from './meme-editor.component';
import { MemeCanvasComponent } from './meme-canvas/meme-canvas.component';
import { MemeTextComponent } from './meme-text/meme-text.component';
import { MemeTextPanelComponent } from './meme-text-panel/meme-text-panel.component';

@NgModule({
  declarations: [
    MemeEditorComponent,
    MemeCanvasComponent,
    MemeTextComponent,
    MemeTextPanelComponent,
  ],
  imports: [SharedModule],
})
export class MemeEditorModule {}
