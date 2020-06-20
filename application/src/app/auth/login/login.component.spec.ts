import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let router: Router;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
        ReactiveFormsModule,
      ],
      providers: [FormBuilder],
      declarations: [LoginComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('signInForm', () => {
    it('should be invalid with empty values', () => {
      expect(component.loginForm.valid).toBeFalsy();
      expect(
        fixture.debugElement.query(By.css('button')).properties.disabled,
      ).toBeTruthy();
    });

    it('should be valid after inserting correct values', () => {
      const inputs = [
        { selector: '#username', value: 'username' },
        {
          selector: '#password',
          value: 'password',
        },
      ];

      for (const { selector, value } of inputs) {
        fixture.debugElement
          .query(By.css(selector))
          .triggerEventHandler('input', {
            target: {
              value,
            },
          });
      }

      expect(
        fixture.debugElement.query(By.css('button')).attributes.disabled,
      ).toBeFalsy();
      expect(component.loginForm.valid).toBeTruthy();
      expect(component.loginForm.value.username).toBe('username');
    });
  });

  describe('onSubmit', () => {
    it('should navigate on login', () => {
      jest.spyOn(authService, 'login').mockReturnValueOnce(of(null as any));
      router.navigate = jest.fn();

      component.loginForm.setValue({
        username: 'username',
        password: 'password',
      });
      component.onSubmit();

      expect(router.navigate).toHaveBeenCalledTimes(1);
    });
  });
});
