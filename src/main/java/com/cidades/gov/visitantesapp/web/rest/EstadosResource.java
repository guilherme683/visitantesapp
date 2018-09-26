package com.cidades.gov.visitantesapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cidades.gov.visitantesapp.domain.Estados;
import com.cidades.gov.visitantesapp.repository.EstadosRepository;
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
 * REST controller for managing Estados.
 */
@RestController
@RequestMapping("/api")
public class EstadosResource {

    private final Logger log = LoggerFactory.getLogger(EstadosResource.class);

    private static final String ENTITY_NAME = "estados";

    private final EstadosRepository estadosRepository;

    public EstadosResource(EstadosRepository estadosRepository) {
        this.estadosRepository = estadosRepository;
    }

    /**
     * POST  /estados : Create a new estados.
     *
     * @param estados the estados to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estados, or with status 400 (Bad Request) if the estados has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estados")
    @Timed
    public ResponseEntity<Estados> createEstados(@RequestBody Estados estados) throws URISyntaxException {
        log.debug("REST request to save Estados : {}", estados);
        if (estados.getId() != null) {
            throw new BadRequestAlertException("A new estados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Estados result = estadosRepository.save(estados);
        return ResponseEntity.created(new URI("/api/estados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estados : Updates an existing estados.
     *
     * @param estados the estados to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estados,
     * or with status 400 (Bad Request) if the estados is not valid,
     * or with status 500 (Internal Server Error) if the estados couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estados")
    @Timed
    public ResponseEntity<Estados> updateEstados(@RequestBody Estados estados) throws URISyntaxException {
        log.debug("REST request to update Estados : {}", estados);
        if (estados.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Estados result = estadosRepository.save(estados);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estados.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estados : get all the estados.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of estados in body
     */
    @GetMapping("/estados")
    @Timed
    public List<Estados> getAllEstados() {
        log.debug("REST request to get all Estados");
        return estadosRepository.findAll();
    }

    /**
     * GET  /estados/:id : get the "id" estados.
     *
     * @param id the id of the estados to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estados, or with status 404 (Not Found)
     */
    @GetMapping("/estados/{id}")
    @Timed
    public ResponseEntity<Estados> getEstados(@PathVariable Long id) {
        log.debug("REST request to get Estados : {}", id);
        Optional<Estados> estados = estadosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estados);
    }

    /**
     * DELETE  /estados/:id : delete the "id" estados.
     *
     * @param id the id of the estados to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estados/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstados(@PathVariable Long id) {
        log.debug("REST request to delete Estados : {}", id);

        estadosRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
