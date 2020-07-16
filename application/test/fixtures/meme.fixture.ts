import { Factory } from 'factory.io';
import * as faker from 'faker';
import { userResponseDtoFactory } from './users.fixture';
import { templateResponseDtoFactory } from './meme-template.fixture';
import {
  MemeResponseDto,
  PageMemeResponseDto,
  SaveMemeDto,
} from '../../src/app/shared/interfaces/api.interface';

export const saveMemeDtoFactory = new Factory<SaveMemeDto>()
  .props({
    templateId: faker.random.number,
  })
  .done();

export const memeResponseDtoFactory = new Factory<MemeResponseDto>()
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

export const pageMemeResponseDtoFactory = new Factory<PageMemeResponseDto>()
  .props({
    content: memeResponseDtoFactory.buildMany(4),
  })
  .done();
