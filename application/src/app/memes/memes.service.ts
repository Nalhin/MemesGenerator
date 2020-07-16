import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {
  MemeResponseDto,
  PageMemeResponseDto,
  SaveMemeDto,
} from '../shared/interfaces/api.interface';

@Injectable({
  providedIn: 'root',
})
export class MemesService {
  constructor(private readonly httpClient: HttpClient) {}

  getAll(page: number) {
    return this.httpClient.get<PageMemeResponseDto>('/api/memes', {
      params: new HttpParams().set('page', String(page)),
    });
  }

  getOneById(id: number) {
    return this.httpClient.get<MemeResponseDto>(`/api/memes/${id}`);
  }

  save(blob: Blob, saveMemeDto: SaveMemeDto) {
    const formData = new FormData();
    formData.append('file', blob);
    formData.append(
      'saveMemeDto',
      new Blob([JSON.stringify(saveMemeDto)], {
        type: 'application/json',
      }),
    );

    return this.httpClient.post<SaveMemeDto>('/api/memes/save', formData);
  }
}
