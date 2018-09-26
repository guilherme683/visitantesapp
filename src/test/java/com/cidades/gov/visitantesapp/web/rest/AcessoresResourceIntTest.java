package com.cidades.gov.visitantesapp.web.rest;

import com.cidades.gov.visitantesapp.VisitantesappApp;

import com.cidades.gov.visitantesapp.domain.Acessores;
import com.cidades.gov.visitantesapp.repository.AcessoresRepository;
import com.cidades.gov.visitantesapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.cidades.gov.visitantesapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AcessoresResource REST controller.
 *
 * @see AcessoresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisitantesappApp.class)
public class AcessoresResourceIntTest {

    private static final String DEFAULT_NOME_ASSESSOR = "AAAAAAAAAA";
    private static final String UPDATED_NOME_ASSESSOR = "BBBBBBBBBB";

    @Autowired
    private AcessoresRepository acessoresRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAcessoresMockMvc;

    private Acessores acessores;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcessoresResource acessoresResource = new AcessoresResource(acessoresRepository);
        this.restAcessoresMockMvc = MockMvcBuilders.standaloneSetup(acessoresResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acessores createEntity(EntityManager em) {
        Acessores acessores = new Acessores()
            .nomeAssessor(DEFAULT_NOME_ASSESSOR);
        return acessores;
    }

    @Before
    public void initTest() {
        acessores = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcessores() throws Exception {
        int databaseSizeBeforeCreate = acessoresRepository.findAll().size();

        // Create the Acessores
        restAcessoresMockMvc.perform(post("/api/acessores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acessores)))
            .andExpect(status().isCreated());

        // Validate the Acessores in the database
        List<Acessores> acessoresList = acessoresRepository.findAll();
        assertThat(acessoresList).hasSize(databaseSizeBeforeCreate + 1);
        Acessores testAcessores = acessoresList.get(acessoresList.size() - 1);
        assertThat(testAcessores.getNomeAssessor()).isEqualTo(DEFAULT_NOME_ASSESSOR);
    }

    @Test
    @Transactional
    public void createAcessoresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acessoresRepository.findAll().size();

        // Create the Acessores with an existing ID
        acessores.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcessoresMockMvc.perform(post("/api/acessores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acessores)))
            .andExpect(status().isBadRequest());

        // Validate the Acessores in the database
        List<Acessores> acessoresList = acessoresRepository.findAll();
        assertThat(acessoresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAcessores() throws Exception {
        // Initialize the database
        acessoresRepository.saveAndFlush(acessores);

        // Get all the acessoresList
        restAcessoresMockMvc.perform(get("/api/acessores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acessores.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeAssessor").value(hasItem(DEFAULT_NOME_ASSESSOR.toString())));
    }
    

    @Test
    @Transactional
    public void getAcessores() throws Exception {
        // Initialize the database
        acessoresRepository.saveAndFlush(acessores);

        // Get the acessores
        restAcessoresMockMvc.perform(get("/api/acessores/{id}", acessores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acessores.getId().intValue()))
            .andExpect(jsonPath("$.nomeAssessor").value(DEFAULT_NOME_ASSESSOR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAcessores() throws Exception {
        // Get the acessores
        restAcessoresMockMvc.perform(get("/api/acessores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcessores() throws Exception {
        // Initialize the database
        acessoresRepository.saveAndFlush(acessores);

        int databaseSizeBeforeUpdate = acessoresRepository.findAll().size();

        // Update the acessores
        Acessores updatedAcessores = acessoresRepository.findById(acessores.getId()).get();
        // Disconnect from session so that the updates on updatedAcessores are not directly saved in db
        em.detach(updatedAcessores);
        updatedAcessores
            .nomeAssessor(UPDATED_NOME_ASSESSOR);

        restAcessoresMockMvc.perform(put("/api/acessores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcessores)))
            .andExpect(status().isOk());

        // Validate the Acessores in the database
        List<Acessores> acessoresList = acessoresRepository.findAll();
        assertThat(acessoresList).hasSize(databaseSizeBeforeUpdate);
        Acessores testAcessores = acessoresList.get(acessoresList.size() - 1);
        assertThat(testAcessores.getNomeAssessor()).isEqualTo(UPDATED_NOME_ASSESSOR);
    }

    @Test
    @Transactional
    public void updateNonExistingAcessores() throws Exception {
        int databaseSizeBeforeUpdate = acessoresRepository.findAll().size();

        // Create the Acessores

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restAcessoresMockMvc.perform(put("/api/acessores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acessores)))
            .andExpect(status().isBadRequest());

        // Validate the Acessores in the database
        List<Acessores> acessoresList = acessoresRepository.findAll();
        assertThat(acessoresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcessores() throws Exception {
        // Initialize the database
        acessoresRepository.saveAndFlush(acessores);

        int databaseSizeBeforeDelete = acessoresRepository.findAll().size();

        // Get the acessores
        restAcessoresMockMvc.perform(delete("/api/acessores/{id}", acessores.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Acessores> acessoresList = acessoresRepository.findAll();
        assertThat(acessoresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acessores.class);
        Acessores acessores1 = new Acessores();
        acessores1.setId(1L);
        Acessores acessores2 = new Acessores();
        acessores2.setId(acessores1.getId());
        assertThat(acessores1).isEqualTo(acessores2);
        acessores2.setId(2L);
        assertThat(acessores1).isNotEqualTo(acessores2);
        acessores1.setId(null);
        assertThat(acessores1).isNotEqualTo(acessores2);
    }
}
