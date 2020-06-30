import { Factory } from 'factory.io';
import * as faker from 'faker';

export const saveTemplateDtoFactory = new Factory<Api.SaveTemplateDto>()
  .props({
    title: faker.name.title,
    url: faker.internet.url,
  })
  .done();

export const templateResponseDtoFactory = new Factory<Api.TemplateResponseDto>()
  .options({ idField: 'id' })
  .mixins([saveTemplateDtoFactory])
  .done();

export const pageTemplateResponseDtoFactory = new Factory<
  Api.PageTemplateResponseDto
>()
  .props({
    content: templateResponseDtoFactory.buildMany(4),
  })
  .done();
