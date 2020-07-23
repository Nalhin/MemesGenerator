import { Injectable } from '@angular/core';
import Cookies from 'universal-cookie';

const AUTH_COOKIE = 'auth';

@Injectable({
  providedIn: 'root',
})
export class CookieService {
  private cookies = new Cookies();

  getAuthCookie(): string | undefined {
    return this.cookies.get(AUTH_COOKIE);
  }

  setAuthCookie(token: string): void {
    this.cookies.set(AUTH_COOKIE, token);
  }

  removeAuthCookie(): void {
    this.cookies.remove(AUTH_COOKIE);
  }
}
