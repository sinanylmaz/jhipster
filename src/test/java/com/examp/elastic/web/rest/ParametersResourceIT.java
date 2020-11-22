package com.examp.elastic.web.rest;

import com.examp.elastic.ElasticExampleApp;
import com.examp.elastic.domain.Parameters;
import com.examp.elastic.repository.ParametersRepository;
import com.examp.elastic.repository.search.ParametersSearchRepository;

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
 * Integration tests for the {@link ParametersResource} REST controller.
 */
@SpringBootTest(classes = ElasticExampleApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ParametersResourceIT {

    private static final String DEFAULT_PARAM_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PARAM_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCTEST = "AAAAAAAAAA";
    private static final String UPDATED_DESCTEST = "BBBBBBBBBB";

    @Autowired
    private ParametersRepository parametersRepository;

    /**
     * This repository is mocked in the com.examp.elastic.repository.search test package.
     *
     * @see com.examp.elastic.repository.search.ParametersSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParametersSearchRepository mockParametersSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParametersMockMvc;

    private Parameters parameters;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parameters createEntity(EntityManager em) {
        Parameters parameters = new Parameters()
            .paramKey(DEFAULT_PARAM_KEY)
            .paramValue(DEFAULT_PARAM_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .desctest(DEFAULT_DESCTEST);
        return parameters;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parameters createUpdatedEntity(EntityManager em) {
        Parameters parameters = new Parameters()
            .paramKey(UPDATED_PARAM_KEY)
            .paramValue(UPDATED_PARAM_VALUE)
            .description(UPDATED_DESCRIPTION)
            .desctest(UPDATED_DESCTEST);
        return parameters;
    }

    @BeforeEach
    public void initTest() {
        parameters = createEntity(em);
    }

    @Test
    @Transactional
    public void createParameters() throws Exception {
        int databaseSizeBeforeCreate = parametersRepository.findAll().size();
        // Create the Parameters
        restParametersMockMvc.perform(post("/api/parameters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parameters)))
            .andExpect(status().isCreated());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeCreate + 1);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getParamKey()).isEqualTo(DEFAULT_PARAM_KEY);
        assertThat(testParameters.getParamValue()).isEqualTo(DEFAULT_PARAM_VALUE);
        assertThat(testParameters.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testParameters.getDesctest()).isEqualTo(DEFAULT_DESCTEST);

        // Validate the Parameters in Elasticsearch
        verify(mockParametersSearchRepository, times(1)).save(testParameters);
    }

    @Test
    @Transactional
    public void createParametersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametersRepository.findAll().size();

        // Create the Parameters with an existing ID
        parameters.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametersMockMvc.perform(post("/api/parameters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parameters)))
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeCreate);

        // Validate the Parameters in Elasticsearch
        verify(mockParametersSearchRepository, times(0)).save(parameters);
    }


    @Test
    @Transactional
    public void getAllParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList
        restParametersMockMvc.perform(get("/api/parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameters.getId().intValue())))
            .andExpect(jsonPath("$.[*].paramKey").value(hasItem(DEFAULT_PARAM_KEY)))
            .andExpect(jsonPath("$.[*].paramValue").value(hasItem(DEFAULT_PARAM_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].desctest").value(hasItem(DEFAULT_DESCTEST)));
    }
    
    @Test
    @Transactional
    public void getParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get the parameters
        restParametersMockMvc.perform(get("/api/parameters/{id}", parameters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parameters.getId().intValue()))
            .andExpect(jsonPath("$.paramKey").value(DEFAULT_PARAM_KEY))
            .andExpect(jsonPath("$.paramValue").value(DEFAULT_PARAM_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.desctest").value(DEFAULT_DESCTEST));
    }
    @Test
    @Transactional
    public void getNonExistingParameters() throws Exception {
        // Get the parameters
        restParametersMockMvc.perform(get("/api/parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();

        // Update the parameters
        Parameters updatedParameters = parametersRepository.findById(parameters.getId()).get();
        // Disconnect from session so that the updates on updatedParameters are not directly saved in db
        em.detach(updatedParameters);
        updatedParameters
            .paramKey(UPDATED_PARAM_KEY)
            .paramValue(UPDATED_PARAM_VALUE)
            .description(UPDATED_DESCRIPTION)
            .desctest(UPDATED_DESCTEST);

        restParametersMockMvc.perform(put("/api/parameters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParameters)))
            .andExpect(status().isOk());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getParamKey()).isEqualTo(UPDATED_PARAM_KEY);
        assertThat(testParameters.getParamValue()).isEqualTo(UPDATED_PARAM_VALUE);
        assertThat(testParameters.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testParameters.getDesctest()).isEqualTo(UPDATED_DESCTEST);

        // Validate the Parameters in Elasticsearch
        verify(mockParametersSearchRepository, times(1)).save(testParameters);
    }

    @Test
    @Transactional
    public void updateNonExistingParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametersMockMvc.perform(put("/api/parameters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parameters)))
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Parameters in Elasticsearch
        verify(mockParametersSearchRepository, times(0)).save(parameters);
    }

    @Test
    @Transactional
    public void deleteParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeDelete = parametersRepository.findAll().size();

        // Delete the parameters
        restParametersMockMvc.perform(delete("/api/parameters/{id}", parameters.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Parameters in Elasticsearch
        verify(mockParametersSearchRepository, times(1)).deleteById(parameters.getId());
    }

    @Test
    @Transactional
    public void searchParameters() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);
        when(mockParametersSearchRepository.search(queryStringQuery("id:" + parameters.getId())))
            .thenReturn(Collections.singletonList(parameters));

        // Search the parameters
        restParametersMockMvc.perform(get("/api/_search/parameters?query=id:" + parameters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameters.getId().intValue())))
            .andExpect(jsonPath("$.[*].paramKey").value(hasItem(DEFAULT_PARAM_KEY)))
            .andExpect(jsonPath("$.[*].paramValue").value(hasItem(DEFAULT_PARAM_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].desctest").value(hasItem(DEFAULT_DESCTEST)));
    }
}
