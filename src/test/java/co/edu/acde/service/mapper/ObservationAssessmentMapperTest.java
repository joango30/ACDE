package co.edu.acde.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObservationAssessmentMapperTest {

    private ObservationAssessmentMapper observationAssessmentMapper;

    @BeforeEach
    public void setUp() {
        observationAssessmentMapper = new ObservationAssessmentMapperImpl();
    }
}
