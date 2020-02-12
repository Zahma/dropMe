package com.abrid.dropme.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.abrid.dropme.web.rest.TestUtil;

public class TruckDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TruckDTO.class);
        TruckDTO truckDTO1 = new TruckDTO();
        truckDTO1.setId(1L);
        TruckDTO truckDTO2 = new TruckDTO();
        assertThat(truckDTO1).isNotEqualTo(truckDTO2);
        truckDTO2.setId(truckDTO1.getId());
        assertThat(truckDTO1).isEqualTo(truckDTO2);
        truckDTO2.setId(2L);
        assertThat(truckDTO1).isNotEqualTo(truckDTO2);
        truckDTO1.setId(null);
        assertThat(truckDTO1).isNotEqualTo(truckDTO2);
    }
}
