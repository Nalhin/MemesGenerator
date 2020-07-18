import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { SignUpComponent } from './sign-up.component';
import { FormBuilder } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import {
  authResponseDtoFactory,
  signUpUserDtoFactory,
} from '../../../../test/fixtures/auth.fixture';
import { SharedModule } from '../../shared/shared.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('SignInComponent', () => {
  let component: SignUpComponent;
  let fixture: ComponentFixture<SignUpComponent>;
  let authService: AuthService;
  let router: Router;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientTestingModule,
        SharedModule,
      ],
      providers: [FormBuilder],
      declarations: [SignUpComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignUpComponent);
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
      expect(component.signUpForm.valid).toBeFalsy();
      expect(
        fixture.debugElement.query(By.css('button')).attributes.disabled,
      ).toBeTruthy();
    });

    it('should be valid after inserting the correct values', () => {
      const signUpUserDto = signUpUserDtoFactory.buildOne();

      for (const [key, value] of Object.entries(signUpUserDto)) {
        fixture.debugElement
          .query(By.css(`input[formcontrolname=${key}]`))
          .triggerEventHandler('input', {
            target: {
              value,
            },
          });
      }

      fixture.detectChanges();
      expect(
        fixture.debugElement.query(By.css('button')).attributes.disabled,
      ).toBeFalsy();
      expect(component.signUpForm.valid).toBeTruthy();
      expect(component.signUpForm.value).toEqual(signUpUserDto);
    });
  });

  describe('onSubmit', () => {
    it('should navigate on login', () => {
      jest
        .spyOn(authService, 'signUp')
        .mockReturnValueOnce(of(authResponseDtoFactory.buildOne()));
      jest.spyOn(router, 'navigate').mockResolvedValueOnce(true);

      component.signUpForm.setValue(signUpUserDtoFactory.buildOne());
      component.onSubmit();

      expect(router.navigate).toHaveBeenCalledTimes(1);
    });
  });
});
