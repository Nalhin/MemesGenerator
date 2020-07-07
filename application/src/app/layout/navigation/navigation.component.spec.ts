import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationComponent } from './navigation.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { SharedModule } from '../../shared/shared.module';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('NavigationComponent', () => {
  let component: NavigationComponent;
  let fixture: ComponentFixture<NavigationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        MatToolbarModule,
        MatSidenavModule,
        SharedModule,
        NoopAnimationsModule,
      ],
      declarations: [NavigationComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
