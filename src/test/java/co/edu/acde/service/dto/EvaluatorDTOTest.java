package co.edu.acde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.acde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluatorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluatorDTO.class);
        EvaluatorDTO evaluatorDTO1 = new EvaluatorDTO();
        evaluatorDTO1.setId(1L);
        EvaluatorDTO evaluatorDTO2 = new EvaluatorDTO();
        assertThat(evaluatorDTO1).isNotEqualTo(evaluatorDTO2);
        evaluatorDTO2.setId(evaluatorDTO1.getId());
        assertThat(evaluatorDTO1).isEqualTo(evaluatorDTO2);
        evaluatorDTO2.setId(2L);
        assertThat(evaluatorDTO1).isNotEqualTo(evaluatorDTO2);
        evaluatorDTO1.setId(null);
        assertThat(evaluatorDTO1).isNotEqualTo(evaluatorDTO2);
    }
}
