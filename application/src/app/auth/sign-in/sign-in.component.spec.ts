import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignInComponent } from './sign-in.component';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import {
  authResponseDtoFactory,
  loginUserDtoFactory,
  signUpUserDtoFactory,
} from '../../../../test/fixtures/auth';

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

    it('should be valid after inserting the correct values', () => {
      const signUpUserDto = signUpUserDtoFactory.buildOne();

      for (let [key, value] of Object.entries(signUpUserDto)) {
        fixture.debugElement
          .query(By.css(`#${key}`))
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
      expect(component.signInForm.value).toEqual(signUpUserDto);
    });
  });

  describe('onSubmit', () => {
    it('should navigate on login', () => {
      jest
        .spyOn(authService, 'signIn')
        .mockReturnValueOnce(of(authResponseDtoFactory.buildOne()));
      router.navigate = jest.fn();

      component.signInForm.setValue(signUpUserDtoFactory.buildOne());
      component.onSubmit();

      expect(router.navigate).toHaveBeenCalledTimes(1);
    });
  });
});
