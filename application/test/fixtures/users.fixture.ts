import { Factory } from 'factory.io';
import * as faker from 'faker';

export const userResponseDtoFactory = new Factory<Api.UserResponseDto>()
  .props({
    email: faker.internet.email,
    username: faker.internet.userName,
  })
  .done();
