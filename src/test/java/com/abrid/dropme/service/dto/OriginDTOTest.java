package com.abrid.dropme.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.abrid.dropme.web.rest.TestUtil;

public class OriginDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OriginDTO.class);
        OriginDTO originDTO1 = new OriginDTO();
        originDTO1.setId(1L);
        OriginDTO originDTO2 = new OriginDTO();
        assertThat(originDTO1).isNotEqualTo(originDTO2);
        originDTO2.setId(originDTO1.getId());
        assertThat(originDTO1).isEqualTo(originDTO2);
        originDTO2.setId(2L);
        assertThat(originDTO1).isNotEqualTo(originDTO2);
        originDTO1.setId(null);
        assertThat(originDTO1).isNotEqualTo(originDTO2);
    }
}
