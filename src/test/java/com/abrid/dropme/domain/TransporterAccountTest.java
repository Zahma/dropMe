package com.abrid.dropme.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.abrid.dropme.web.rest.TestUtil;

public class TransporterAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransporterAccount.class);
        TransporterAccount transporterAccount1 = new TransporterAccount();
        transporterAccount1.setId(1L);
        TransporterAccount transporterAccount2 = new TransporterAccount();
        transporterAccount2.setId(transporterAccount1.getId());
        assertThat(transporterAccount1).isEqualTo(transporterAccount2);
        transporterAccount2.setId(2L);
        assertThat(transporterAccount1).isNotEqualTo(transporterAccount2);
        transporterAccount1.setId(null);
        assertThat(transporterAccount1).isNotEqualTo(transporterAccount2);
    }
}
