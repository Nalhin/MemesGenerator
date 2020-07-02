import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';

import { MemesRoutingModule } from './memes-routing.module';
import { MemesListComponent } from './memes-list/memes-list.component';
import { MemeDetailsComponent } from './meme-details/meme-details.component';

@NgModule({
  declarations: [MemesListComponent, MemeDetailsComponent],
  imports: [SharedModule, MemesRoutingModule],
})
export class MemesModule {}
