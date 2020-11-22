package com.examp.elastic.web.rest;

import com.examp.elastic.ElasticExampleApp;
import com.examp.elastic.domain.ParametersType;
import com.examp.elastic.repository.ParametersTypeRepository;
import com.examp.elastic.repository.search.ParametersTypeSearchRepository;

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
 * Integration tests for the {@link ParametersTypeResource} REST controller.
 */
@SpringBootTest(classes = ElasticExampleApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ParametersTypeResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ParametersTypeRepository parametersTypeRepository;

    /**
     * This repository is mocked in the com.examp.elastic.repository.search test package.
     *
     * @see com.examp.elastic.repository.search.ParametersTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParametersTypeSearchRepository mockParametersTypeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParametersTypeMockMvc;

    private ParametersType parametersType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParametersType createEntity(EntityManager em) {
        ParametersType parametersType = new ParametersType()
            .description(DEFAULT_DESCRIPTION);
        return parametersType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParametersType createUpdatedEntity(EntityManager em) {
        ParametersType parametersType = new ParametersType()
            .description(UPDATED_DESCRIPTION);
        return parametersType;
    }

    @BeforeEach
    public void initTest() {
        parametersType = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametersType() throws Exception {
        int databaseSizeBeforeCreate = parametersTypeRepository.findAll().size();
        // Create the ParametersType
        restParametersTypeMockMvc.perform(post("/api/parameters-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametersType)))
            .andExpect(status().isCreated());

        // Validate the ParametersType in the database
        List<ParametersType> parametersTypeList = parametersTypeRepository.findAll();
        assertThat(parametersTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ParametersType testParametersType = parametersTypeList.get(parametersTypeList.size() - 1);
        assertThat(testParametersType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ParametersType in Elasticsearch
        verify(mockParametersTypeSearchRepository, times(1)).save(testParametersType);
    }

    @Test
    @Transactional
    public void createParametersTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametersTypeRepository.findAll().size();

        // Create the ParametersType with an existing ID
        parametersType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametersTypeMockMvc.perform(post("/api/parameters-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametersType)))
            .andExpect(status().isBadRequest());

        // Validate the ParametersType in the database
        List<ParametersType> parametersTypeList = parametersTypeRepository.findAll();
        assertThat(parametersTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParametersType in Elasticsearch
        verify(mockParametersTypeSearchRepository, times(0)).save(parametersType);
    }


    @Test
    @Transactional
    public void getAllParametersTypes() throws Exception {
        // Initialize the database
        parametersTypeRepository.saveAndFlush(parametersType);

        // Get all the parametersTypeList
        restParametersTypeMockMvc.perform(get("/api/parameters-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametersType.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getParametersType() throws Exception {
        // Initialize the database
        parametersTypeRepository.saveAndFlush(parametersType);

        // Get the parametersType
        restParametersTypeMockMvc.perform(get("/api/parameters-types/{id}", parametersType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parametersType.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingParametersType() throws Exception {
        // Get the parametersType
        restParametersTypeMockMvc.perform(get("/api/parameters-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametersType() throws Exception {
        // Initialize the database
        parametersTypeRepository.saveAndFlush(parametersType);

        int databaseSizeBeforeUpdate = parametersTypeRepository.findAll().size();

        // Update the parametersType
        ParametersType updatedParametersType = parametersTypeRepository.findById(parametersType.getId()).get();
        // Disconnect from session so that the updates on updatedParametersType are not directly saved in db
        em.detach(updatedParametersType);
        updatedParametersType
            .description(UPDATED_DESCRIPTION);

        restParametersTypeMockMvc.perform(put("/api/parameters-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParametersType)))
            .andExpect(status().isOk());

        // Validate the ParametersType in the database
        List<ParametersType> parametersTypeList = parametersTypeRepository.findAll();
        assertThat(parametersTypeList).hasSize(databaseSizeBeforeUpdate);
        ParametersType testParametersType = parametersTypeList.get(parametersTypeList.size() - 1);
        assertThat(testParametersType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ParametersType in Elasticsearch
        verify(mockParametersTypeSearchRepository, times(1)).save(testParametersType);
    }

    @Test
    @Transactional
    public void updateNonExistingParametersType() throws Exception {
        int databaseSizeBeforeUpdate = parametersTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametersTypeMockMvc.perform(put("/api/parameters-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametersType)))
            .andExpect(status().isBadRequest());

        // Validate the ParametersType in the database
        List<ParametersType> parametersTypeList = parametersTypeRepository.findAll();
        assertThat(parametersTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParametersType in Elasticsearch
        verify(mockParametersTypeSearchRepository, times(0)).save(parametersType);
    }

    @Test
    @Transactional
    public void deleteParametersType() throws Exception {
        // Initialize the database
        parametersTypeRepository.saveAndFlush(parametersType);

        int databaseSizeBeforeDelete = parametersTypeRepository.findAll().size();

        // Delete the parametersType
        restParametersTypeMockMvc.perform(delete("/api/parameters-types/{id}", parametersType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParametersType> parametersTypeList = parametersTypeRepository.findAll();
        assertThat(parametersTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParametersType in Elasticsearch
        verify(mockParametersTypeSearchRepository, times(1)).deleteById(parametersType.getId());
    }

    @Test
    @Transactional
    public void searchParametersType() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        parametersTypeRepository.saveAndFlush(parametersType);
        when(mockParametersTypeSearchRepository.search(queryStringQuery("id:" + parametersType.getId())))
            .thenReturn(Collections.singletonList(parametersType));

        // Search the parametersType
        restParametersTypeMockMvc.perform(get("/api/_search/parameters-types?query=id:" + parametersType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametersType.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
