import { Moment } from 'moment';
import { IConversation } from 'app/shared/model/conversation.model';
import { EMarchandise } from 'app/shared/model/enumerations/e-marchandise.model';
import { ETripState } from 'app/shared/model/enumerations/e-trip-state.model';

export interface ITrip {
  id?: number;
  isFull?: boolean;
  width?: number;
  height?: number;
  length?: number;
  weight?: number;
  marchandise?: EMarchandise;
  etd?: Moment;
  description?: string;
  state?: ETripState;
  eta?: Moment;
  distance?: number;
  originId?: number;
  destinationId?: number;
  conversations?: IConversation[];
  truckId?: number;
  clientAccountId?: number;
}

export class Trip implements ITrip {
  constructor(
    public id?: number,
    public isFull?: boolean,
    public width?: number,
    public height?: number,
    public length?: number,
    public weight?: number,
    public marchandise?: EMarchandise,
    public etd?: Moment,
    public description?: string,
    public state?: ETripState,
    public eta?: Moment,
    public distance?: number,
    public originId?: number,
    public destinationId?: number,
    public conversations?: IConversation[],
    public truckId?: number,
    public clientAccountId?: number
  ) {
    this.isFull = this.isFull || false;
  }
}
