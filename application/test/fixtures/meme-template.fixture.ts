import { Factory } from 'factory.io';
import * as faker from 'faker';
import {
  PageTemplateResponseDto,
  SaveTemplateDto,
  TemplateResponseDto,
} from '../../src/app/shared/interfaces/api.interface';

export const saveTemplateDtoFactory = new Factory<SaveTemplateDto>()
  .props({
    title: faker.name.title,
    url: faker.internet.url,
  })
  .done();

export const templateResponseDtoFactory = new Factory<TemplateResponseDto>()
  .options({ idField: 'id' })
  .mixins([saveTemplateDtoFactory])
  .done();

export const pageTemplateResponseDtoFactory = new Factory<
  PageTemplateResponseDto
>()
  .props({
    content: templateResponseDtoFactory.buildMany(4),
  })
  .done();
