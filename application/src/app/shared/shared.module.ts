import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ButtonComponent } from './components/button/button.component';
import { InputComponent } from './components/input/input.component';
import { DraggableDirective } from './directives/draggable.directive';

@NgModule({
  imports: [CommonModule],
  declarations: [ButtonComponent, InputComponent, DraggableDirective],
  exports: [
    CommonModule,
    ReactiveFormsModule,
    ButtonComponent,
    InputComponent,
    DraggableDirective,
  ],
})
export class SharedModule {}
