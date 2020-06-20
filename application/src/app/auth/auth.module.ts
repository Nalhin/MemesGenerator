import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignInComponent } from './sign-in/sign-in.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [SignInComponent, LoginComponent],
  imports: [AuthRoutingModule, CommonModule, ReactiveFormsModule],
})
export class AuthModule {}
