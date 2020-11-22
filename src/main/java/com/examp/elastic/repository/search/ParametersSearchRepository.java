package com.examp.elastic.repository.search;

import com.examp.elastic.domain.Parameters;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Parameters} entity.
 */
public interface ParametersSearchRepository extends ElasticsearchRepository<Parameters, Long> {
}
