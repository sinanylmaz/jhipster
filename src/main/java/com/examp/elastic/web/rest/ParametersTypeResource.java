package com.examp.elastic.web.rest;

import com.examp.elastic.domain.ParametersType;
import com.examp.elastic.repository.ParametersTypeRepository;
import com.examp.elastic.repository.search.ParametersTypeSearchRepository;
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
 * REST controller for managing {@link com.examp.elastic.domain.ParametersType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ParametersTypeResource {

    private final Logger log = LoggerFactory.getLogger(ParametersTypeResource.class);

    private static final String ENTITY_NAME = "parametersType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametersTypeRepository parametersTypeRepository;

    private final ParametersTypeSearchRepository parametersTypeSearchRepository;

    public ParametersTypeResource(ParametersTypeRepository parametersTypeRepository, ParametersTypeSearchRepository parametersTypeSearchRepository) {
        this.parametersTypeRepository = parametersTypeRepository;
        this.parametersTypeSearchRepository = parametersTypeSearchRepository;
    }

    /**
     * {@code POST  /parameters-types} : Create a new parametersType.
     *
     * @param parametersType the parametersType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametersType, or with status {@code 400 (Bad Request)} if the parametersType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parameters-types")
    public ResponseEntity<ParametersType> createParametersType(@RequestBody ParametersType parametersType) throws URISyntaxException {
        log.debug("REST request to save ParametersType : {}", parametersType);
        if (parametersType.getId() != null) {
            throw new BadRequestAlertException("A new parametersType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParametersType result = parametersTypeRepository.save(parametersType);
        parametersTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/parameters-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parameters-types} : Updates an existing parametersType.
     *
     * @param parametersType the parametersType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametersType,
     * or with status {@code 400 (Bad Request)} if the parametersType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametersType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parameters-types")
    public ResponseEntity<ParametersType> updateParametersType(@RequestBody ParametersType parametersType) throws URISyntaxException {
        log.debug("REST request to update ParametersType : {}", parametersType);
        if (parametersType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParametersType result = parametersTypeRepository.save(parametersType);
        parametersTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametersType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parameters-types} : get all the parametersTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametersTypes in body.
     */
    @GetMapping("/parameters-types")
    public List<ParametersType> getAllParametersTypes() {
        log.debug("REST request to get all ParametersTypes");
        return parametersTypeRepository.findAll();
    }

    /**
     * {@code GET  /parameters-types/:id} : get the "id" parametersType.
     *
     * @param id the id of the parametersType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametersType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parameters-types/{id}")
    public ResponseEntity<ParametersType> getParametersType(@PathVariable Long id) {
        log.debug("REST request to get ParametersType : {}", id);
        Optional<ParametersType> parametersType = parametersTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parametersType);
    }

    /**
     * {@code DELETE  /parameters-types/:id} : delete the "id" parametersType.
     *
     * @param id the id of the parametersType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parameters-types/{id}")
    public ResponseEntity<Void> deleteParametersType(@PathVariable Long id) {
        log.debug("REST request to delete ParametersType : {}", id);
        parametersTypeRepository.deleteById(id);
        parametersTypeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/parameters-types?query=:query} : search for the parametersType corresponding
     * to the query.
     *
     * @param query the query of the parametersType search.
     * @return the result of the search.
     */
    @GetMapping("/_search/parameters-types")
    public List<ParametersType> searchParametersTypes(@RequestParam String query) {
        log.debug("REST request to search ParametersTypes for query {}", query);
        return StreamSupport
            .stream(parametersTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
