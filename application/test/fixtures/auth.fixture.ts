import { Factory } from 'factory.io';
import * as faker from 'faker';
import {
  AuthResponseDto,
  LoginUserDto,
  SignUpUserDto,
} from '../../src/app/shared/interfaces/api.interface';
import { userResponseDtoFactory } from './users.fixture';

export const loginUserDtoFactory = new Factory<LoginUserDto>()
  .props({
    username: faker.internet.userName,
    password: faker.internet.password,
  })
  .done();

export const authResponseDtoFactory = new Factory<AuthResponseDto>()
  .props({
    token: faker.random.word,
    user: userResponseDtoFactory.buildOne,
  })
  .done();

export const signUpUserDtoFactory = new Factory<SignUpUserDto>()
  .mixins([loginUserDtoFactory])
  .props({
    email: faker.internet.email,
  })
  .done();
