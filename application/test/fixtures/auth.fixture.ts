import { Factory } from 'factory.io';
import * as faker from 'faker';

export const loginUserDtoFactory = new Factory<Api.LoginUserDto>()
  .props({
    username: faker.internet.userName,
    password: faker.internet.password,
  })
  .done();

export const authResponseDtoFactory = new Factory<Api.AuthResponseDto>()
  .props({
    token: faker.random.word,
  })
  .done();

export const signUpUserDtoFactory = new Factory<Api.SignUpUserDto>()
  .mixins([loginUserDtoFactory])
  .props({
    email: faker.internet.email,
  })
  .done();
