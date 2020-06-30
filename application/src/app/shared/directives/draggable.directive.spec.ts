import { DraggableDirective } from './draggable.directive';
import { Component, DebugElement, ViewChild } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DragPosition } from '../interfaces/dragPosition';

@Component({
  selector: 'test',
  template: '<div appDraggable (dragged)="onDrag($event)"> </div>',
})
class TestComponent {
  position = { x: 0, y: 0 };

  @ViewChild(DraggableDirective) directive: DraggableDirective;

  onDrag(pos: DragPosition): void {
    this.position.x = pos.x;
    this.position.y = pos.y;
  }
}

describe('DraggableDirective', () => {
  let component: TestComponent;
  let fixture: ComponentFixture<TestComponent>;
  let element: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TestComponent, DraggableDirective],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestComponent);
    component = fixture.componentInstance;
    element = fixture.debugElement.query(By.css('div'));
    fixture.detectChanges();
  });

  it('should create an instance', () => {
    expect(component).toBeTruthy();
  });

  it('should trigger drag event and calculate positions correctly', () => {
    const mouseDownEvent = { offsetX: 50, offsetY: 45 };
    const mouseMoveEvent = {
      pageX: 60,
      pageY: 50,
    } as MouseEvent;
    const expected = {
      x: mouseMoveEvent.pageX - mouseDownEvent.offsetX,
      y: mouseMoveEvent.pageY - mouseDownEvent.offsetY,
    };

    element.triggerEventHandler('mousedown', mouseDownEvent);
    component.directive.onMouseMove(mouseMoveEvent);
    fixture.detectChanges();

    expect(component.position).toEqual(expected);
  });
});
