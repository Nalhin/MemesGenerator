import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';

import { FooterComponent } from './footer/footer.component';
import { NavigationComponent } from './navigation/navigation.component';
import { RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';

@NgModule({
  declarations: [FooterComponent, NavigationComponent, LayoutComponent],
  imports: [SharedModule, RouterModule],
  exports: [LayoutComponent],
})
export class LayoutModule {}
