package com.abrid.dropme.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.abrid.dropme.web.rest.TestUtil;

public class DestinationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DestinationDTO.class);
        DestinationDTO destinationDTO1 = new DestinationDTO();
        destinationDTO1.setId(1L);
        DestinationDTO destinationDTO2 = new DestinationDTO();
        assertThat(destinationDTO1).isNotEqualTo(destinationDTO2);
        destinationDTO2.setId(destinationDTO1.getId());
        assertThat(destinationDTO1).isEqualTo(destinationDTO2);
        destinationDTO2.setId(2L);
        assertThat(destinationDTO1).isNotEqualTo(destinationDTO2);
        destinationDTO1.setId(null);
        assertThat(destinationDTO1).isNotEqualTo(destinationDTO2);
    }
}
