import { IConversation } from 'app/shared/model/conversation.model';
import { ETruckType } from 'app/shared/model/enumerations/e-truck-type.model';

export interface ITruck {
  id?: number;
  plateNumber?: string;
  conteneurPlateNumber?: string;
  type?: ETruckType;
  width?: number;
  height?: number;
  length?: number;
  maxWeight?: number;
  driverId?: number;
  tripId?: number;
  conversations?: IConversation[];
  transporterAccountId?: number;
}

export class Truck implements ITruck {
  constructor(
    public id?: number,
    public plateNumber?: string,
    public conteneurPlateNumber?: string,
    public type?: ETruckType,
    public width?: number,
    public height?: number,
    public length?: number,
    public maxWeight?: number,
    public driverId?: number,
    public tripId?: number,
    public conversations?: IConversation[],
    public transporterAccountId?: number
  ) {}
}
