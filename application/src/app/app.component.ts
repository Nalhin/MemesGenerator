import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  styleUrls: ['./app.component.scss'],
  template: ` <div class="app-container mat-app-background">
    <app-navigation>
      <router-outlet></router-outlet>
      <app-footer></app-footer>
    </app-navigation>
  </div>`,
})
export class AppComponent {}
