package com.examp.elastic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.examp.elastic.web.rest.TestUtil;

public class TesttableTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Testtable.class);
        Testtable testtable1 = new Testtable();
        testtable1.setId(1L);
        Testtable testtable2 = new Testtable();
        testtable2.setId(testtable1.getId());
        assertThat(testtable1).isEqualTo(testtable2);
        testtable2.setId(2L);
        assertThat(testtable1).isNotEqualTo(testtable2);
        testtable1.setId(null);
        assertThat(testtable1).isNotEqualTo(testtable2);
    }
}
