import { Factory } from 'factory.io';
import * as faker from 'faker';
import { UserResponseDto } from '../../src/app/shared/interfaces/api.interface';

export const userResponseDtoFactory = new Factory<UserResponseDto>()
  .props({
    email: faker.internet.email,
    username: faker.internet.userName,
  })
  .done();
