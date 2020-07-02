import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MemesListComponent } from './memes-list/memes-list.component';
import { MemeDetailsComponent } from './meme-details/meme-details.component';

const routes: Routes = [
  {
    path: '',
    component: MemesListComponent,
  },
  {
    path: ':id',
    component: MemeDetailsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MemesRoutingModule {}
