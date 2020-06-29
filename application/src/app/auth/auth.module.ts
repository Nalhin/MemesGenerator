import { NgModule } from '@angular/core';
import { SignInComponent } from './sign-in/sign-in.component';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [SignInComponent, LoginComponent],
  imports: [AuthRoutingModule, SharedModule],
})
export class AuthModule {}
