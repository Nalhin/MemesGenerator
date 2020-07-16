import { NgModule } from '@angular/core';
import { SignUpComponent } from './sign-up/sign-up.component';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { SharedModule } from '../shared/shared.module';
import { LogoutComponent } from './logout/logout.component';

@NgModule({
  declarations: [SignUpComponent, LoginComponent, LogoutComponent],
  imports: [AuthRoutingModule, SharedModule],
})
export class AuthModule {}
