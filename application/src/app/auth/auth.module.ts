import { NgModule } from '@angular/core';
import { SignInComponent } from './sign-in/sign-in.component';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { SharedModule } from '../shared/shared.module';
import { LogoutComponent } from './logout/logout.component';

@NgModule({
  declarations: [SignInComponent, LoginComponent, LogoutComponent],
  imports: [AuthRoutingModule, SharedModule],
})
export class AuthModule {}
