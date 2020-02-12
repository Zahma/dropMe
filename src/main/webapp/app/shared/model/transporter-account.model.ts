import { ITruck } from 'app/shared/model/truck.model';
import { IReputation } from 'app/shared/model/reputation.model';

export interface ITransporterAccount {
  id?: number;
  name?: string;
  phone?: string;
  patentContentType?: string;
  patent?: any;
  managerFName?: string;
  managerLName?: string;
  balance?: number;
  insuranceContentType?: string;
  insurance?: any;
  referal?: string;
  referedBy?: string;
  activated?: boolean;
  userLogin?: string;
  userId?: number;
  trucks?: ITruck[];
  reputations?: IReputation[];
}

export class TransporterAccount implements ITransporterAccount {
  constructor(
    public id?: number,
    public name?: string,
    public phone?: string,
    public patentContentType?: string,
    public patent?: any,
    public managerFName?: string,
    public managerLName?: string,
    public balance?: number,
    public insuranceContentType?: string,
    public insurance?: any,
    public referal?: string,
    public referedBy?: string,
    public activated?: boolean,
    public userLogin?: string,
    public userId?: number,
    public trucks?: ITruck[],
    public reputations?: IReputation[]
  ) {
    this.activated = this.activated || false;
  }
}
