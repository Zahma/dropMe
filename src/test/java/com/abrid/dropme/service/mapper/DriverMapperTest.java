package com.abrid.dropme.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DriverMapperTest {

    private DriverMapper driverMapper;

    @BeforeEach
    public void setUp() {
        driverMapper = new DriverMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(driverMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(driverMapper.fromId(null)).isNull();
    }
}
