package com.cidades.gov.visitantesapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cidades.gov.visitantesapp.domain.Regioes;
import com.cidades.gov.visitantesapp.repository.RegioesRepository;
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
 * REST controller for managing Regioes.
 */
@RestController
@RequestMapping("/api")
public class RegioesResource {

    private final Logger log = LoggerFactory.getLogger(RegioesResource.class);

    private static final String ENTITY_NAME = "regioes";

    private final RegioesRepository regioesRepository;

    public RegioesResource(RegioesRepository regioesRepository) {
        this.regioesRepository = regioesRepository;
    }

    /**
     * POST  /regioes : Create a new regioes.
     *
     * @param regioes the regioes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regioes, or with status 400 (Bad Request) if the regioes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regioes")
    @Timed
    public ResponseEntity<Regioes> createRegioes(@RequestBody Regioes regioes) throws URISyntaxException {
        log.debug("REST request to save Regioes : {}", regioes);
        if (regioes.getId() != null) {
            throw new BadRequestAlertException("A new regioes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Regioes result = regioesRepository.save(regioes);
        return ResponseEntity.created(new URI("/api/regioes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regioes : Updates an existing regioes.
     *
     * @param regioes the regioes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regioes,
     * or with status 400 (Bad Request) if the regioes is not valid,
     * or with status 500 (Internal Server Error) if the regioes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regioes")
    @Timed
    public ResponseEntity<Regioes> updateRegioes(@RequestBody Regioes regioes) throws URISyntaxException {
        log.debug("REST request to update Regioes : {}", regioes);
        if (regioes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Regioes result = regioesRepository.save(regioes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, regioes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regioes : get all the regioes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of regioes in body
     */
    @GetMapping("/regioes")
    @Timed
    public List<Regioes> getAllRegioes() {
        log.debug("REST request to get all Regioes");
        return regioesRepository.findAll();
    }

    /**
     * GET  /regioes/:id : get the "id" regioes.
     *
     * @param id the id of the regioes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regioes, or with status 404 (Not Found)
     */
    @GetMapping("/regioes/{id}")
    @Timed
    public ResponseEntity<Regioes> getRegioes(@PathVariable Long id) {
        log.debug("REST request to get Regioes : {}", id);
        Optional<Regioes> regioes = regioesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(regioes);
    }

    /**
     * DELETE  /regioes/:id : delete the "id" regioes.
     *
     * @param id the id of the regioes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regioes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegioes(@PathVariable Long id) {
        log.debug("REST request to delete Regioes : {}", id);

        regioesRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
