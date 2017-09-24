package com.cus.metime.promosi.repository;

import com.cus.metime.promosi.domain.Promo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Promo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PromoRepository extends JpaRepository<Promo, Long> {

}
