package com.abrid.dropme.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.abrid.dropme.domain.Chat} entity.
 */
public class ChatDTO implements Serializable {

    private Long id;

    private String text;

    private Instant date;


    private Long conversationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChatDTO chatDTO = (ChatDTO) o;
        if (chatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChatDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", date='" + getDate() + "'" +
            ", conversationId=" + getConversationId() +
            "}";
    }
}
