import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MemeTemplatesListComponent } from './templates-list/meme-templates-list.component';
import { MemeTemplateDetailsComponent } from './template-details/meme-template-details.component';

const routes: Routes = [
  {
    path: '',
    component: MemeTemplatesListComponent,
  },
  {
    path: ':id',
    component: MemeTemplateDetailsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TemplatesRoutingModule {}
