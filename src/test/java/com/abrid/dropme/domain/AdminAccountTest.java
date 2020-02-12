package com.abrid.dropme.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.abrid.dropme.web.rest.TestUtil;

public class AdminAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminAccount.class);
        AdminAccount adminAccount1 = new AdminAccount();
        adminAccount1.setId(1L);
        AdminAccount adminAccount2 = new AdminAccount();
        adminAccount2.setId(adminAccount1.getId());
        assertThat(adminAccount1).isEqualTo(adminAccount2);
        adminAccount2.setId(2L);
        assertThat(adminAccount1).isNotEqualTo(adminAccount2);
        adminAccount1.setId(null);
        assertThat(adminAccount1).isNotEqualTo(adminAccount2);
    }
}
