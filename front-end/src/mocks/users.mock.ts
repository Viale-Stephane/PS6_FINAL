import { User } from '../models/users';

export const USERS_MOCKED: User[] = [
  {
    username: 'professor',
    password: 'professor',
    professor: true
  },
  {
    username: 'student',
    password: 'student',
    professor: false
  },
];
