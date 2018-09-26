package com.cidades.gov.visitantesapp.web.rest;

import com.cidades.gov.visitantesapp.VisitantesappApp;

import com.cidades.gov.visitantesapp.domain.Visitantes;
import com.cidades.gov.visitantesapp.repository.VisitantesRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.cidades.gov.visitantesapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cidades.gov.visitantesapp.domain.enumeration.Status;
/**
 * Test class for the VisitantesResource REST controller.
 *
 * @see VisitantesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisitantesappApp.class)
public class VisitantesResourceIntTest {

    private static final Instant DEFAULT_DATA_VISITA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_VISITA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOME_VISITANTE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_VISITANTE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL = "BBBBBBBBBB";

    private static final String DEFAULT_ACOMPANHADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_ACOMPANHADO_POR = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS_ATENDIMENTO = Status.AGUARDANDO;
    private static final Status UPDATED_STATUS_ATENDIMENTO = Status.ATENDIDO;

    private static final String DEFAULT_CADASTRADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_CADASTRADO_POR = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private VisitantesRepository visitantesRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisitantesMockMvc;

    private Visitantes visitantes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VisitantesResource visitantesResource = new VisitantesResource(visitantesRepository);
        this.restVisitantesMockMvc = MockMvcBuilders.standaloneSetup(visitantesResource)
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
    public static Visitantes createEntity(EntityManager em) {
        Visitantes visitantes = new Visitantes()
            .dataVisita(DEFAULT_DATA_VISITA)
            .nomeVisitante(DEFAULT_NOME_VISITANTE)
            .local(DEFAULT_LOCAL)
            .acompanhadoPor(DEFAULT_ACOMPANHADO_POR)
            .estado(DEFAULT_ESTADO)
            .statusAtendimento(DEFAULT_STATUS_ATENDIMENTO)
            .cadastradoPor(DEFAULT_CADASTRADO_POR)
            .observacao(DEFAULT_OBSERVACAO);
        return visitantes;
    }

    @Before
    public void initTest() {
        visitantes = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisitantes() throws Exception {
        int databaseSizeBeforeCreate = visitantesRepository.findAll().size();

        // Create the Visitantes
        restVisitantesMockMvc.perform(post("/api/visitantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitantes)))
            .andExpect(status().isCreated());

        // Validate the Visitantes in the database
        List<Visitantes> visitantesList = visitantesRepository.findAll();
        assertThat(visitantesList).hasSize(databaseSizeBeforeCreate + 1);
        Visitantes testVisitantes = visitantesList.get(visitantesList.size() - 1);
        assertThat(testVisitantes.getDataVisita()).isEqualTo(DEFAULT_DATA_VISITA);
        assertThat(testVisitantes.getNomeVisitante()).isEqualTo(DEFAULT_NOME_VISITANTE);
        assertThat(testVisitantes.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testVisitantes.getAcompanhadoPor()).isEqualTo(DEFAULT_ACOMPANHADO_POR);
        assertThat(testVisitantes.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testVisitantes.getStatusAtendimento()).isEqualTo(DEFAULT_STATUS_ATENDIMENTO);
        assertThat(testVisitantes.getCadastradoPor()).isEqualTo(DEFAULT_CADASTRADO_POR);
        assertThat(testVisitantes.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    public void createVisitantesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitantesRepository.findAll().size();

        // Create the Visitantes with an existing ID
        visitantes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitantesMockMvc.perform(post("/api/visitantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitantes)))
            .andExpect(status().isBadRequest());

        // Validate the Visitantes in the database
        List<Visitantes> visitantesList = visitantesRepository.findAll();
        assertThat(visitantesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVisitantes() throws Exception {
        // Initialize the database
        visitantesRepository.saveAndFlush(visitantes);

        // Get all the visitantesList
        restVisitantesMockMvc.perform(get("/api/visitantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitantes.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataVisita").value(hasItem(DEFAULT_DATA_VISITA.toString())))
            .andExpect(jsonPath("$.[*].nomeVisitante").value(hasItem(DEFAULT_NOME_VISITANTE.toString())))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL.toString())))
            .andExpect(jsonPath("$.[*].acompanhadoPor").value(hasItem(DEFAULT_ACOMPANHADO_POR.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].statusAtendimento").value(hasItem(DEFAULT_STATUS_ATENDIMENTO.toString())))
            .andExpect(jsonPath("$.[*].cadastradoPor").value(hasItem(DEFAULT_CADASTRADO_POR.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));
    }
    

    @Test
    @Transactional
    public void getVisitantes() throws Exception {
        // Initialize the database
        visitantesRepository.saveAndFlush(visitantes);

        // Get the visitantes
        restVisitantesMockMvc.perform(get("/api/visitantes/{id}", visitantes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visitantes.getId().intValue()))
            .andExpect(jsonPath("$.dataVisita").value(DEFAULT_DATA_VISITA.toString()))
            .andExpect(jsonPath("$.nomeVisitante").value(DEFAULT_NOME_VISITANTE.toString()))
            .andExpect(jsonPath("$.local").value(DEFAULT_LOCAL.toString()))
            .andExpect(jsonPath("$.acompanhadoPor").value(DEFAULT_ACOMPANHADO_POR.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.statusAtendimento").value(DEFAULT_STATUS_ATENDIMENTO.toString()))
            .andExpect(jsonPath("$.cadastradoPor").value(DEFAULT_CADASTRADO_POR.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVisitantes() throws Exception {
        // Get the visitantes
        restVisitantesMockMvc.perform(get("/api/visitantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisitantes() throws Exception {
        // Initialize the database
        visitantesRepository.saveAndFlush(visitantes);

        int databaseSizeBeforeUpdate = visitantesRepository.findAll().size();

        // Update the visitantes
        Visitantes updatedVisitantes = visitantesRepository.findById(visitantes.getId()).get();
        // Disconnect from session so that the updates on updatedVisitantes are not directly saved in db
        em.detach(updatedVisitantes);
        updatedVisitantes
            .dataVisita(UPDATED_DATA_VISITA)
            .nomeVisitante(UPDATED_NOME_VISITANTE)
            .local(UPDATED_LOCAL)
            .acompanhadoPor(UPDATED_ACOMPANHADO_POR)
            .estado(UPDATED_ESTADO)
            .statusAtendimento(UPDATED_STATUS_ATENDIMENTO)
            .cadastradoPor(UPDATED_CADASTRADO_POR)
            .observacao(UPDATED_OBSERVACAO);

        restVisitantesMockMvc.perform(put("/api/visitantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisitantes)))
            .andExpect(status().isOk());

        // Validate the Visitantes in the database
        List<Visitantes> visitantesList = visitantesRepository.findAll();
        assertThat(visitantesList).hasSize(databaseSizeBeforeUpdate);
        Visitantes testVisitantes = visitantesList.get(visitantesList.size() - 1);
        assertThat(testVisitantes.getDataVisita()).isEqualTo(UPDATED_DATA_VISITA);
        assertThat(testVisitantes.getNomeVisitante()).isEqualTo(UPDATED_NOME_VISITANTE);
        assertThat(testVisitantes.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testVisitantes.getAcompanhadoPor()).isEqualTo(UPDATED_ACOMPANHADO_POR);
        assertThat(testVisitantes.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testVisitantes.getStatusAtendimento()).isEqualTo(UPDATED_STATUS_ATENDIMENTO);
        assertThat(testVisitantes.getCadastradoPor()).isEqualTo(UPDATED_CADASTRADO_POR);
        assertThat(testVisitantes.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingVisitantes() throws Exception {
        int databaseSizeBeforeUpdate = visitantesRepository.findAll().size();

        // Create the Visitantes

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restVisitantesMockMvc.perform(put("/api/visitantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitantes)))
            .andExpect(status().isBadRequest());

        // Validate the Visitantes in the database
        List<Visitantes> visitantesList = visitantesRepository.findAll();
        assertThat(visitantesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVisitantes() throws Exception {
        // Initialize the database
        visitantesRepository.saveAndFlush(visitantes);

        int databaseSizeBeforeDelete = visitantesRepository.findAll().size();

        // Get the visitantes
        restVisitantesMockMvc.perform(delete("/api/visitantes/{id}", visitantes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Visitantes> visitantesList = visitantesRepository.findAll();
        assertThat(visitantesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visitantes.class);
        Visitantes visitantes1 = new Visitantes();
        visitantes1.setId(1L);
        Visitantes visitantes2 = new Visitantes();
        visitantes2.setId(visitantes1.getId());
        assertThat(visitantes1).isEqualTo(visitantes2);
        visitantes2.setId(2L);
        assertThat(visitantes1).isNotEqualTo(visitantes2);
        visitantes1.setId(null);
        assertThat(visitantes1).isNotEqualTo(visitantes2);
    }
}
