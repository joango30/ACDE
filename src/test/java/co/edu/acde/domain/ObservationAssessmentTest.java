package co.edu.acde.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.acde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObservationAssessmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservationAssessment.class);
        ObservationAssessment observationAssessment1 = new ObservationAssessment();
        observationAssessment1.setId(1L);
        ObservationAssessment observationAssessment2 = new ObservationAssessment();
        observationAssessment2.setId(observationAssessment1.getId());
        assertThat(observationAssessment1).isEqualTo(observationAssessment2);
        observationAssessment2.setId(2L);
        assertThat(observationAssessment1).isNotEqualTo(observationAssessment2);
        observationAssessment1.setId(null);
        assertThat(observationAssessment1).isNotEqualTo(observationAssessment2);
    }
}
