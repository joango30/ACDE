package co.edu.acde.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.acde.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObservationAssessmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservationAssessmentDTO.class);
        ObservationAssessmentDTO observationAssessmentDTO1 = new ObservationAssessmentDTO();
        observationAssessmentDTO1.setId(1L);
        ObservationAssessmentDTO observationAssessmentDTO2 = new ObservationAssessmentDTO();
        assertThat(observationAssessmentDTO1).isNotEqualTo(observationAssessmentDTO2);
        observationAssessmentDTO2.setId(observationAssessmentDTO1.getId());
        assertThat(observationAssessmentDTO1).isEqualTo(observationAssessmentDTO2);
        observationAssessmentDTO2.setId(2L);
        assertThat(observationAssessmentDTO1).isNotEqualTo(observationAssessmentDTO2);
        observationAssessmentDTO1.setId(null);
        assertThat(observationAssessmentDTO1).isNotEqualTo(observationAssessmentDTO2);
    }
}
