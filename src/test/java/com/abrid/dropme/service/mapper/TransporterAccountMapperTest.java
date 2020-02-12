package com.abrid.dropme.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransporterAccountMapperTest {

    private TransporterAccountMapper transporterAccountMapper;

    @BeforeEach
    public void setUp() {
        transporterAccountMapper = new TransporterAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(transporterAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(transporterAccountMapper.fromId(null)).isNull();
    }
}
