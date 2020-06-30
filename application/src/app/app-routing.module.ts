import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NoAuthGuard } from './shared/guards/no-auth.guard';
import { MemeEditorComponent } from './meme-editor/meme-editor.component';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
    canActivate: [NoAuthGuard],
  },
  {
    path: 'editor',
    component: MemeEditorComponent,
  },
  {
    path: 'templates',
    loadChildren: () =>
      import('./meme-templates/meme-templates.module').then(
        (m) => m.MemeTemplatesModule,
      ),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
