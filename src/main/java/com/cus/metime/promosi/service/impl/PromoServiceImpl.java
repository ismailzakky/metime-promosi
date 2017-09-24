package com.cus.metime.promosi.service.impl;

import com.cus.metime.promosi.service.PromoService;
import com.cus.metime.promosi.domain.Promo;
import com.cus.metime.promosi.repository.PromoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Promo.
 */
@Service
@Transactional
public class PromoServiceImpl implements PromoService{

    private final Logger log = LoggerFactory.getLogger(PromoServiceImpl.class);

    private final PromoRepository promoRepository;

    public PromoServiceImpl(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }

    /**
     * Save a promo.
     *
     * @param promo the entity to save
     * @return the persisted entity
     */
    @Override
    public Promo save(Promo promo) {
        log.debug("Request to save Promo : {}", promo);
        return promoRepository.save(promo);
    }

    /**
     *  Get all the promos.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Promo> findAll() {
        log.debug("Request to get all Promos");
        return promoRepository.findAll();
    }

    /**
     *  Get one promo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Promo findOne(Long id) {
        log.debug("Request to get Promo : {}", id);
        return promoRepository.findOne(id);
    }

    /**
     *  Delete the  promo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Promo : {}", id);
        promoRepository.delete(id);
    }
}
