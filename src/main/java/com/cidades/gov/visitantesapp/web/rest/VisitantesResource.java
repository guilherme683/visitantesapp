package com.cidades.gov.visitantesapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cidades.gov.visitantesapp.domain.Visitantes;
import com.cidades.gov.visitantesapp.repository.VisitantesRepository;
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
 * REST controller for managing Visitantes.
 */
@RestController
@RequestMapping("/api")
public class VisitantesResource {

    private final Logger log = LoggerFactory.getLogger(VisitantesResource.class);

    private static final String ENTITY_NAME = "visitantes";

    private final VisitantesRepository visitantesRepository;

    public VisitantesResource(VisitantesRepository visitantesRepository) {
        this.visitantesRepository = visitantesRepository;
    }

    /**
     * POST  /visitantes : Create a new visitantes.
     *
     * @param visitantes the visitantes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visitantes, or with status 400 (Bad Request) if the visitantes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visitantes")
    @Timed
    public ResponseEntity<Visitantes> createVisitantes(@RequestBody Visitantes visitantes) throws URISyntaxException {
        log.debug("REST request to save Visitantes : {}", visitantes);
        if (visitantes.getId() != null) {
            throw new BadRequestAlertException("A new visitantes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Visitantes result = visitantesRepository.save(visitantes);
        return ResponseEntity.created(new URI("/api/visitantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visitantes : Updates an existing visitantes.
     *
     * @param visitantes the visitantes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visitantes,
     * or with status 400 (Bad Request) if the visitantes is not valid,
     * or with status 500 (Internal Server Error) if the visitantes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visitantes")
    @Timed
    public ResponseEntity<Visitantes> updateVisitantes(@RequestBody Visitantes visitantes) throws URISyntaxException {
        log.debug("REST request to update Visitantes : {}", visitantes);
        if (visitantes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Visitantes result = visitantesRepository.save(visitantes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visitantes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visitantes : get all the visitantes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of visitantes in body
     */
    @GetMapping("/visitantes")
    @Timed
    public ResponseEntity<List<Visitantes>> getAllVisitantes(Pageable pageable) {
        log.debug("REST request to get a page of Visitantes");
        Page<Visitantes> page = visitantesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/visitantes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /visitantes/:id : get the "id" visitantes.
     *
     * @param id the id of the visitantes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visitantes, or with status 404 (Not Found)
     */
    @GetMapping("/visitantes/{id}")
    @Timed
    public ResponseEntity<Visitantes> getVisitantes(@PathVariable Long id) {
        log.debug("REST request to get Visitantes : {}", id);
        Optional<Visitantes> visitantes = visitantesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(visitantes);
    }

    /**
     * DELETE  /visitantes/:id : delete the "id" visitantes.
     *
     * @param id the id of the visitantes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visitantes/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisitantes(@PathVariable Long id) {
        log.debug("REST request to delete Visitantes : {}", id);

        visitantesRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
