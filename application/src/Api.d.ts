declare namespace Api {
  namespace AddTemplateUsingPOST {
    export interface BodyParameters {
      saveTemplateDto: Api.AddTemplateUsingPOST.Parameters.SaveTemplateDto;
    }
    namespace Parameters {
      export type SaveTemplateDto = Api.SaveTemplateDto;
    }
    namespace Responses {
      export type $200 = Api.TemplateResponseDto;
    }
  }
  /**
   * AuthResponseDto
   */
  export interface AuthResponseDto {
    token?: string;
  }
  /**
   * File
   */
  export interface File {
    absolute?: boolean;
    absoluteFile?: Api.File;
    absolutePath?: string;
    canonicalFile?: Api.File;
    canonicalPath?: string;
    directory?: boolean;
    executable?: boolean;
    file?: boolean;
    freeSpace?: number; // int64
    hidden?: boolean;
    lastModified?: number; // int64
    name?: string;
    parent?: string;
    parentFile?: Api.File;
    path?: string;
    readable?: boolean;
    totalSpace?: number; // int64
    usableSpace?: number; // int64
    writable?: boolean;
  }
  namespace GetAllUsingGET {
    namespace Responses {
      export type $200 = Api.PageMemeResponseDto;
    }
  }
  namespace GetAllUsingGET1 {
    namespace Responses {
      export type $200 = Api.PageTemplateResponseDto;
    }
  }
  namespace GetAllUsingGET2 {
    namespace Responses {
      export type $200 = Api.UserResponseDto[];
    }
  }
  namespace GetByIdUsingGET {
    namespace Responses {
      export type $200 = Api.TemplateResponseDto;
    }
  }
  namespace GetOneByIdUsingGET {
    namespace Responses {
      export type $200 = Api.MemeResponseDto;
    }
  }
  /**
   * InputStream
   */
  export interface InputStream {}
  /**
   * LoginUserDto
   */
  export interface LoginUserDto {
    password?: string;
    username?: string;
  }
  namespace LoginUsingPOST {
    export interface BodyParameters {
      loginUserDto: Api.LoginUsingPOST.Parameters.LoginUserDto;
    }
    namespace Parameters {
      export type LoginUserDto = Api.LoginUserDto;
    }
    namespace Responses {
      export type $200 = Api.AuthResponseDto;
    }
  }
  namespace MeUsingGET {
    namespace Responses {
      export type $200 = Api.UserResponseDto;
    }
  }
  /**
   * MemeResponseDto
   */
  export interface MemeResponseDto {
    author?: Api.UserResponseDto;
    created?: string; // date-time
    id?: number; // int64
    template?: Api.TemplateResponseDto;
    url?: string;
  }
  /**
   * Page«MemeResponseDto»
   */
  export interface PageMemeResponseDto {
    content?: Api.MemeResponseDto[];
    empty?: boolean;
    first?: boolean;
    last?: boolean;
    number?: number; // int32
    numberOfElements?: number; // int32
    pageable?: Api.Pageable;
    size?: number; // int32
    sort?: Api.Sort;
    totalElements?: number; // int64
    totalPages?: number; // int32
  }
  /**
   * Page«TemplateResponseDto»
   */
  export interface PageTemplateResponseDto {
    content?: Api.TemplateResponseDto[];
    empty?: boolean;
    first?: boolean;
    last?: boolean;
    number?: number; // int32
    numberOfElements?: number; // int32
    pageable?: Api.Pageable;
    size?: number; // int32
    sort?: Api.Sort;
    totalElements?: number; // int64
    totalPages?: number; // int32
  }
  /**
   * Pageable
   */
  export interface Pageable {
    offset?: number; // int64
    pageNumber?: number; // int32
    pageSize?: number; // int32
    paged?: boolean;
    sort?: Api.Sort;
    unpaged?: boolean;
  }
  /**
   * Resource
   */
  export interface Resource {
    description?: string;
    file?: Api.File;
    filename?: string;
    inputStream?: Api.InputStream;
    open?: boolean;
    readable?: boolean;
    uri?: Api.URI;
    url?: Api.URL;
  }
  /**
   * SaveMemeDto
   */
  export interface SaveMemeDto {
    templateId?: number; // int64
  }
  /**
   * SaveTemplateDto
   */
  export interface SaveTemplateDto {
    title?: string;
    url?: string;
  }
  namespace SaveUsingPOST {
    export interface FormDataParameters {
      saveMemeDto: Api.SaveUsingPOST.Parameters.SaveMemeDto;
    }
    namespace Parameters {
      export type SaveMemeDto = Api.SaveMemeDto;
    }
    namespace Responses {
      export type $200 = Api.MemeResponseDto;
    }
  }
  /**
   * SignUpUserDto
   */
  export interface SignUpUserDto {
    email?: string;
    password?: string;
    username?: string;
  }
  namespace SignUpUsingPOST {
    export interface BodyParameters {
      signUpUserDto: Api.SignUpUsingPOST.Parameters.SignUpUserDto;
    }
    namespace Parameters {
      export type SignUpUserDto = Api.SignUpUserDto;
    }
    namespace Responses {
      export type $200 = Api.AuthResponseDto;
    }
  }
  /**
   * Sort
   */
  export interface Sort {
    empty?: boolean;
    sorted?: boolean;
    unsorted?: boolean;
  }
  /**
   * TemplateResponseDto
   */
  export interface TemplateResponseDto {
    id?: number; // int64
    title?: string;
    url?: string;
  }
  /**
   * URI
   */
  export interface URI {
    absolute?: boolean;
    authority?: string;
    fragment?: string;
    host?: string;
    opaque?: boolean;
    path?: string;
    port?: number; // int32
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
  /**
   * URL
   */
  export interface URL {
    authority?: string;
    content?: {};
    defaultPort?: number; // int32
    deserializedFields?: Api.URLStreamHandler;
    file?: string;
    host?: string;
    path?: string;
    port?: number; // int32
    protocol?: string;
    query?: string;
    ref?: string;
    serializedHashCode?: number; // int32
    userInfo?: string;
  }
  /**
   * URLStreamHandler
   */
  export interface URLStreamHandler {}
  /**
   * UserResponseDto
   */
  export interface UserResponseDto {
    email?: string;
    username?: string;
  }
}
