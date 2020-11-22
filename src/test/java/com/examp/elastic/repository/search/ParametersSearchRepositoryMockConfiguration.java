package com.examp.elastic.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ParametersSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ParametersSearchRepositoryMockConfiguration {

    @MockBean
    private ParametersSearchRepository mockParametersSearchRepository;

}
