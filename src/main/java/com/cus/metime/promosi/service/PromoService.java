package com.cus.metime.promosi.service;

import com.cus.metime.promosi.domain.Promo;
import java.util.List;

/**
 * Service Interface for managing Promo.
 */
public interface PromoService {

    /**
     * Save a promo.
     *
     * @param promo the entity to save
     * @return the persisted entity
     */
    Promo save(Promo promo);

    /**
     *  Get all the promos.
     *
     *  @return the list of entities
     */
    List<Promo> findAll();

    /**
     *  Get the "id" promo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Promo findOne(Long id);

    /**
     *  Delete the "id" promo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
