import { APP_INITIALIZER, NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BaseUrlInterceptor } from './interceptors/base-url/base-url.interceptor';
import { LayoutModule } from './layout/layout.module';
import { AuthService } from './services/auth/auth.service';
import { LoadingInterceptor } from './interceptors/loading/loading.interceptor';
import { AuthHeaderInterceptor } from './interceptors/auth-header/auth-header.interceptor';

@NgModule({
  declarations: [],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoadingInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: BaseUrlInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthHeaderInterceptor,
      multi: true,
    },
    {
      provide: APP_INITIALIZER,
      useFactory: (authService: AuthService) => (): Promise<void> => {
        return authService.onInit();
      },
      deps: [AuthService],
      multi: true,
    },
  ],
  imports: [CommonModule, LayoutModule],
  exports: [LayoutModule],
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule has already been imported.');
    }
  }
}
