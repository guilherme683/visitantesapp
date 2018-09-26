package com.cidades.gov.visitantesapp.web.rest;

import com.cidades.gov.visitantesapp.VisitantesappApp;

import com.cidades.gov.visitantesapp.domain.Estados;
import com.cidades.gov.visitantesapp.repository.EstadosRepository;
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
 * Test class for the EstadosResource REST controller.
 *
 * @see EstadosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisitantesappApp.class)
public class EstadosResourceIntTest {

    private static final Long DEFAULT_COD_ESTADOS_IBGE = 1L;
    private static final Long UPDATED_COD_ESTADOS_IBGE = 2L;

    private static final String DEFAULT_NOME_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA_ESTADO = "BBBBBBBBBB";

    private static final Long DEFAULT_COD_REGIOES = 1L;
    private static final Long UPDATED_COD_REGIOES = 2L;

    @Autowired
    private EstadosRepository estadosRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEstadosMockMvc;

    private Estados estados;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstadosResource estadosResource = new EstadosResource(estadosRepository);
        this.restEstadosMockMvc = MockMvcBuilders.standaloneSetup(estadosResource)
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
    public static Estados createEntity(EntityManager em) {
        Estados estados = new Estados()
            .codEstadosIbge(DEFAULT_COD_ESTADOS_IBGE)
            .nomeEstado(DEFAULT_NOME_ESTADO)
            .siglaEstado(DEFAULT_SIGLA_ESTADO)
            .codRegioes(DEFAULT_COD_REGIOES);
        return estados;
    }

    @Before
    public void initTest() {
        estados = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstados() throws Exception {
        int databaseSizeBeforeCreate = estadosRepository.findAll().size();

        // Create the Estados
        restEstadosMockMvc.perform(post("/api/estados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estados)))
            .andExpect(status().isCreated());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeCreate + 1);
        Estados testEstados = estadosList.get(estadosList.size() - 1);
        assertThat(testEstados.getCodEstadosIbge()).isEqualTo(DEFAULT_COD_ESTADOS_IBGE);
        assertThat(testEstados.getNomeEstado()).isEqualTo(DEFAULT_NOME_ESTADO);
        assertThat(testEstados.getSiglaEstado()).isEqualTo(DEFAULT_SIGLA_ESTADO);
        assertThat(testEstados.getCodRegioes()).isEqualTo(DEFAULT_COD_REGIOES);
    }

    @Test
    @Transactional
    public void createEstadosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estadosRepository.findAll().size();

        // Create the Estados with an existing ID
        estados.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadosMockMvc.perform(post("/api/estados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estados)))
            .andExpect(status().isBadRequest());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        // Get all the estadosList
        restEstadosMockMvc.perform(get("/api/estados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estados.getId().intValue())))
            .andExpect(jsonPath("$.[*].codEstadosIbge").value(hasItem(DEFAULT_COD_ESTADOS_IBGE.intValue())))
            .andExpect(jsonPath("$.[*].nomeEstado").value(hasItem(DEFAULT_NOME_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].siglaEstado").value(hasItem(DEFAULT_SIGLA_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].codRegioes").value(hasItem(DEFAULT_COD_REGIOES.intValue())));
    }
    

    @Test
    @Transactional
    public void getEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        // Get the estados
        restEstadosMockMvc.perform(get("/api/estados/{id}", estados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estados.getId().intValue()))
            .andExpect(jsonPath("$.codEstadosIbge").value(DEFAULT_COD_ESTADOS_IBGE.intValue()))
            .andExpect(jsonPath("$.nomeEstado").value(DEFAULT_NOME_ESTADO.toString()))
            .andExpect(jsonPath("$.siglaEstado").value(DEFAULT_SIGLA_ESTADO.toString()))
            .andExpect(jsonPath("$.codRegioes").value(DEFAULT_COD_REGIOES.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingEstados() throws Exception {
        // Get the estados
        restEstadosMockMvc.perform(get("/api/estados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();

        // Update the estados
        Estados updatedEstados = estadosRepository.findById(estados.getId()).get();
        // Disconnect from session so that the updates on updatedEstados are not directly saved in db
        em.detach(updatedEstados);
        updatedEstados
            .codEstadosIbge(UPDATED_COD_ESTADOS_IBGE)
            .nomeEstado(UPDATED_NOME_ESTADO)
            .siglaEstado(UPDATED_SIGLA_ESTADO)
            .codRegioes(UPDATED_COD_REGIOES);

        restEstadosMockMvc.perform(put("/api/estados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstados)))
            .andExpect(status().isOk());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
        Estados testEstados = estadosList.get(estadosList.size() - 1);
        assertThat(testEstados.getCodEstadosIbge()).isEqualTo(UPDATED_COD_ESTADOS_IBGE);
        assertThat(testEstados.getNomeEstado()).isEqualTo(UPDATED_NOME_ESTADO);
        assertThat(testEstados.getSiglaEstado()).isEqualTo(UPDATED_SIGLA_ESTADO);
        assertThat(testEstados.getCodRegioes()).isEqualTo(UPDATED_COD_REGIOES);
    }

    @Test
    @Transactional
    public void updateNonExistingEstados() throws Exception {
        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();

        // Create the Estados

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restEstadosMockMvc.perform(put("/api/estados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estados)))
            .andExpect(status().isBadRequest());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        int databaseSizeBeforeDelete = estadosRepository.findAll().size();

        // Get the estados
        restEstadosMockMvc.perform(delete("/api/estados/{id}", estados.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estados.class);
        Estados estados1 = new Estados();
        estados1.setId(1L);
        Estados estados2 = new Estados();
        estados2.setId(estados1.getId());
        assertThat(estados1).isEqualTo(estados2);
        estados2.setId(2L);
        assertThat(estados1).isNotEqualTo(estados2);
        estados1.setId(null);
        assertThat(estados1).isNotEqualTo(estados2);
    }
}
