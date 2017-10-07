package com.cus.metime.promosi.web.rest;

import com.cus.metime.promosi.PromosiApp;

import com.cus.metime.promosi.config.SecurityBeanOverrideConfiguration;

import com.cus.metime.promosi.domain.Promo;
import com.cus.metime.promosi.domain.builder.PromoBuilder;
import com.cus.metime.promosi.repository.PromoRepository;
import com.cus.metime.promosi.service.PromoService;
import com.cus.metime.promosi.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cus.metime.promosi.domain.enumeration.PromoCategory;
/**
 * Test class for the PromoResource REST controller.
 *
 * @see PromoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PromosiApp.class, SecurityBeanOverrideConfiguration.class})
public class PromoResourceIntTest {

    private static final String DEFAULT_MEDIA_FILE = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_FILE = "BBBBBBBBBB";

    private static final PromoCategory DEFAULT_PROMO_CATEGORY = PromoCategory.BCA;
    private static final PromoCategory UPDATED_PROMO_CATEGORY = PromoCategory.CIMB;

    @Autowired
    private PromoRepository promoRepository;

    @Autowired
    private PromoService promoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPromoMockMvc;

    private Promo promo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
       /* final PromoResource promoResource = new PromoResource(promoService);
        this.restPromoMockMvc = MockMvcBuilders.standaloneSetup(promoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();*/
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Promo createEntity(EntityManager em) {
        Promo promo = new PromoBuilder().createPromo()
            .mediaFile(DEFAULT_MEDIA_FILE)
            .promoCategory(DEFAULT_PROMO_CATEGORY);
        return promo;
    }

    @Before
    public void initTest() {
        promo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPromo() throws Exception {
        int databaseSizeBeforeCreate = promoRepository.findAll().size();

        // Create the Promo
        restPromoMockMvc.perform(post("/api/promos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promo)))
            .andExpect(status().isCreated());

        // Validate the Promo in the database
        List<Promo> promoList = promoRepository.findAll();
        assertThat(promoList).hasSize(databaseSizeBeforeCreate + 1);
        Promo testPromo = promoList.get(promoList.size() - 1);
        assertThat(testPromo.getMediaFile()).isEqualTo(DEFAULT_MEDIA_FILE);
        assertThat(testPromo.getPromoCategory()).isEqualTo(DEFAULT_PROMO_CATEGORY);
    }

    @Test
    @Transactional
    public void createPromoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = promoRepository.findAll().size();

        // Create the Promo with an existing ID
        promo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPromoMockMvc.perform(post("/api/promos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promo)))
            .andExpect(status().isBadRequest());

        // Validate the Promo in the database
        List<Promo> promoList = promoRepository.findAll();
        assertThat(promoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPromos() throws Exception {
        // Initialize the database
        promoRepository.saveAndFlush(promo);

        // Get all the promoList
        restPromoMockMvc.perform(get("/api/promos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(promo.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaFile").value(hasItem(DEFAULT_MEDIA_FILE.toString())))
            .andExpect(jsonPath("$.[*].promoCategory").value(hasItem(DEFAULT_PROMO_CATEGORY.toString())));
    }

    @Test
    @Transactional
    public void getPromo() throws Exception {
        // Initialize the database
        promoRepository.saveAndFlush(promo);

        // Get the promo
        restPromoMockMvc.perform(get("/api/promos/{id}", promo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(promo.getId().intValue()))
            .andExpect(jsonPath("$.mediaFile").value(DEFAULT_MEDIA_FILE.toString()))
            .andExpect(jsonPath("$.promoCategory").value(DEFAULT_PROMO_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPromo() throws Exception {
        // Get the promo
        restPromoMockMvc.perform(get("/api/promos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromo() throws Exception {
        // Initialize the database
        //promoService.save(promo);

        int databaseSizeBeforeUpdate = promoRepository.findAll().size();

        // Update the promo
        Promo updatedPromo = promoRepository.findOne(promo.getId());
        updatedPromo
            .mediaFile(UPDATED_MEDIA_FILE)
            .promoCategory(UPDATED_PROMO_CATEGORY);

        restPromoMockMvc.perform(put("/api/promos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPromo)))
            .andExpect(status().isOk());

        // Validate the Promo in the database
        List<Promo> promoList = promoRepository.findAll();
        assertThat(promoList).hasSize(databaseSizeBeforeUpdate);
        Promo testPromo = promoList.get(promoList.size() - 1);
        assertThat(testPromo.getMediaFile()).isEqualTo(UPDATED_MEDIA_FILE);
        assertThat(testPromo.getPromoCategory()).isEqualTo(UPDATED_PROMO_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingPromo() throws Exception {
        int databaseSizeBeforeUpdate = promoRepository.findAll().size();

        // Create the Promo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPromoMockMvc.perform(put("/api/promos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promo)))
            .andExpect(status().isCreated());

        // Validate the Promo in the database
        List<Promo> promoList = promoRepository.findAll();
        assertThat(promoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePromo() throws Exception {
        // Initialize the database
        //promoService.save(promo);

        int databaseSizeBeforeDelete = promoRepository.findAll().size();

        // Get the promo
        restPromoMockMvc.perform(delete("/api/promos/{id}", promo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Promo> promoList = promoRepository.findAll();
        assertThat(promoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Promo.class);
        Promo promo1 = new PromoBuilder().createPromo();
        promo1.setId(1L);
        Promo promo2 = new PromoBuilder().createPromo();
        promo2.setId(promo1.getId());
        assertThat(promo1).isEqualTo(promo2);
        promo2.setId(2L);
        assertThat(promo1).isNotEqualTo(promo2);
        promo1.setId(null);
        assertThat(promo1).isNotEqualTo(promo2);
    }
}
