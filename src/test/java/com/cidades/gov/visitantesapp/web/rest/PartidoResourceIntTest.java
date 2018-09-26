package com.cidades.gov.visitantesapp.web.rest;

import com.cidades.gov.visitantesapp.VisitantesappApp;

import com.cidades.gov.visitantesapp.domain.Partido;
import com.cidades.gov.visitantesapp.repository.PartidoRepository;
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
 * Test class for the PartidoResource REST controller.
 *
 * @see PartidoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisitantesappApp.class)
public class PartidoResourceIntTest {

    private static final String DEFAULT_NOME_PARTIDO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PARTIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA_PARTIDO = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA_PARTIDO = "BBBBBBBBBB";

    @Autowired
    private PartidoRepository partidoRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartidoMockMvc;

    private Partido partido;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartidoResource partidoResource = new PartidoResource(partidoRepository);
        this.restPartidoMockMvc = MockMvcBuilders.standaloneSetup(partidoResource)
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
    public static Partido createEntity(EntityManager em) {
        Partido partido = new Partido()
            .nomePartido(DEFAULT_NOME_PARTIDO)
            .siglaPartido(DEFAULT_SIGLA_PARTIDO);
        return partido;
    }

    @Before
    public void initTest() {
        partido = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartido() throws Exception {
        int databaseSizeBeforeCreate = partidoRepository.findAll().size();

        // Create the Partido
        restPartidoMockMvc.perform(post("/api/partidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partido)))
            .andExpect(status().isCreated());

        // Validate the Partido in the database
        List<Partido> partidoList = partidoRepository.findAll();
        assertThat(partidoList).hasSize(databaseSizeBeforeCreate + 1);
        Partido testPartido = partidoList.get(partidoList.size() - 1);
        assertThat(testPartido.getNomePartido()).isEqualTo(DEFAULT_NOME_PARTIDO);
        assertThat(testPartido.getSiglaPartido()).isEqualTo(DEFAULT_SIGLA_PARTIDO);
    }

    @Test
    @Transactional
    public void createPartidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partidoRepository.findAll().size();

        // Create the Partido with an existing ID
        partido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartidoMockMvc.perform(post("/api/partidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partido)))
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        List<Partido> partidoList = partidoRepository.findAll();
        assertThat(partidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPartidos() throws Exception {
        // Initialize the database
        partidoRepository.saveAndFlush(partido);

        // Get all the partidoList
        restPartidoMockMvc.perform(get("/api/partidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partido.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomePartido").value(hasItem(DEFAULT_NOME_PARTIDO.toString())))
            .andExpect(jsonPath("$.[*].siglaPartido").value(hasItem(DEFAULT_SIGLA_PARTIDO.toString())));
    }
    

    @Test
    @Transactional
    public void getPartido() throws Exception {
        // Initialize the database
        partidoRepository.saveAndFlush(partido);

        // Get the partido
        restPartidoMockMvc.perform(get("/api/partidos/{id}", partido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partido.getId().intValue()))
            .andExpect(jsonPath("$.nomePartido").value(DEFAULT_NOME_PARTIDO.toString()))
            .andExpect(jsonPath("$.siglaPartido").value(DEFAULT_SIGLA_PARTIDO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPartido() throws Exception {
        // Get the partido
        restPartidoMockMvc.perform(get("/api/partidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartido() throws Exception {
        // Initialize the database
        partidoRepository.saveAndFlush(partido);

        int databaseSizeBeforeUpdate = partidoRepository.findAll().size();

        // Update the partido
        Partido updatedPartido = partidoRepository.findById(partido.getId()).get();
        // Disconnect from session so that the updates on updatedPartido are not directly saved in db
        em.detach(updatedPartido);
        updatedPartido
            .nomePartido(UPDATED_NOME_PARTIDO)
            .siglaPartido(UPDATED_SIGLA_PARTIDO);

        restPartidoMockMvc.perform(put("/api/partidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartido)))
            .andExpect(status().isOk());

        // Validate the Partido in the database
        List<Partido> partidoList = partidoRepository.findAll();
        assertThat(partidoList).hasSize(databaseSizeBeforeUpdate);
        Partido testPartido = partidoList.get(partidoList.size() - 1);
        assertThat(testPartido.getNomePartido()).isEqualTo(UPDATED_NOME_PARTIDO);
        assertThat(testPartido.getSiglaPartido()).isEqualTo(UPDATED_SIGLA_PARTIDO);
    }

    @Test
    @Transactional
    public void updateNonExistingPartido() throws Exception {
        int databaseSizeBeforeUpdate = partidoRepository.findAll().size();

        // Create the Partido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restPartidoMockMvc.perform(put("/api/partidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partido)))
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        List<Partido> partidoList = partidoRepository.findAll();
        assertThat(partidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartido() throws Exception {
        // Initialize the database
        partidoRepository.saveAndFlush(partido);

        int databaseSizeBeforeDelete = partidoRepository.findAll().size();

        // Get the partido
        restPartidoMockMvc.perform(delete("/api/partidos/{id}", partido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Partido> partidoList = partidoRepository.findAll();
        assertThat(partidoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partido.class);
        Partido partido1 = new Partido();
        partido1.setId(1L);
        Partido partido2 = new Partido();
        partido2.setId(partido1.getId());
        assertThat(partido1).isEqualTo(partido2);
        partido2.setId(2L);
        assertThat(partido1).isNotEqualTo(partido2);
        partido1.setId(null);
        assertThat(partido1).isNotEqualTo(partido2);
    }
}
