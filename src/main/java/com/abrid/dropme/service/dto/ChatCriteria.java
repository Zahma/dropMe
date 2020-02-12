package com.abrid.dropme.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.abrid.dropme.domain.Chat} entity. This class is used
 * in {@link com.abrid.dropme.web.rest.ChatResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /chats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChatCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter text;

    private InstantFilter date;

    private LongFilter conversationId;

    public ChatCriteria() {
    }

    public ChatCriteria(ChatCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.conversationId = other.conversationId == null ? null : other.conversationId.copy();
    }

    @Override
    public ChatCriteria copy() {
        return new ChatCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getText() {
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public LongFilter getConversationId() {
        return conversationId;
    }

    public void setConversationId(LongFilter conversationId) {
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
        final ChatCriteria that = (ChatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(text, that.text) &&
            Objects.equals(date, that.date) &&
            Objects.equals(conversationId, that.conversationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        text,
        date,
        conversationId
        );
    }

    @Override
    public String toString() {
        return "ChatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (conversationId != null ? "conversationId=" + conversationId + ", " : "") +
            "}";
    }

}
