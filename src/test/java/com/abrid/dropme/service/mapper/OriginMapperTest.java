package com.abrid.dropme.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OriginMapperTest {

    private OriginMapper originMapper;

    @BeforeEach
    public void setUp() {
        originMapper = new OriginMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(originMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(originMapper.fromId(null)).isNull();
    }
}
