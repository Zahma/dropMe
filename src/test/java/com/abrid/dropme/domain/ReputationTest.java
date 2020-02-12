package com.abrid.dropme.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.abrid.dropme.web.rest.TestUtil;

public class ReputationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reputation.class);
        Reputation reputation1 = new Reputation();
        reputation1.setId(1L);
        Reputation reputation2 = new Reputation();
        reputation2.setId(reputation1.getId());
        assertThat(reputation1).isEqualTo(reputation2);
        reputation2.setId(2L);
        assertThat(reputation1).isNotEqualTo(reputation2);
        reputation1.setId(null);
        assertThat(reputation1).isNotEqualTo(reputation2);
    }
}
