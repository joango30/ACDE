package co.edu.acde.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.acde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evaluator.class);
        Evaluator evaluator1 = new Evaluator();
        evaluator1.setId(1L);
        Evaluator evaluator2 = new Evaluator();
        evaluator2.setId(evaluator1.getId());
        assertThat(evaluator1).isEqualTo(evaluator2);
        evaluator2.setId(2L);
        assertThat(evaluator1).isNotEqualTo(evaluator2);
        evaluator1.setId(null);
        assertThat(evaluator1).isNotEqualTo(evaluator2);
    }
}
