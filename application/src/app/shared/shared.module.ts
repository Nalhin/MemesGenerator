import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ButtonComponent } from './components/button/button.component';
import { InputComponent } from './components/input/input.component';

@NgModule({
  imports: [CommonModule],
  declarations: [ButtonComponent, InputComponent],
  exports: [CommonModule, ReactiveFormsModule, ButtonComponent, InputComponent],
})
export class SharedModule {}
