package com.examp.elastic.repository.search;

import com.examp.elastic.domain.Testtable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Testtable} entity.
 */
public interface TesttableSearchRepository extends ElasticsearchRepository<Testtable, Long> {
}
