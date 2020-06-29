import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InputComponent } from './input.component';
import { Component, DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';

@Component({
  selector: 'test',
  template: '<input base class="test"/>',
})
class TestComponent {}

describe('InputComponent', () => {
  let component: TestComponent;
  let fixture: ComponentFixture<TestComponent>;
  let element: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TestComponent, InputComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    element = fixture.debugElement.query(By.css('input'));
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
