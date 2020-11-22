package com.examp.elastic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.examp.elastic.web.rest.TestUtil;

public class ParametersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parameters.class);
        Parameters parameters1 = new Parameters();
        parameters1.setId(1L);
        Parameters parameters2 = new Parameters();
        parameters2.setId(parameters1.getId());
        assertThat(parameters1).isEqualTo(parameters2);
        parameters2.setId(2L);
        assertThat(parameters1).isNotEqualTo(parameters2);
        parameters1.setId(null);
        assertThat(parameters1).isNotEqualTo(parameters2);
    }
}
