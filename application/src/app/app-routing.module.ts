import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'templates',
    loadChildren: () =>
      import('./meme-templates/meme-templates.module').then(
        (m) => m.MemeTemplatesModule,
      ),
  },
  {
    path: 'memes',
    loadChildren: () =>
      import('./memes/memes.module').then((m) => m.MemesModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
