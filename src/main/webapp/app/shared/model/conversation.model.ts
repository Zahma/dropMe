import { IChat } from 'app/shared/model/chat.model';

export interface IConversation {
  id?: number;
  chats?: IChat[];
  tripId?: number;
  truckId?: number;
}

export class Conversation implements IConversation {
  constructor(public id?: number, public chats?: IChat[], public tripId?: number, public truckId?: number) {}
}
