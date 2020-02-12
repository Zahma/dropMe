package com.abrid.dropme.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.abrid.dropme.web.rest.TestUtil;

public class AdminAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminAccountDTO.class);
        AdminAccountDTO adminAccountDTO1 = new AdminAccountDTO();
        adminAccountDTO1.setId(1L);
        AdminAccountDTO adminAccountDTO2 = new AdminAccountDTO();
        assertThat(adminAccountDTO1).isNotEqualTo(adminAccountDTO2);
        adminAccountDTO2.setId(adminAccountDTO1.getId());
        assertThat(adminAccountDTO1).isEqualTo(adminAccountDTO2);
        adminAccountDTO2.setId(2L);
        assertThat(adminAccountDTO1).isNotEqualTo(adminAccountDTO2);
        adminAccountDTO1.setId(null);
        assertThat(adminAccountDTO1).isNotEqualTo(adminAccountDTO2);
    }
}
