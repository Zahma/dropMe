package com.abrid.dropme.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminAccountMapperTest {

    private AdminAccountMapper adminAccountMapper;

    @BeforeEach
    public void setUp() {
        adminAccountMapper = new AdminAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adminAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adminAccountMapper.fromId(null)).isNull();
    }
}
