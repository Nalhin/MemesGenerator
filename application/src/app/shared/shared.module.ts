import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { DraggableDirective } from './directives/draggable.directive';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatProgressBarModule } from '@angular/material/progress-bar';

const MAT_MODULES = [
  MatInputModule,
  MatFormFieldModule,
  MatButtonModule,
  MatIconModule,
  MatListModule,
  MatCardModule,
  MatToolbarModule,
  MatSidenavModule,
  MatProgressBarModule,
];

const SHARED_MODULES = [CommonModule, ReactiveFormsModule];

@NgModule({
  imports: [...MAT_MODULES, ...SHARED_MODULES],
  declarations: [DraggableDirective],
  exports: [...MAT_MODULES, ...SHARED_MODULES, DraggableDirective],
})
export class SharedModule {}
