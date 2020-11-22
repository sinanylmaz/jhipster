package com.examp.elastic.web.rest;

import com.examp.elastic.ElasticExampleApp;
import com.examp.elastic.domain.Testtable;
import com.examp.elastic.repository.TesttableRepository;
import com.examp.elastic.repository.search.TesttableSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TesttableResource} REST controller.
 */
@SpringBootTest(classes = ElasticExampleApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TesttableResourceIT {

    private static final String DEFAULT_PARAM_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PARAM_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMETERS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETERS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCTEST = "AAAAAAAAAA";
    private static final String UPDATED_DESCTEST = "BBBBBBBBBB";

    @Autowired
    private TesttableRepository testtableRepository;

    /**
     * This repository is mocked in the com.examp.elastic.repository.search test package.
     *
     * @see com.examp.elastic.repository.search.TesttableSearchRepositoryMockConfiguration
     */
    @Autowired
    private TesttableSearchRepository mockTesttableSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTesttableMockMvc;

    private Testtable testtable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Testtable createEntity(EntityManager em) {
        Testtable testtable = new Testtable()
            .paramKey(DEFAULT_PARAM_KEY)
            .paramValue(DEFAULT_PARAM_VALUE)
            .parametersType(DEFAULT_PARAMETERS_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .desctest(DEFAULT_DESCTEST);
        return testtable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Testtable createUpdatedEntity(EntityManager em) {
        Testtable testtable = new Testtable()
            .paramKey(UPDATED_PARAM_KEY)
            .paramValue(UPDATED_PARAM_VALUE)
            .parametersType(UPDATED_PARAMETERS_TYPE)
            .description(UPDATED_DESCRIPTION)
            .desctest(UPDATED_DESCTEST);
        return testtable;
    }

    @BeforeEach
    public void initTest() {
        testtable = createEntity(em);
    }

    @Test
    @Transactional
    public void createTesttable() throws Exception {
        int databaseSizeBeforeCreate = testtableRepository.findAll().size();
        // Create the Testtable
        restTesttableMockMvc.perform(post("/api/testtables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testtable)))
            .andExpect(status().isCreated());

        // Validate the Testtable in the database
        List<Testtable> testtableList = testtableRepository.findAll();
        assertThat(testtableList).hasSize(databaseSizeBeforeCreate + 1);
        Testtable testTesttable = testtableList.get(testtableList.size() - 1);
        assertThat(testTesttable.getParamKey()).isEqualTo(DEFAULT_PARAM_KEY);
        assertThat(testTesttable.getParamValue()).isEqualTo(DEFAULT_PARAM_VALUE);
        assertThat(testTesttable.getParametersType()).isEqualTo(DEFAULT_PARAMETERS_TYPE);
        assertThat(testTesttable.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTesttable.getDesctest()).isEqualTo(DEFAULT_DESCTEST);

        // Validate the Testtable in Elasticsearch
        verify(mockTesttableSearchRepository, times(1)).save(testTesttable);
    }

    @Test
    @Transactional
    public void createTesttableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testtableRepository.findAll().size();

        // Create the Testtable with an existing ID
        testtable.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTesttableMockMvc.perform(post("/api/testtables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testtable)))
            .andExpect(status().isBadRequest());

        // Validate the Testtable in the database
        List<Testtable> testtableList = testtableRepository.findAll();
        assertThat(testtableList).hasSize(databaseSizeBeforeCreate);

        // Validate the Testtable in Elasticsearch
        verify(mockTesttableSearchRepository, times(0)).save(testtable);
    }


    @Test
    @Transactional
    public void getAllTesttables() throws Exception {
        // Initialize the database
        testtableRepository.saveAndFlush(testtable);

        // Get all the testtableList
        restTesttableMockMvc.perform(get("/api/testtables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testtable.getId().intValue())))
            .andExpect(jsonPath("$.[*].paramKey").value(hasItem(DEFAULT_PARAM_KEY)))
            .andExpect(jsonPath("$.[*].paramValue").value(hasItem(DEFAULT_PARAM_VALUE)))
            .andExpect(jsonPath("$.[*].parametersType").value(hasItem(DEFAULT_PARAMETERS_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].desctest").value(hasItem(DEFAULT_DESCTEST)));
    }
    
    @Test
    @Transactional
    public void getTesttable() throws Exception {
        // Initialize the database
        testtableRepository.saveAndFlush(testtable);

        // Get the testtable
        restTesttableMockMvc.perform(get("/api/testtables/{id}", testtable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(testtable.getId().intValue()))
            .andExpect(jsonPath("$.paramKey").value(DEFAULT_PARAM_KEY))
            .andExpect(jsonPath("$.paramValue").value(DEFAULT_PARAM_VALUE))
            .andExpect(jsonPath("$.parametersType").value(DEFAULT_PARAMETERS_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.desctest").value(DEFAULT_DESCTEST));
    }
    @Test
    @Transactional
    public void getNonExistingTesttable() throws Exception {
        // Get the testtable
        restTesttableMockMvc.perform(get("/api/testtables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTesttable() throws Exception {
        // Initialize the database
        testtableRepository.saveAndFlush(testtable);

        int databaseSizeBeforeUpdate = testtableRepository.findAll().size();

        // Update the testtable
        Testtable updatedTesttable = testtableRepository.findById(testtable.getId()).get();
        // Disconnect from session so that the updates on updatedTesttable are not directly saved in db
        em.detach(updatedTesttable);
        updatedTesttable
            .paramKey(UPDATED_PARAM_KEY)
            .paramValue(UPDATED_PARAM_VALUE)
            .parametersType(UPDATED_PARAMETERS_TYPE)
            .description(UPDATED_DESCRIPTION)
            .desctest(UPDATED_DESCTEST);

        restTesttableMockMvc.perform(put("/api/testtables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTesttable)))
            .andExpect(status().isOk());

        // Validate the Testtable in the database
        List<Testtable> testtableList = testtableRepository.findAll();
        assertThat(testtableList).hasSize(databaseSizeBeforeUpdate);
        Testtable testTesttable = testtableList.get(testtableList.size() - 1);
        assertThat(testTesttable.getParamKey()).isEqualTo(UPDATED_PARAM_KEY);
        assertThat(testTesttable.getParamValue()).isEqualTo(UPDATED_PARAM_VALUE);
        assertThat(testTesttable.getParametersType()).isEqualTo(UPDATED_PARAMETERS_TYPE);
        assertThat(testTesttable.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTesttable.getDesctest()).isEqualTo(UPDATED_DESCTEST);

        // Validate the Testtable in Elasticsearch
        verify(mockTesttableSearchRepository, times(1)).save(testTesttable);
    }

    @Test
    @Transactional
    public void updateNonExistingTesttable() throws Exception {
        int databaseSizeBeforeUpdate = testtableRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTesttableMockMvc.perform(put("/api/testtables").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testtable)))
            .andExpect(status().isBadRequest());

        // Validate the Testtable in the database
        List<Testtable> testtableList = testtableRepository.findAll();
        assertThat(testtableList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Testtable in Elasticsearch
        verify(mockTesttableSearchRepository, times(0)).save(testtable);
    }

    @Test
    @Transactional
    public void deleteTesttable() throws Exception {
        // Initialize the database
        testtableRepository.saveAndFlush(testtable);

        int databaseSizeBeforeDelete = testtableRepository.findAll().size();

        // Delete the testtable
        restTesttableMockMvc.perform(delete("/api/testtables/{id}", testtable.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Testtable> testtableList = testtableRepository.findAll();
        assertThat(testtableList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Testtable in Elasticsearch
        verify(mockTesttableSearchRepository, times(1)).deleteById(testtable.getId());
    }

    @Test
    @Transactional
    public void searchTesttable() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        testtableRepository.saveAndFlush(testtable);
        when(mockTesttableSearchRepository.search(queryStringQuery("id:" + testtable.getId())))
            .thenReturn(Collections.singletonList(testtable));

        // Search the testtable
        restTesttableMockMvc.perform(get("/api/_search/testtables?query=id:" + testtable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testtable.getId().intValue())))
            .andExpect(jsonPath("$.[*].paramKey").value(hasItem(DEFAULT_PARAM_KEY)))
            .andExpect(jsonPath("$.[*].paramValue").value(hasItem(DEFAULT_PARAM_VALUE)))
            .andExpect(jsonPath("$.[*].parametersType").value(hasItem(DEFAULT_PARAMETERS_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].desctest").value(hasItem(DEFAULT_DESCTEST)));
    }
}
