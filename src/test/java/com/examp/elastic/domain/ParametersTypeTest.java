package com.examp.elastic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.examp.elastic.web.rest.TestUtil;

public class ParametersTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametersType.class);
        ParametersType parametersType1 = new ParametersType();
        parametersType1.setId(1L);
        ParametersType parametersType2 = new ParametersType();
        parametersType2.setId(parametersType1.getId());
        assertThat(parametersType1).isEqualTo(parametersType2);
        parametersType2.setId(2L);
        assertThat(parametersType1).isNotEqualTo(parametersType2);
        parametersType1.setId(null);
        assertThat(parametersType1).isNotEqualTo(parametersType2);
    }
}
