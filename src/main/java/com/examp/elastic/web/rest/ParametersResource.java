package com.examp.elastic.web.rest;

import com.examp.elastic.domain.Parameters;
import com.examp.elastic.repository.ParametersRepository;
import com.examp.elastic.repository.search.ParametersSearchRepository;
import com.examp.elastic.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.examp.elastic.domain.Parameters}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ParametersResource {

    private final Logger log = LoggerFactory.getLogger(ParametersResource.class);

    private static final String ENTITY_NAME = "parameters";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametersRepository parametersRepository;

    private final ParametersSearchRepository parametersSearchRepository;

    public ParametersResource(ParametersRepository parametersRepository, ParametersSearchRepository parametersSearchRepository) {
        this.parametersRepository = parametersRepository;
        this.parametersSearchRepository = parametersSearchRepository;
    }

    /**
     * {@code POST  /parameters} : Create a new parameters.
     *
     * @param parameters the parameters to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parameters, or with status {@code 400 (Bad Request)} if the parameters has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parameters")
    public ResponseEntity<Parameters> createParameters(@Valid @RequestBody Parameters parameters) throws URISyntaxException {
        log.debug("REST request to save Parameters : {}", parameters);
        if (parameters.getId() != null) {
            throw new BadRequestAlertException("A new parameters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parameters result = parametersRepository.save(parameters);
        parametersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parameters} : Updates an existing parameters.
     *
     * @param parameters the parameters to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parameters,
     * or with status {@code 400 (Bad Request)} if the parameters is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parameters couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parameters")
    public ResponseEntity<Parameters> updateParameters(@Valid @RequestBody Parameters parameters) throws URISyntaxException {
        log.debug("REST request to update Parameters : {}", parameters);
        if (parameters.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Parameters result = parametersRepository.save(parameters);
        parametersSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parameters.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parameters} : get all the parameters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parameters in body.
     */
    @GetMapping("/parameters")
    public List<Parameters> getAllParameters() {
        log.debug("REST request to get all Parameters");
        return parametersRepository.findAll();
    }

    /**
     * {@code GET  /parameters/:id} : get the "id" parameters.
     *
     * @param id the id of the parameters to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parameters, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parameters/{id}")
    public ResponseEntity<Parameters> getParameters(@PathVariable Long id) {
        log.debug("REST request to get Parameters : {}", id);
        Optional<Parameters> parameters = parametersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parameters);
    }

    /**
     * {@code DELETE  /parameters/:id} : delete the "id" parameters.
     *
     * @param id the id of the parameters to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parameters/{id}")
    public ResponseEntity<Void> deleteParameters(@PathVariable Long id) {
        log.debug("REST request to delete Parameters : {}", id);
        parametersRepository.deleteById(id);
        parametersSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/parameters?query=:query} : search for the parameters corresponding
     * to the query.
     *
     * @param query the query of the parameters search.
     * @return the result of the search.
     */
    @GetMapping("/_search/parameters")
    public List<Parameters> searchParameters(@RequestParam String query) {
        log.debug("REST request to search Parameters for query {}", query);
        return StreamSupport
            .stream(parametersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
