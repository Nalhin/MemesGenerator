import {
  async,
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';

import { SignInComponent } from './sign-in.component';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { Router } from '@angular/router';

describe('SignInComponent', () => {
  let component: SignInComponent;
  let fixture: ComponentFixture<SignInComponent>;
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
      declarations: [SignInComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignInComponent);
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
      expect(component.signInForm.valid).toBeFalsy();
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
        { selector: '#email', value: 'email' },
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
      expect(component.signInForm.valid).toBeTruthy();
      expect(component.signInForm.value.username).toBe('username');
    });
  });

  describe('onSubmit', () => {
    it('should navigate on login', () => {
      jest.spyOn(authService, 'signIn').mockReturnValueOnce(of(null as any));
      router.navigate = jest.fn();

      component.signInForm.setValue({
        username: 'username',
        password: 'password',
        email: 'email',
      });
      component.onSubmit();

      expect(router.navigate).toHaveBeenCalledTimes(1);
    });
  });
});
