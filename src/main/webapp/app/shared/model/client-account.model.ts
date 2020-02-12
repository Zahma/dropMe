import { IReputation } from 'app/shared/model/reputation.model';
import { ITrip } from 'app/shared/model/trip.model';

export interface IClientAccount {
  id?: number;
  firstName?: string;
  lastName?: string;
  phone?: string;
  referredBy?: string;
  referal?: string;
  activated?: boolean;
  userLogin?: string;
  userId?: number;
  reputations?: IReputation[];
  trips?: ITrip[];
}

export class ClientAccount implements IClientAccount {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public phone?: string,
    public referredBy?: string,
    public referal?: string,
    public activated?: boolean,
    public userLogin?: string,
    public userId?: number,
    public reputations?: IReputation[],
    public trips?: ITrip[]
  ) {
    this.activated = this.activated || false;
  }
}
