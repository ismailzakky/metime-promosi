package com.cus.metime.promosi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cus.metime.promosi.domain.Promo;
import com.cus.metime.promosi.service.PromoService;
import com.cus.metime.promosi.web.rest.util.HeaderUtil;
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
 * REST controller for managing Promo.
 */
@RestController
@RequestMapping("/api")
public class PromoResource {

    private final Logger log = LoggerFactory.getLogger(PromoResource.class);

    private static final String ENTITY_NAME = "promo";

    private final PromoService promoService;

    public PromoResource(PromoService promoService) {
        this.promoService = promoService;
    }

    /**
     * POST  /promos : Create a new promo.
     *
     * @param promo the promo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new promo, or with status 400 (Bad Request) if the promo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/promos")
    @Timed
    public ResponseEntity<Promo> createPromo(@RequestBody Promo promo) throws URISyntaxException {
        log.debug("REST request to save Promo : {}", promo);
        if (promo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new promo cannot already have an ID")).body(null);
        }
        Promo result = promoService.save(promo);
        return ResponseEntity.created(new URI("/api/promos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /promos : Updates an existing promo.
     *
     * @param promo the promo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated promo,
     * or with status 400 (Bad Request) if the promo is not valid,
     * or with status 500 (Internal Server Error) if the promo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/promos")
    @Timed
    public ResponseEntity<Promo> updatePromo(@RequestBody Promo promo) throws URISyntaxException {
        log.debug("REST request to update Promo : {}", promo);
        if (promo.getId() == null) {
            return createPromo(promo);
        }
        Promo result = promoService.save(promo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, promo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /promos : get all the promos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of promos in body
     */
    @GetMapping("/promos")
    @Timed
    public List<Promo> getAllPromos() {
        log.debug("REST request to get all Promos");
        return promoService.findAll();
        }

    /**
     * GET  /promos/:id : get the "id" promo.
     *
     * @param id the id of the promo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the promo, or with status 404 (Not Found)
     */
    @GetMapping("/promos/{id}")
    @Timed
    public ResponseEntity<Promo> getPromo(@PathVariable Long id) {
        log.debug("REST request to get Promo : {}", id);
        Promo promo = promoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(promo));
    }

    /**
     * DELETE  /promos/:id : delete the "id" promo.
     *
     * @param id the id of the promo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/promos/{id}")
    @Timed
    public ResponseEntity<Void> deletePromo(@PathVariable Long id) {
        log.debug("REST request to delete Promo : {}", id);
        promoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
