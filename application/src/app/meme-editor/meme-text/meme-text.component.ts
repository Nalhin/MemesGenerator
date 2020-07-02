import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { MemeText } from './meme-text.model';
import { DragPosition } from '../../shared/interfaces/dragPosition';
import { DraggablePosition } from './draggable-position.model';

@Component({
  selector: 'app-meme-text',
  template: ` <div
    #container
    class="absolute border-2 border-black select-none"
    (dragged)="onDrag($event)"
    [ngStyle]="{
      'width.%': memeText.size.width,
      'height.%': memeText.size.height,
      'top.px': memeText.position.y,
      'left.px': memeText.position.x
    }"
  >
    <div #handle class="absolute top-0" (dragged)="onResize($event)">
      X
    </div>
    <div class="absolute bottom-0" (dragged)="onResize($event)">
      X
    </div>
    <div class="absolute right-0 bottom-0" (dragged)="onResize($event)">
      X
    </div>
    <div class="absolute right-0" (dragged)="onResize($event)">
      X
    </div>
    <span>{{ memeText.text }}</span>
  </div>`,
})
export class MemeTextComponent {
  @Input() memeText: MemeText;

  @ViewChild('container') container: ElementRef;
  @ViewChild('handle') handle: ElementRef;

  onDrag(pos: DragPosition): void {
    const element: HTMLElement = this.container.nativeElement;
    const draggablePosition = new DraggablePosition(pos);
    draggablePosition.setXBound(
      0,
      element.offsetParent.clientWidth - element.offsetWidth,
    );
    draggablePosition.setYBound(
      0,
      element.offsetParent.clientHeight - element.offsetHeight,
    );
    this.memeText.position.x = draggablePosition.x;
    this.memeText.position.y = draggablePosition.y;
  }

  onResize(pos: DragPosition) {
    const element: HTMLElement = this.container.nativeElement;
    const handle: HTMLElement = this.handle.nativeElement;

    if (pos.x > element.offsetLeft + element.offsetWidth - handle.clientWidth) {
      this.memeText.size.width =
        ((pos.x - element.offsetLeft) / element.offsetParent.clientWidth) * 100;
    } else {
      this.memeText.size.width =
        this.memeText.size.width -
        ((pos.x - element.offsetLeft) / element.offsetParent.clientWidth) * 100;
      this.memeText.position.x = pos.x;
    }
    if (
      pos.y >
      element.offsetTop + element.offsetHeight - handle.clientHeight
    ) {
      this.memeText.size.height =
        ((pos.y - element.offsetTop) / element.offsetParent.clientHeight) * 100;
    } else {
      this.memeText.size.height =
        this.memeText.size.height -
        ((pos.y - element.offsetTop) / element.offsetParent.clientHeight) * 100;
      this.memeText.position.y = pos.y;
    }
  }
}
