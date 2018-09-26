package com.cidades.gov.visitantesapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cidades.gov.visitantesapp.domain.Partido;
import com.cidades.gov.visitantesapp.repository.PartidoRepository;
import com.cidades.gov.visitantesapp.web.rest.errors.BadRequestAlertException;
import com.cidades.gov.visitantesapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Partido.
 */
@RestController
@RequestMapping("/api")
public class PartidoResource {

    private final Logger log = LoggerFactory.getLogger(PartidoResource.class);

    private static final String ENTITY_NAME = "partido";

    private final PartidoRepository partidoRepository;

    public PartidoResource(PartidoRepository partidoRepository) {
        this.partidoRepository = partidoRepository;
    }

    /**
     * POST  /partidos : Create a new partido.
     *
     * @param partido the partido to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partido, or with status 400 (Bad Request) if the partido has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/partidos")
    @Timed
    public ResponseEntity<Partido> createPartido(@RequestBody Partido partido) throws URISyntaxException {
        log.debug("REST request to save Partido : {}", partido);
        if (partido.getId() != null) {
            throw new BadRequestAlertException("A new partido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Partido result = partidoRepository.save(partido);
        return ResponseEntity.created(new URI("/api/partidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partidos : Updates an existing partido.
     *
     * @param partido the partido to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partido,
     * or with status 400 (Bad Request) if the partido is not valid,
     * or with status 500 (Internal Server Error) if the partido couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/partidos")
    @Timed
    public ResponseEntity<Partido> updatePartido(@RequestBody Partido partido) throws URISyntaxException {
        log.debug("REST request to update Partido : {}", partido);
        if (partido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Partido result = partidoRepository.save(partido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partido.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partidos : get all the partidos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partidos in body
     */
    @GetMapping("/partidos")
    @Timed
    public List<Partido> getAllPartidos() {
        log.debug("REST request to get all Partidos");
        return partidoRepository.findAll();
    }

    /**
     * GET  /partidos/:id : get the "id" partido.
     *
     * @param id the id of the partido to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partido, or with status 404 (Not Found)
     */
    @GetMapping("/partidos/{id}")
    @Timed
    public ResponseEntity<Partido> getPartido(@PathVariable Long id) {
        log.debug("REST request to get Partido : {}", id);
        Optional<Partido> partido = partidoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partido);
    }

    /**
     * DELETE  /partidos/:id : delete the "id" partido.
     *
     * @param id the id of the partido to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/partidos/{id}")
    @Timed
    public ResponseEntity<Void> deletePartido(@PathVariable Long id) {
        log.debug("REST request to delete Partido : {}", id);

        partidoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
