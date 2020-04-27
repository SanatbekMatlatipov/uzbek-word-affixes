package uz.uzkaznlptools.admin.api.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.uzkaznlptools.admin.api.web.rest.TestUtil;

public class EndingsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndingsDTO.class);
        EndingsDTO endingsDTO1 = new EndingsDTO();
        endingsDTO1.setId(1L);
        EndingsDTO endingsDTO2 = new EndingsDTO();
        assertThat(endingsDTO1).isNotEqualTo(endingsDTO2);
        endingsDTO2.setId(endingsDTO1.getId());
        assertThat(endingsDTO1).isEqualTo(endingsDTO2);
        endingsDTO2.setId(2L);
        assertThat(endingsDTO1).isNotEqualTo(endingsDTO2);
        endingsDTO1.setId(null);
        assertThat(endingsDTO1).isNotEqualTo(endingsDTO2);
    }
}
