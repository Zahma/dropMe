package com.abrid.dropme.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.abrid.dropme.web.rest.TestUtil;

public class TransporterAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransporterAccountDTO.class);
        TransporterAccountDTO transporterAccountDTO1 = new TransporterAccountDTO();
        transporterAccountDTO1.setId(1L);
        TransporterAccountDTO transporterAccountDTO2 = new TransporterAccountDTO();
        assertThat(transporterAccountDTO1).isNotEqualTo(transporterAccountDTO2);
        transporterAccountDTO2.setId(transporterAccountDTO1.getId());
        assertThat(transporterAccountDTO1).isEqualTo(transporterAccountDTO2);
        transporterAccountDTO2.setId(2L);
        assertThat(transporterAccountDTO1).isNotEqualTo(transporterAccountDTO2);
        transporterAccountDTO1.setId(null);
        assertThat(transporterAccountDTO1).isNotEqualTo(transporterAccountDTO2);
    }
}
