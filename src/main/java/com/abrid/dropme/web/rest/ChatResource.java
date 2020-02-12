package com.abrid.dropme.web.rest;

import com.abrid.dropme.service.ChatService;
import com.abrid.dropme.web.rest.errors.BadRequestAlertException;
import com.abrid.dropme.service.dto.ChatDTO;
import com.abrid.dropme.service.dto.ChatCriteria;
import com.abrid.dropme.service.ChatQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.abrid.dropme.domain.Chat}.
 */
@RestController
@RequestMapping("/api")
public class ChatResource {

    private final Logger log = LoggerFactory.getLogger(ChatResource.class);

    private static final String ENTITY_NAME = "chat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChatService chatService;

    private final ChatQueryService chatQueryService;

    public ChatResource(ChatService chatService, ChatQueryService chatQueryService) {
        this.chatService = chatService;
        this.chatQueryService = chatQueryService;
    }

    /**
     * {@code POST  /chats} : Create a new chat.
     *
     * @param chatDTO the chatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chatDTO, or with status {@code 400 (Bad Request)} if the chat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chats")
    public ResponseEntity<ChatDTO> createChat(@RequestBody ChatDTO chatDTO) throws URISyntaxException {
        log.debug("REST request to save Chat : {}", chatDTO);
        if (chatDTO.getId() != null) {
            throw new BadRequestAlertException("A new chat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatDTO result = chatService.save(chatDTO);
        return ResponseEntity.created(new URI("/api/chats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chats} : Updates an existing chat.
     *
     * @param chatDTO the chatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chatDTO,
     * or with status {@code 400 (Bad Request)} if the chatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chats")
    public ResponseEntity<ChatDTO> updateChat(@RequestBody ChatDTO chatDTO) throws URISyntaxException {
        log.debug("REST request to update Chat : {}", chatDTO);
        if (chatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChatDTO result = chatService.save(chatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chats} : get all the chats.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chats in body.
     */
    @GetMapping("/chats")
    public ResponseEntity<List<ChatDTO>> getAllChats(ChatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Chats by criteria: {}", criteria);
        Page<ChatDTO> page = chatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chats/count} : count all the chats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/chats/count")
    public ResponseEntity<Long> countChats(ChatCriteria criteria) {
        log.debug("REST request to count Chats by criteria: {}", criteria);
        return ResponseEntity.ok().body(chatQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /chats/:id} : get the "id" chat.
     *
     * @param id the id of the chatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chats/{id}")
    public ResponseEntity<ChatDTO> getChat(@PathVariable Long id) {
        log.debug("REST request to get Chat : {}", id);
        Optional<ChatDTO> chatDTO = chatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chatDTO);
    }

    /**
     * {@code DELETE  /chats/:id} : delete the "id" chat.
     *
     * @param id the id of the chatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chats/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        log.debug("REST request to delete Chat : {}", id);
        chatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
