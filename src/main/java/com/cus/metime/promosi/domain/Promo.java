package com.cus.metime.promosi.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.cus.metime.promosi.domain.enumeration.PromoCategory;

/**
 * A Promo.
 */
@Entity
@Table(name = "promo")
public class Promo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "media_file")
    private String mediaFile;

    @Enumerated(EnumType.STRING)
    @Column(name = "promo_category")
    private PromoCategory promoCategory;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediaFile() {
        return mediaFile;
    }

    public Promo mediaFile(String mediaFile) {
        this.mediaFile = mediaFile;
        return this;
    }

    public void setMediaFile(String mediaFile) {
        this.mediaFile = mediaFile;
    }

    public PromoCategory getPromoCategory() {
        return promoCategory;
    }

    public Promo promoCategory(PromoCategory promoCategory) {
        this.promoCategory = promoCategory;
        return this;
    }

    public void setPromoCategory(PromoCategory promoCategory) {
        this.promoCategory = promoCategory;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Promo promo = (Promo) o;
        if (promo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), promo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Promo{" +
            "id=" + getId() +
            ", mediaFile='" + getMediaFile() + "'" +
            ", promoCategory='" + getPromoCategory() + "'" +
            "}";
    }
}
