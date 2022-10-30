package co.edu.acde.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvaluatorMapperTest {

    private EvaluatorMapper evaluatorMapper;

    @BeforeEach
    public void setUp() {
        evaluatorMapper = new EvaluatorMapperImpl();
    }
}
