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
export class TemplatesService {
  constructor(private readonly httpClient: HttpClient) {}

  getAll(page: number) {
    return this.httpClient.get<PageTemplateResponseDto>('/templates', {
      params: new HttpParams().set('page', String(page)),
    });
  }

  getOneById(id: number) {
    return this.httpClient.get<TemplateResponseDto>(`/templates/${id}`);
  }

  save(saveTemplateDto: SaveTemplateDto) {
    return this.httpClient.post<MemeResponseDto>(
      '/templates/save',
      saveTemplateDto,
    );
  }
}
