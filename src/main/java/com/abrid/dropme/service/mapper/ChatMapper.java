package com.abrid.dropme.service.mapper;


import com.abrid.dropme.domain.*;
import com.abrid.dropme.service.dto.ChatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chat} and its DTO {@link ChatDTO}.
 */
@Mapper(componentModel = "spring", uses = {ConversationMapper.class})
public interface ChatMapper extends EntityMapper<ChatDTO, Chat> {

    @Mapping(source = "conversation.id", target = "conversationId")
    ChatDTO toDto(Chat chat);

    @Mapping(source = "conversationId", target = "conversation")
    Chat toEntity(ChatDTO chatDTO);

    default Chat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chat chat = new Chat();
        chat.setId(id);
        return chat;
    }
}
