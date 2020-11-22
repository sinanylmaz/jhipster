package com.examp.elastic.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ParametersTypeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ParametersTypeSearchRepositoryMockConfiguration {

    @MockBean
    private ParametersTypeSearchRepository mockParametersTypeSearchRepository;

}
