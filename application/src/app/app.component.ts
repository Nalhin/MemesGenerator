import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  styleUrls: ['./app.component.scss'],
  template: ` <div class="app-container mat-app-background">
    <app-layout>
      <router-outlet></router-outlet>
    </app-layout>
  </div>`,
})
export class AppComponent {}
