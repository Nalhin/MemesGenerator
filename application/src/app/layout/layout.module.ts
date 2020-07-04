import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';

import { FooterComponent } from './footer/footer.component';
import { NavigationComponent } from './navigation/navigation.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [FooterComponent, NavigationComponent],
  imports: [SharedModule, RouterModule],
  exports: [NavigationComponent, FooterComponent],
})
export class LayoutModule {}
