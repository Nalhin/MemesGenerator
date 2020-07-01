import { Factory } from 'factory.io';
import * as faker from 'faker';
import { userResponseDtoFactory } from './users.fixture';
import { templateResponseDtoFactory } from './meme-template.fixture';

export const saveMemeDtoFactory = new Factory<Api.SaveMemeDto>()
  .props({
    templateId: faker.random.number,
  })
  .done();

export const memeResponseDtoFactory = new Factory<Api.MemeResponseDto>()
  .options({ idField: 'id' })
  .props({
    author: userResponseDtoFactory.buildOne.bind(userResponseDtoFactory),
    created: () => faker.date.past().toISOString(),
    template: templateResponseDtoFactory.buildOne.bind(
      templateResponseDtoFactory,
    ),
    url: faker.internet.url,
  })
  .done();

export const pageMemeResponseDtoFactory = new Factory<Api.PageMemeResponseDto>()
  .props({
    content: memeResponseDtoFactory.buildMany(4),
  })
  .done();
