import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {
  MemeResponseDto,
  PageTemplateResponseDto,
  SaveTemplateDto,
  TemplateResponseDto,
} from '../shared/interfaces/api.interface';

@Injectable({
  providedIn: 'root',
})
export class MemeTemplatesService {
  constructor(private readonly httpClient: HttpClient) {}

  getAll(page: number) {
    return this.httpClient.get<PageTemplateResponseDto>('/api/templates', {
      params: new HttpParams().set('page', String(page)),
    });
  }

  getOneById(id: number) {
    return this.httpClient.get<TemplateResponseDto>(`/api/templates/${id}`);
  }

  save(saveTemplateDto: SaveTemplateDto) {
    return this.httpClient.post<MemeResponseDto>(
      '/api/templates/save',
      saveTemplateDto,
    );
  }
}
