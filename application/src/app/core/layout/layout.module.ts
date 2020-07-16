import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';

import { FooterComponent } from './footer/footer.component';
import { NavigationComponent } from './navigation/navigation.component';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';

@NgModule({
  declarations: [FooterComponent, NavigationComponent],
  imports: [SharedModule, RouterModule, MatToolbarModule, MatSidenavModule],
  exports: [NavigationComponent, FooterComponent],
})
export class LayoutModule {}
