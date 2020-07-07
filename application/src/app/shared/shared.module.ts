import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatInput, MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { DraggableDirective } from './directives/draggable.directive';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

@NgModule({
  imports: [
    CommonModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
  ],
  declarations: [DraggableDirective],
  exports: [
    CommonModule,
    ReactiveFormsModule,
    MatInput,
    MatFormField,
    MatButton,
    MatIconModule,
    MatListModule,
    DraggableDirective,
  ],
})
export class SharedModule {}
