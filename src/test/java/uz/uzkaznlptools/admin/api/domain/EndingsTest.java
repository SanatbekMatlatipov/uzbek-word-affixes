package uz.uzkaznlptools.admin.api.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.uzkaznlptools.admin.api.web.rest.TestUtil;

public class EndingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Endings.class);
        Endings endings1 = new Endings();
        endings1.setId(1L);
        Endings endings2 = new Endings();
        endings2.setId(endings1.getId());
        assertThat(endings1).isEqualTo(endings2);
        endings2.setId(2L);
        assertThat(endings1).isNotEqualTo(endings2);
        endings1.setId(null);
        assertThat(endings1).isNotEqualTo(endings2);
    }
}
