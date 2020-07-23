import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserResponseDto } from '../../../shared/interfaces/api.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private readonly httpClient: HttpClient) {}

  me(): Observable<UserResponseDto> {
    return this.httpClient.get<UserResponseDto>('/api/users/me');
  }
}
