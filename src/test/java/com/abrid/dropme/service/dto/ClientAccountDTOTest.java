package com.abrid.dropme.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.abrid.dropme.web.rest.TestUtil;

public class ClientAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientAccountDTO.class);
        ClientAccountDTO clientAccountDTO1 = new ClientAccountDTO();
        clientAccountDTO1.setId(1L);
        ClientAccountDTO clientAccountDTO2 = new ClientAccountDTO();
        assertThat(clientAccountDTO1).isNotEqualTo(clientAccountDTO2);
        clientAccountDTO2.setId(clientAccountDTO1.getId());
        assertThat(clientAccountDTO1).isEqualTo(clientAccountDTO2);
        clientAccountDTO2.setId(2L);
        assertThat(clientAccountDTO1).isNotEqualTo(clientAccountDTO2);
        clientAccountDTO1.setId(null);
        assertThat(clientAccountDTO1).isNotEqualTo(clientAccountDTO2);
    }
}
