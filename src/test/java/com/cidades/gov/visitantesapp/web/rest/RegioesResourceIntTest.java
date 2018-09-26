package com.cidades.gov.visitantesapp.web.rest;

import com.cidades.gov.visitantesapp.VisitantesappApp;

import com.cidades.gov.visitantesapp.domain.Regioes;
import com.cidades.gov.visitantesapp.repository.RegioesRepository;
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
 * Test class for the RegioesResource REST controller.
 *
 * @see RegioesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisitantesappApp.class)
public class RegioesResourceIntTest {

    private static final Long DEFAULT_COD_REGIOES_IBGE = 1L;
    private static final Long UPDATED_COD_REGIOES_IBGE = 2L;

    private static final String DEFAULT_NOME_REGIAO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_REGIAO = "BBBBBBBBBB";

    @Autowired
    private RegioesRepository regioesRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegioesMockMvc;

    private Regioes regioes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegioesResource regioesResource = new RegioesResource(regioesRepository);
        this.restRegioesMockMvc = MockMvcBuilders.standaloneSetup(regioesResource)
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
    public static Regioes createEntity(EntityManager em) {
        Regioes regioes = new Regioes()
            .codRegioesIbge(DEFAULT_COD_REGIOES_IBGE)
            .nomeRegiao(DEFAULT_NOME_REGIAO);
        return regioes;
    }

    @Before
    public void initTest() {
        regioes = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegioes() throws Exception {
        int databaseSizeBeforeCreate = regioesRepository.findAll().size();

        // Create the Regioes
        restRegioesMockMvc.perform(post("/api/regioes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regioes)))
            .andExpect(status().isCreated());

        // Validate the Regioes in the database
        List<Regioes> regioesList = regioesRepository.findAll();
        assertThat(regioesList).hasSize(databaseSizeBeforeCreate + 1);
        Regioes testRegioes = regioesList.get(regioesList.size() - 1);
        assertThat(testRegioes.getCodRegioesIbge()).isEqualTo(DEFAULT_COD_REGIOES_IBGE);
        assertThat(testRegioes.getNomeRegiao()).isEqualTo(DEFAULT_NOME_REGIAO);
    }

    @Test
    @Transactional
    public void createRegioesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regioesRepository.findAll().size();

        // Create the Regioes with an existing ID
        regioes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegioesMockMvc.perform(post("/api/regioes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regioes)))
            .andExpect(status().isBadRequest());

        // Validate the Regioes in the database
        List<Regioes> regioesList = regioesRepository.findAll();
        assertThat(regioesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRegioes() throws Exception {
        // Initialize the database
        regioesRepository.saveAndFlush(regioes);

        // Get all the regioesList
        restRegioesMockMvc.perform(get("/api/regioes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regioes.getId().intValue())))
            .andExpect(jsonPath("$.[*].codRegioesIbge").value(hasItem(DEFAULT_COD_REGIOES_IBGE.intValue())))
            .andExpect(jsonPath("$.[*].nomeRegiao").value(hasItem(DEFAULT_NOME_REGIAO.toString())));
    }
    

    @Test
    @Transactional
    public void getRegioes() throws Exception {
        // Initialize the database
        regioesRepository.saveAndFlush(regioes);

        // Get the regioes
        restRegioesMockMvc.perform(get("/api/regioes/{id}", regioes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regioes.getId().intValue()))
            .andExpect(jsonPath("$.codRegioesIbge").value(DEFAULT_COD_REGIOES_IBGE.intValue()))
            .andExpect(jsonPath("$.nomeRegiao").value(DEFAULT_NOME_REGIAO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRegioes() throws Exception {
        // Get the regioes
        restRegioesMockMvc.perform(get("/api/regioes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegioes() throws Exception {
        // Initialize the database
        regioesRepository.saveAndFlush(regioes);

        int databaseSizeBeforeUpdate = regioesRepository.findAll().size();

        // Update the regioes
        Regioes updatedRegioes = regioesRepository.findById(regioes.getId()).get();
        // Disconnect from session so that the updates on updatedRegioes are not directly saved in db
        em.detach(updatedRegioes);
        updatedRegioes
            .codRegioesIbge(UPDATED_COD_REGIOES_IBGE)
            .nomeRegiao(UPDATED_NOME_REGIAO);

        restRegioesMockMvc.perform(put("/api/regioes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegioes)))
            .andExpect(status().isOk());

        // Validate the Regioes in the database
        List<Regioes> regioesList = regioesRepository.findAll();
        assertThat(regioesList).hasSize(databaseSizeBeforeUpdate);
        Regioes testRegioes = regioesList.get(regioesList.size() - 1);
        assertThat(testRegioes.getCodRegioesIbge()).isEqualTo(UPDATED_COD_REGIOES_IBGE);
        assertThat(testRegioes.getNomeRegiao()).isEqualTo(UPDATED_NOME_REGIAO);
    }

    @Test
    @Transactional
    public void updateNonExistingRegioes() throws Exception {
        int databaseSizeBeforeUpdate = regioesRepository.findAll().size();

        // Create the Regioes

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restRegioesMockMvc.perform(put("/api/regioes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regioes)))
            .andExpect(status().isBadRequest());

        // Validate the Regioes in the database
        List<Regioes> regioesList = regioesRepository.findAll();
        assertThat(regioesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegioes() throws Exception {
        // Initialize the database
        regioesRepository.saveAndFlush(regioes);

        int databaseSizeBeforeDelete = regioesRepository.findAll().size();

        // Get the regioes
        restRegioesMockMvc.perform(delete("/api/regioes/{id}", regioes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Regioes> regioesList = regioesRepository.findAll();
        assertThat(regioesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regioes.class);
        Regioes regioes1 = new Regioes();
        regioes1.setId(1L);
        Regioes regioes2 = new Regioes();
        regioes2.setId(regioes1.getId());
        assertThat(regioes1).isEqualTo(regioes2);
        regioes2.setId(2L);
        assertThat(regioes1).isNotEqualTo(regioes2);
        regioes1.setId(null);
        assertThat(regioes1).isNotEqualTo(regioes2);
    }
}
