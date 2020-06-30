import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class MemeTemplatesService {
  constructor(private readonly httpClient: HttpClient) {}

  getAll(page: number) {
    return this.httpClient.get<Api.PageTemplateResponseDto>('/api/templates', {
      params: new HttpParams().set('page', String(page)),
    });
  }

  getOneById(id: number) {
    return this.httpClient.get<Api.TemplateResponseDto>(`/api/templates/${id}`);
  }

  save(saveTemplateDto: Api.SaveTemplateDto) {
    return this.httpClient.post<Api.MemeResponseDto>(
      '/api/templates/save',
      saveTemplateDto,
    );
  }
}
