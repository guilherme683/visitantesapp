package com.cidades.gov.visitantesapp.web.rest;

import com.cidades.gov.visitantesapp.VisitantesappApp;

import com.cidades.gov.visitantesapp.domain.Municipios;
import com.cidades.gov.visitantesapp.repository.MunicipiosRepository;
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
 * Test class for the MunicipiosResource REST controller.
 *
 * @see MunicipiosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisitantesappApp.class)
public class MunicipiosResourceIntTest {

    private static final Long DEFAULT_COD_MUNICIPIOS_IBGE = 1L;
    private static final Long UPDATED_COD_MUNICIPIOS_IBGE = 2L;

    private static final String DEFAULT_NOME_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MUNICIPIO = "BBBBBBBBBB";

    private static final Long DEFAULT_COD_ESTADOS_IBGE = 1L;
    private static final Long UPDATED_COD_ESTADOS_IBGE = 2L;

    @Autowired
    private MunicipiosRepository municipiosRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMunicipiosMockMvc;

    private Municipios municipios;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MunicipiosResource municipiosResource = new MunicipiosResource(municipiosRepository);
        this.restMunicipiosMockMvc = MockMvcBuilders.standaloneSetup(municipiosResource)
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
    public static Municipios createEntity(EntityManager em) {
        Municipios municipios = new Municipios()
            .codMunicipiosIbge(DEFAULT_COD_MUNICIPIOS_IBGE)
            .nomeMunicipio(DEFAULT_NOME_MUNICIPIO)
            .codEstadosIbge(DEFAULT_COD_ESTADOS_IBGE);
        return municipios;
    }

    @Before
    public void initTest() {
        municipios = createEntity(em);
    }

    @Test
    @Transactional
    public void createMunicipios() throws Exception {
        int databaseSizeBeforeCreate = municipiosRepository.findAll().size();

        // Create the Municipios
        restMunicipiosMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipios)))
            .andExpect(status().isCreated());

        // Validate the Municipios in the database
        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeCreate + 1);
        Municipios testMunicipios = municipiosList.get(municipiosList.size() - 1);
        assertThat(testMunicipios.getCodMunicipiosIbge()).isEqualTo(DEFAULT_COD_MUNICIPIOS_IBGE);
        assertThat(testMunicipios.getNomeMunicipio()).isEqualTo(DEFAULT_NOME_MUNICIPIO);
        assertThat(testMunicipios.getCodEstadosIbge()).isEqualTo(DEFAULT_COD_ESTADOS_IBGE);
    }

    @Test
    @Transactional
    public void createMunicipiosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = municipiosRepository.findAll().size();

        // Create the Municipios with an existing ID
        municipios.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMunicipiosMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipios)))
            .andExpect(status().isBadRequest());

        // Validate the Municipios in the database
        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMunicipios() throws Exception {
        // Initialize the database
        municipiosRepository.saveAndFlush(municipios);

        // Get all the municipiosList
        restMunicipiosMockMvc.perform(get("/api/municipios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipios.getId().intValue())))
            .andExpect(jsonPath("$.[*].codMunicipiosIbge").value(hasItem(DEFAULT_COD_MUNICIPIOS_IBGE.intValue())))
            .andExpect(jsonPath("$.[*].nomeMunicipio").value(hasItem(DEFAULT_NOME_MUNICIPIO.toString())))
            .andExpect(jsonPath("$.[*].codEstadosIbge").value(hasItem(DEFAULT_COD_ESTADOS_IBGE.intValue())));
    }
    

    @Test
    @Transactional
    public void getMunicipios() throws Exception {
        // Initialize the database
        municipiosRepository.saveAndFlush(municipios);

        // Get the municipios
        restMunicipiosMockMvc.perform(get("/api/municipios/{id}", municipios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(municipios.getId().intValue()))
            .andExpect(jsonPath("$.codMunicipiosIbge").value(DEFAULT_COD_MUNICIPIOS_IBGE.intValue()))
            .andExpect(jsonPath("$.nomeMunicipio").value(DEFAULT_NOME_MUNICIPIO.toString()))
            .andExpect(jsonPath("$.codEstadosIbge").value(DEFAULT_COD_ESTADOS_IBGE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMunicipios() throws Exception {
        // Get the municipios
        restMunicipiosMockMvc.perform(get("/api/municipios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMunicipios() throws Exception {
        // Initialize the database
        municipiosRepository.saveAndFlush(municipios);

        int databaseSizeBeforeUpdate = municipiosRepository.findAll().size();

        // Update the municipios
        Municipios updatedMunicipios = municipiosRepository.findById(municipios.getId()).get();
        // Disconnect from session so that the updates on updatedMunicipios are not directly saved in db
        em.detach(updatedMunicipios);
        updatedMunicipios
            .codMunicipiosIbge(UPDATED_COD_MUNICIPIOS_IBGE)
            .nomeMunicipio(UPDATED_NOME_MUNICIPIO)
            .codEstadosIbge(UPDATED_COD_ESTADOS_IBGE);

        restMunicipiosMockMvc.perform(put("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMunicipios)))
            .andExpect(status().isOk());

        // Validate the Municipios in the database
        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeUpdate);
        Municipios testMunicipios = municipiosList.get(municipiosList.size() - 1);
        assertThat(testMunicipios.getCodMunicipiosIbge()).isEqualTo(UPDATED_COD_MUNICIPIOS_IBGE);
        assertThat(testMunicipios.getNomeMunicipio()).isEqualTo(UPDATED_NOME_MUNICIPIO);
        assertThat(testMunicipios.getCodEstadosIbge()).isEqualTo(UPDATED_COD_ESTADOS_IBGE);
    }

    @Test
    @Transactional
    public void updateNonExistingMunicipios() throws Exception {
        int databaseSizeBeforeUpdate = municipiosRepository.findAll().size();

        // Create the Municipios

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restMunicipiosMockMvc.perform(put("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipios)))
            .andExpect(status().isBadRequest());

        // Validate the Municipios in the database
        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMunicipios() throws Exception {
        // Initialize the database
        municipiosRepository.saveAndFlush(municipios);

        int databaseSizeBeforeDelete = municipiosRepository.findAll().size();

        // Get the municipios
        restMunicipiosMockMvc.perform(delete("/api/municipios/{id}", municipios.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Municipios.class);
        Municipios municipios1 = new Municipios();
        municipios1.setId(1L);
        Municipios municipios2 = new Municipios();
        municipios2.setId(municipios1.getId());
        assertThat(municipios1).isEqualTo(municipios2);
        municipios2.setId(2L);
        assertThat(municipios1).isNotEqualTo(municipios2);
        municipios1.setId(null);
        assertThat(municipios1).isNotEqualTo(municipios2);
    }
}
