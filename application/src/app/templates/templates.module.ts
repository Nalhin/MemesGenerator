import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';

import { TemplatesRoutingModule } from './templates-routing.module';
import { MemeTemplatesListComponent } from './templates-list/meme-templates-list.component';
import { MemeTemplateDetailsComponent } from './template-details/meme-template-details.component';
import { MemeEditorModule } from './meme-editor/meme-editor.module';

@NgModule({
  declarations: [MemeTemplatesListComponent, MemeTemplateDetailsComponent],
  imports: [SharedModule, TemplatesRoutingModule, MemeEditorModule],
})
export class TemplatesModule {}
