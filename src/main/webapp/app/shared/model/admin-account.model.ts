import { ERole } from 'app/shared/model/enumerations/e-role.model';

export interface IAdminAccount {
  id?: number;
  role?: ERole;
  userLogin?: string;
  userId?: number;
}

export class AdminAccount implements IAdminAccount {
  constructor(public id?: number, public role?: ERole, public userLogin?: string, public userId?: number) {}
}
