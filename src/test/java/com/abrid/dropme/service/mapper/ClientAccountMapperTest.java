package com.abrid.dropme.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientAccountMapperTest {

    private ClientAccountMapper clientAccountMapper;

    @BeforeEach
    public void setUp() {
        clientAccountMapper = new ClientAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(clientAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(clientAccountMapper.fromId(null)).isNull();
    }
}
