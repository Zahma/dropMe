package com.abrid.dropme.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DestinationMapperTest {

    private DestinationMapper destinationMapper;

    @BeforeEach
    public void setUp() {
        destinationMapper = new DestinationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(destinationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(destinationMapper.fromId(null)).isNull();
    }
}
