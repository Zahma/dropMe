export interface IReputation {
  id?: number;
  rate?: number;
  comment?: string;
  transporterAccountId?: number;
  clientAccountId?: number;
}

export class Reputation implements IReputation {
  constructor(
    public id?: number,
    public rate?: number,
    public comment?: string,
    public transporterAccountId?: number,
    public clientAccountId?: number
  ) {}
}
