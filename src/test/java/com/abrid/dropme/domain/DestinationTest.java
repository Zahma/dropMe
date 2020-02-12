package com.abrid.dropme.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.abrid.dropme.web.rest.TestUtil;

public class DestinationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Destination.class);
        Destination destination1 = new Destination();
        destination1.setId(1L);
        Destination destination2 = new Destination();
        destination2.setId(destination1.getId());
        assertThat(destination1).isEqualTo(destination2);
        destination2.setId(2L);
        assertThat(destination1).isNotEqualTo(destination2);
        destination1.setId(null);
        assertThat(destination1).isNotEqualTo(destination2);
    }
}
