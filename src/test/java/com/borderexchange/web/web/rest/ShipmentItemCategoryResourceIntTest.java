package com.borderexchange.web.web.rest;

import com.borderexchange.web.BexWebApp;

import com.borderexchange.web.domain.ShipmentItemCategory;
import com.borderexchange.web.repository.ShipmentItemCategoryRepository;
import com.borderexchange.web.service.ShipmentItemCategoryService;
import com.borderexchange.web.service.dto.ShipmentItemCategoryDTO;
import com.borderexchange.web.service.mapper.ShipmentItemCategoryMapper;
import com.borderexchange.web.web.rest.errors.ExceptionTranslator;

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

/**
 * Test class for the ShipmentItemCategoryResource REST controller.
 *
 * @see ShipmentItemCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BexWebApp.class)
public class ShipmentItemCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ShipmentItemCategoryRepository shipmentItemCategoryRepository;

    @Autowired
    private ShipmentItemCategoryMapper shipmentItemCategoryMapper;

    @Autowired
    private ShipmentItemCategoryService shipmentItemCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShipmentItemCategoryMockMvc;

    private ShipmentItemCategory shipmentItemCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentItemCategoryResource shipmentItemCategoryResource = new ShipmentItemCategoryResource(shipmentItemCategoryService);
        this.restShipmentItemCategoryMockMvc = MockMvcBuilders.standaloneSetup(shipmentItemCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentItemCategory createEntity(EntityManager em) {
        ShipmentItemCategory shipmentItemCategory = new ShipmentItemCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return shipmentItemCategory;
    }

    @Before
    public void initTest() {
        shipmentItemCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentItemCategory() throws Exception {
        int databaseSizeBeforeCreate = shipmentItemCategoryRepository.findAll().size();

        // Create the ShipmentItemCategory
        ShipmentItemCategoryDTO shipmentItemCategoryDTO = shipmentItemCategoryMapper.toDto(shipmentItemCategory);
        restShipmentItemCategoryMockMvc.perform(post("/api/shipment-item-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentItemCategory in the database
        List<ShipmentItemCategory> shipmentItemCategoryList = shipmentItemCategoryRepository.findAll();
        assertThat(shipmentItemCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentItemCategory testShipmentItemCategory = shipmentItemCategoryList.get(shipmentItemCategoryList.size() - 1);
        assertThat(testShipmentItemCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShipmentItemCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createShipmentItemCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentItemCategoryRepository.findAll().size();

        // Create the ShipmentItemCategory with an existing ID
        shipmentItemCategory.setId(1L);
        ShipmentItemCategoryDTO shipmentItemCategoryDTO = shipmentItemCategoryMapper.toDto(shipmentItemCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentItemCategoryMockMvc.perform(post("/api/shipment-item-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShipmentItemCategory> shipmentItemCategoryList = shipmentItemCategoryRepository.findAll();
        assertThat(shipmentItemCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShipmentItemCategories() throws Exception {
        // Initialize the database
        shipmentItemCategoryRepository.saveAndFlush(shipmentItemCategory);

        // Get all the shipmentItemCategoryList
        restShipmentItemCategoryMockMvc.perform(get("/api/shipment-item-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentItemCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getShipmentItemCategory() throws Exception {
        // Initialize the database
        shipmentItemCategoryRepository.saveAndFlush(shipmentItemCategory);

        // Get the shipmentItemCategory
        restShipmentItemCategoryMockMvc.perform(get("/api/shipment-item-categories/{id}", shipmentItemCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentItemCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentItemCategory() throws Exception {
        // Get the shipmentItemCategory
        restShipmentItemCategoryMockMvc.perform(get("/api/shipment-item-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentItemCategory() throws Exception {
        // Initialize the database
        shipmentItemCategoryRepository.saveAndFlush(shipmentItemCategory);
        int databaseSizeBeforeUpdate = shipmentItemCategoryRepository.findAll().size();

        // Update the shipmentItemCategory
        ShipmentItemCategory updatedShipmentItemCategory = shipmentItemCategoryRepository.findOne(shipmentItemCategory.getId());
        updatedShipmentItemCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ShipmentItemCategoryDTO shipmentItemCategoryDTO = shipmentItemCategoryMapper.toDto(updatedShipmentItemCategory);

        restShipmentItemCategoryMockMvc.perform(put("/api/shipment-item-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ShipmentItemCategory in the database
        List<ShipmentItemCategory> shipmentItemCategoryList = shipmentItemCategoryRepository.findAll();
        assertThat(shipmentItemCategoryList).hasSize(databaseSizeBeforeUpdate);
        ShipmentItemCategory testShipmentItemCategory = shipmentItemCategoryList.get(shipmentItemCategoryList.size() - 1);
        assertThat(testShipmentItemCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipmentItemCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentItemCategory() throws Exception {
        int databaseSizeBeforeUpdate = shipmentItemCategoryRepository.findAll().size();

        // Create the ShipmentItemCategory
        ShipmentItemCategoryDTO shipmentItemCategoryDTO = shipmentItemCategoryMapper.toDto(shipmentItemCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShipmentItemCategoryMockMvc.perform(put("/api/shipment-item-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentItemCategory in the database
        List<ShipmentItemCategory> shipmentItemCategoryList = shipmentItemCategoryRepository.findAll();
        assertThat(shipmentItemCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShipmentItemCategory() throws Exception {
        // Initialize the database
        shipmentItemCategoryRepository.saveAndFlush(shipmentItemCategory);
        int databaseSizeBeforeDelete = shipmentItemCategoryRepository.findAll().size();

        // Get the shipmentItemCategory
        restShipmentItemCategoryMockMvc.perform(delete("/api/shipment-item-categories/{id}", shipmentItemCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentItemCategory> shipmentItemCategoryList = shipmentItemCategoryRepository.findAll();
        assertThat(shipmentItemCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentItemCategory.class);
        ShipmentItemCategory shipmentItemCategory1 = new ShipmentItemCategory();
        shipmentItemCategory1.setId(1L);
        ShipmentItemCategory shipmentItemCategory2 = new ShipmentItemCategory();
        shipmentItemCategory2.setId(shipmentItemCategory1.getId());
        assertThat(shipmentItemCategory1).isEqualTo(shipmentItemCategory2);
        shipmentItemCategory2.setId(2L);
        assertThat(shipmentItemCategory1).isNotEqualTo(shipmentItemCategory2);
        shipmentItemCategory1.setId(null);
        assertThat(shipmentItemCategory1).isNotEqualTo(shipmentItemCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentItemCategoryDTO.class);
        ShipmentItemCategoryDTO shipmentItemCategoryDTO1 = new ShipmentItemCategoryDTO();
        shipmentItemCategoryDTO1.setId(1L);
        ShipmentItemCategoryDTO shipmentItemCategoryDTO2 = new ShipmentItemCategoryDTO();
        assertThat(shipmentItemCategoryDTO1).isNotEqualTo(shipmentItemCategoryDTO2);
        shipmentItemCategoryDTO2.setId(shipmentItemCategoryDTO1.getId());
        assertThat(shipmentItemCategoryDTO1).isEqualTo(shipmentItemCategoryDTO2);
        shipmentItemCategoryDTO2.setId(2L);
        assertThat(shipmentItemCategoryDTO1).isNotEqualTo(shipmentItemCategoryDTO2);
        shipmentItemCategoryDTO1.setId(null);
        assertThat(shipmentItemCategoryDTO1).isNotEqualTo(shipmentItemCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipmentItemCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipmentItemCategoryMapper.fromId(null)).isNull();
    }
}
