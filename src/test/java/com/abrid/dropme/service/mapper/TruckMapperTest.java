package com.abrid.dropme.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TruckMapperTest {

    private TruckMapper truckMapper;

    @BeforeEach
    public void setUp() {
        truckMapper = new TruckMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(truckMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(truckMapper.fromId(null)).isNull();
    }
}
