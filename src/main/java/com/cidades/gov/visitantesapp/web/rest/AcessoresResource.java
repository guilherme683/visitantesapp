package com.cidades.gov.visitantesapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cidades.gov.visitantesapp.domain.Acessores;
import com.cidades.gov.visitantesapp.repository.AcessoresRepository;
import com.cidades.gov.visitantesapp.web.rest.errors.BadRequestAlertException;
import com.cidades.gov.visitantesapp.web.rest.util.HeaderUtil;
import com.cidades.gov.visitantesapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Acessores.
 */
@RestController
@RequestMapping("/api")
public class AcessoresResource {

    private final Logger log = LoggerFactory.getLogger(AcessoresResource.class);

    private static final String ENTITY_NAME = "acessores";

    private final AcessoresRepository acessoresRepository;

    public AcessoresResource(AcessoresRepository acessoresRepository) {
        this.acessoresRepository = acessoresRepository;
    }

    /**
     * POST  /acessores : Create a new acessores.
     *
     * @param acessores the acessores to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acessores, or with status 400 (Bad Request) if the acessores has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acessores")
    @Timed
    public ResponseEntity<Acessores> createAcessores(@RequestBody Acessores acessores) throws URISyntaxException {
        log.debug("REST request to save Acessores : {}", acessores);
        if (acessores.getId() != null) {
            throw new BadRequestAlertException("A new acessores cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Acessores result = acessoresRepository.save(acessores);
        return ResponseEntity.created(new URI("/api/acessores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acessores : Updates an existing acessores.
     *
     * @param acessores the acessores to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acessores,
     * or with status 400 (Bad Request) if the acessores is not valid,
     * or with status 500 (Internal Server Error) if the acessores couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acessores")
    @Timed
    public ResponseEntity<Acessores> updateAcessores(@RequestBody Acessores acessores) throws URISyntaxException {
        log.debug("REST request to update Acessores : {}", acessores);
        if (acessores.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Acessores result = acessoresRepository.save(acessores);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acessores.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acessores : get all the acessores.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of acessores in body
     */
    @GetMapping("/acessores")
    @Timed
    public ResponseEntity<List<Acessores>> getAllAcessores(Pageable pageable) {
        log.debug("REST request to get a page of Acessores");
        Page<Acessores> page = acessoresRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/acessores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /acessores/:id : get the "id" acessores.
     *
     * @param id the id of the acessores to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acessores, or with status 404 (Not Found)
     */
    @GetMapping("/acessores/{id}")
    @Timed
    public ResponseEntity<Acessores> getAcessores(@PathVariable Long id) {
        log.debug("REST request to get Acessores : {}", id);
        Optional<Acessores> acessores = acessoresRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(acessores);
    }

    /**
     * DELETE  /acessores/:id : delete the "id" acessores.
     *
     * @param id the id of the acessores to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acessores/{id}")
    @Timed
    public ResponseEntity<Void> deleteAcessores(@PathVariable Long id) {
        log.debug("REST request to delete Acessores : {}", id);

        acessoresRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
