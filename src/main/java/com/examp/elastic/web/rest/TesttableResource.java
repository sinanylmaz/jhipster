package com.examp.elastic.web.rest;

import com.examp.elastic.domain.Testtable;
import com.examp.elastic.repository.TesttableRepository;
import com.examp.elastic.repository.search.TesttableSearchRepository;
import com.examp.elastic.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.examp.elastic.domain.Testtable}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TesttableResource {

    private final Logger log = LoggerFactory.getLogger(TesttableResource.class);

    private static final String ENTITY_NAME = "testtable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TesttableRepository testtableRepository;

    private final TesttableSearchRepository testtableSearchRepository;

    public TesttableResource(TesttableRepository testtableRepository, TesttableSearchRepository testtableSearchRepository) {
        this.testtableRepository = testtableRepository;
        this.testtableSearchRepository = testtableSearchRepository;
    }

    /**
     * {@code POST  /testtables} : Create a new testtable.
     *
     * @param testtable the testtable to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testtable, or with status {@code 400 (Bad Request)} if the testtable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/testtables")
    public ResponseEntity<Testtable> createTesttable(@RequestBody Testtable testtable) throws URISyntaxException {
        log.debug("REST request to save Testtable : {}", testtable);
        if (testtable.getId() != null) {
            throw new BadRequestAlertException("A new testtable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Testtable result = testtableRepository.save(testtable);
        testtableSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/testtables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /testtables} : Updates an existing testtable.
     *
     * @param testtable the testtable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testtable,
     * or with status {@code 400 (Bad Request)} if the testtable is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testtable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/testtables")
    public ResponseEntity<Testtable> updateTesttable(@RequestBody Testtable testtable) throws URISyntaxException {
        log.debug("REST request to update Testtable : {}", testtable);
        if (testtable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Testtable result = testtableRepository.save(testtable);
        testtableSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, testtable.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /testtables} : get all the testtables.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testtables in body.
     */
    @GetMapping("/testtables")
    public List<Testtable> getAllTesttables() {
        log.debug("REST request to get all Testtables");
        return testtableRepository.findAll();
    }

    /**
     * {@code GET  /testtables/:id} : get the "id" testtable.
     *
     * @param id the id of the testtable to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testtable, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/testtables/{id}")
    public ResponseEntity<Testtable> getTesttable(@PathVariable Long id) {
        log.debug("REST request to get Testtable : {}", id);
        Optional<Testtable> testtable = testtableRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(testtable);
    }

    /**
     * {@code DELETE  /testtables/:id} : delete the "id" testtable.
     *
     * @param id the id of the testtable to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/testtables/{id}")
    public ResponseEntity<Void> deleteTesttable(@PathVariable Long id) {
        log.debug("REST request to delete Testtable : {}", id);
        testtableRepository.deleteById(id);
        testtableSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/testtables?query=:query} : search for the testtable corresponding
     * to the query.
     *
     * @param query the query of the testtable search.
     * @return the result of the search.
     */
    @GetMapping("/_search/testtables")
    public List<Testtable> searchTesttables(@RequestParam String query) {
        log.debug("REST request to search Testtables for query {}", query);
        return StreamSupport
            .stream(testtableSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
