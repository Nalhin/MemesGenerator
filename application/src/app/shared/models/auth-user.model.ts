export abstract class User {
  readonly username: string;
  readonly email: string;
  readonly id: number;
  readonly roles: string[] = [];
  readonly token: string;

  protected constructor(partial: Partial<User> = {}) {
    Object.assign(partial);
  }

  abstract get isAuthenticated(): boolean;
}

export class AnonymousUser extends User {
  constructor() {
    super();
  }

  get isAuthenticated() {
    return false;
  }
}

export class AuthenticatedUser extends User {
  constructor(partial: Partial<User>) {
    super(partial);
  }

  get isAuthenticated() {
    return true;
  }
}
