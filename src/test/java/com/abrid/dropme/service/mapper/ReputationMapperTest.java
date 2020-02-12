package com.abrid.dropme.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReputationMapperTest {

    private ReputationMapper reputationMapper;

    @BeforeEach
    public void setUp() {
        reputationMapper = new ReputationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reputationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reputationMapper.fromId(null)).isNull();
    }
}
