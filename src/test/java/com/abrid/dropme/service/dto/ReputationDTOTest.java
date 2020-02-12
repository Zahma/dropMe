package com.abrid.dropme.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.abrid.dropme.web.rest.TestUtil;

public class ReputationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReputationDTO.class);
        ReputationDTO reputationDTO1 = new ReputationDTO();
        reputationDTO1.setId(1L);
        ReputationDTO reputationDTO2 = new ReputationDTO();
        assertThat(reputationDTO1).isNotEqualTo(reputationDTO2);
        reputationDTO2.setId(reputationDTO1.getId());
        assertThat(reputationDTO1).isEqualTo(reputationDTO2);
        reputationDTO2.setId(2L);
        assertThat(reputationDTO1).isNotEqualTo(reputationDTO2);
        reputationDTO1.setId(null);
        assertThat(reputationDTO1).isNotEqualTo(reputationDTO2);
    }
}
