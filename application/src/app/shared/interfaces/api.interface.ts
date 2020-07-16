/* tslint:disable */
/* eslint-disable */

/*
 * ---------------------------------------------------------------
 * ## THIS FILE WAS GENERATED VIA SWAGGER-TYPESCRIPT-API        ##
 * ##                                                           ##
 * ## AUTHOR: acacode                                           ##
 * ## SOURCE: https://github.com/acacode/swagger-typescript-api ##
 * ---------------------------------------------------------------
 */

export interface AuthResponseDto {
  token?: string;
}

export interface File {
  absolute?: boolean;
  absoluteFile?: File;
  absolutePath?: string;
  canonicalFile?: File;
  canonicalPath?: string;
  directory?: boolean;
  executable?: boolean;
  file?: boolean;
  freeSpace?: number;
  hidden?: boolean;
  lastModified?: number;
  name?: string;
  parent?: string;
  parentFile?: File;
  path?: string;
  readable?: boolean;
  totalSpace?: number;
  usableSpace?: number;
  writable?: boolean;
}

export type InputStream = object;

export interface LoginUserDto {
  password?: string;
  username?: string;
}

export interface MemeResponseDto {
  author?: UserResponseDto;
  created?: string;
  id?: number;
  template?: TemplateResponseDto;
  url?: string;
}

export interface Pageable {
  offset?: number;
  pageNumber?: number;
  pageSize?: number;
  paged?: boolean;
  sort?: Sort;
  unpaged?: boolean;
}

export interface Resource {
  description?: string;
  file?: File;
  filename?: string;
  inputStream?: InputStream;
  open?: boolean;
  readable?: boolean;
  uri?: URI;
  url?: URL;
}

export interface SaveMemeDto {
  templateId?: number;
}

export interface SaveTemplateDto {
  title?: string;
  url?: string;
}

export interface SignUpUserDto {
  email?: string;
  password?: string;
  username?: string;
}

export interface Sort {
  empty?: boolean;
  sorted?: boolean;
  unsorted?: boolean;
}

export interface TemplateResponseDto {
  id?: number;
  title?: string;
  url?: string;
}

export interface URI {
  absolute?: boolean;
  authority?: string;
  fragment?: string;
  host?: string;
  opaque?: boolean;
  path?: string;
  port?: number;
  query?: string;
  rawAuthority?: string;
  rawFragment?: string;
  rawPath?: string;
  rawQuery?: string;
  rawSchemeSpecificPart?: string;
  rawUserInfo?: string;
  scheme?: string;
  schemeSpecificPart?: string;
  userInfo?: string;
}

export interface URL {
  authority?: string;
  content?: object;
  defaultPort?: number;
  deserializedFields?: URLStreamHandler;
  file?: string;
  host?: string;
  path?: string;
  port?: number;
  protocol?: string;
  query?: string;
  ref?: string;
  serializedHashCode?: number;
  userInfo?: string;
}

export type URLStreamHandler = object;

export interface UserResponseDto {
  email?: string;
  username?: string;
}

export interface PageMemeResponseDto {
  content?: MemeResponseDto[];
  empty?: boolean;
  first?: boolean;
  last?: boolean;
  number?: number;
  numberOfElements?: number;
  pageable?: Pageable;
  size?: number;
  sort?: Sort;
  totalElements?: number;
  totalPages?: number;
}

export interface PageTemplateResponseDto {
  content?: TemplateResponseDto[];
  empty?: boolean;
  first?: boolean;
  last?: boolean;
  number?: number;
  numberOfElements?: number;
  pageable?: Pageable;
  size?: number;
  sort?: Sort;
  totalElements?: number;
  totalPages?: number;
}
