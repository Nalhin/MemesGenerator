import {
  Directive,
  EventEmitter,
  HostListener,
  OnInit,
  Output,
} from '@angular/core';
import { map, mergeMap, takeUntil } from 'rxjs/operators';
import { DragPositionInterface } from '../interfaces/drag-position.interface';

@Directive({
  selector: '[dragged]',
})
export class DraggableDirective implements OnInit {
  mouseUp = new EventEmitter<MouseEvent>();
  mouseDown = new EventEmitter<MouseEvent>();
  mouseMove = new EventEmitter<MouseEvent>();

  @Output()
  dragged = new EventEmitter<DragPositionInterface>();

  @HostListener('document:mouseup', ['$event'])
  onMouseUp(event: MouseEvent) {
    this.mouseUp.emit(event);
  }

  @HostListener('mousedown', ['$event'])
  onMouseDown(event: MouseEvent) {
    this.mouseDown.emit(event);
    event.stopPropagation();
  }

  @HostListener('document:mousemove', ['$event'])
  onMouseMove(event: MouseEvent) {
    this.mouseMove.emit(event);
  }

  ngOnInit(): void {
    this.mouseDown
      .pipe(
        mergeMap((down) =>
          this.mouseMove.pipe(
            map((move) => ({
              x: move.pageX - down.offsetX,
              y: move.pageY - down.offsetY,
            })),
            takeUntil(this.mouseUp),
          ),
        ),
      )
      .subscribe((pos) => {
        this.dragged.emit(pos);
      });
  }
}
