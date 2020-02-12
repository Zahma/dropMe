package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.ConversationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conversation} and its DTO {@link ConversationDTO}.
 */
@Mapper(componentModel = "spring", uses = {TripMapper.class, TruckMapper.class})
public interface ConversationMapper extends EntityMapper<ConversationDTO, Conversation> {

    @Mapping(source = "trip.id", target = "tripId")
    @Mapping(source = "truck.id", target = "truckId")
    ConversationDTO toDto(Conversation conversation);

    @Mapping(target = "chats", ignore = true)
    @Mapping(target = "removeChat", ignore = true)
    @Mapping(source = "tripId", target = "trip")
    @Mapping(source = "truckId", target = "truck")
    Conversation toEntity(ConversationDTO conversationDTO);

    default Conversation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Conversation conversation = new Conversation();
        conversation.setId(id);
        return conversation;
    }
}
