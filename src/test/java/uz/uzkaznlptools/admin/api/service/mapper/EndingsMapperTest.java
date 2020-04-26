package uz.uzkaznlptools.admin.api.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EndingsMapperTest {

    private EndingsMapper endingsMapper;

    @BeforeEach
    public void setUp() {
        endingsMapper = new EndingsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(endingsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(endingsMapper.fromId(null)).isNull();
    }
}
