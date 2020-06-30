import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';

import { MemeTemplatesRoutingModule } from './meme-templates-routing.module';
import { MemeTemplatesListComponent } from './meme-templates-list/meme-templates-list.component';
import { MemeTemplateDetailsComponent } from './meme-template-details/meme-template-details.component';
import { MemeEditorModule } from '../meme-editor/meme-editor.module';

@NgModule({
  declarations: [MemeTemplatesListComponent, MemeTemplateDetailsComponent],
  imports: [SharedModule, MemeTemplatesRoutingModule, MemeEditorModule],
})
export class MemeTemplatesModule {}
