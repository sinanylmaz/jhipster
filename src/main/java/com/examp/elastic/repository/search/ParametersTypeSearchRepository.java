package com.examp.elastic.repository.search;

import com.examp.elastic.domain.ParametersType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ParametersType} entity.
 */
public interface ParametersTypeSearchRepository extends ElasticsearchRepository<ParametersType, Long> {
}
