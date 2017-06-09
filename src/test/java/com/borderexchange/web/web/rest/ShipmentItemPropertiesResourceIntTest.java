package com.borderexchange.web.web.rest;

import com.borderexchange.web.BexWebApp;

import com.borderexchange.web.domain.ShipmentItemProperties;
import com.borderexchange.web.repository.ShipmentItemPropertiesRepository;
import com.borderexchange.web.service.ShipmentItemPropertiesService;
import com.borderexchange.web.service.dto.ShipmentItemPropertiesDTO;
import com.borderexchange.web.service.mapper.ShipmentItemPropertiesMapper;
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
 * Test class for the ShipmentItemPropertiesResource REST controller.
 *
 * @see ShipmentItemPropertiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BexWebApp.class)
public class ShipmentItemPropertiesResourceIntTest {

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;

    private static final Double DEFAULT_LENGTH = 1D;
    private static final Double UPDATED_LENGTH = 2D;

    private static final Double DEFAULT_WIDTH = 1D;
    private static final Double UPDATED_WIDTH = 2D;

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    @Autowired
    private ShipmentItemPropertiesRepository shipmentItemPropertiesRepository;

    @Autowired
    private ShipmentItemPropertiesMapper shipmentItemPropertiesMapper;

    @Autowired
    private ShipmentItemPropertiesService shipmentItemPropertiesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShipmentItemPropertiesMockMvc;

    private ShipmentItemProperties shipmentItemProperties;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentItemPropertiesResource shipmentItemPropertiesResource = new ShipmentItemPropertiesResource(shipmentItemPropertiesService);
        this.restShipmentItemPropertiesMockMvc = MockMvcBuilders.standaloneSetup(shipmentItemPropertiesResource)
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
    public static ShipmentItemProperties createEntity(EntityManager em) {
        ShipmentItemProperties shipmentItemProperties = new ShipmentItemProperties()
            .height(DEFAULT_HEIGHT)
            .length(DEFAULT_LENGTH)
            .width(DEFAULT_WIDTH)
            .weight(DEFAULT_WEIGHT);
        return shipmentItemProperties;
    }

    @Before
    public void initTest() {
        shipmentItemProperties = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentItemProperties() throws Exception {
        int databaseSizeBeforeCreate = shipmentItemPropertiesRepository.findAll().size();

        // Create the ShipmentItemProperties
        ShipmentItemPropertiesDTO shipmentItemPropertiesDTO = shipmentItemPropertiesMapper.toDto(shipmentItemProperties);
        restShipmentItemPropertiesMockMvc.perform(post("/api/shipment-item-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemPropertiesDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentItemProperties in the database
        List<ShipmentItemProperties> shipmentItemPropertiesList = shipmentItemPropertiesRepository.findAll();
        assertThat(shipmentItemPropertiesList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentItemProperties testShipmentItemProperties = shipmentItemPropertiesList.get(shipmentItemPropertiesList.size() - 1);
        assertThat(testShipmentItemProperties.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testShipmentItemProperties.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testShipmentItemProperties.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testShipmentItemProperties.getWeight()).isEqualTo(DEFAULT_WEIGHT);
    }

    @Test
    @Transactional
    public void createShipmentItemPropertiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentItemPropertiesRepository.findAll().size();

        // Create the ShipmentItemProperties with an existing ID
        shipmentItemProperties.setId(1L);
        ShipmentItemPropertiesDTO shipmentItemPropertiesDTO = shipmentItemPropertiesMapper.toDto(shipmentItemProperties);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentItemPropertiesMockMvc.perform(post("/api/shipment-item-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemPropertiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShipmentItemProperties> shipmentItemPropertiesList = shipmentItemPropertiesRepository.findAll();
        assertThat(shipmentItemPropertiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShipmentItemProperties() throws Exception {
        // Initialize the database
        shipmentItemPropertiesRepository.saveAndFlush(shipmentItemProperties);

        // Get all the shipmentItemPropertiesList
        restShipmentItemPropertiesMockMvc.perform(get("/api/shipment-item-properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentItemProperties.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())));
    }

    @Test
    @Transactional
    public void getShipmentItemProperties() throws Exception {
        // Initialize the database
        shipmentItemPropertiesRepository.saveAndFlush(shipmentItemProperties);

        // Get the shipmentItemProperties
        restShipmentItemPropertiesMockMvc.perform(get("/api/shipment-item-properties/{id}", shipmentItemProperties.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentItemProperties.getId().intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentItemProperties() throws Exception {
        // Get the shipmentItemProperties
        restShipmentItemPropertiesMockMvc.perform(get("/api/shipment-item-properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentItemProperties() throws Exception {
        // Initialize the database
        shipmentItemPropertiesRepository.saveAndFlush(shipmentItemProperties);
        int databaseSizeBeforeUpdate = shipmentItemPropertiesRepository.findAll().size();

        // Update the shipmentItemProperties
        ShipmentItemProperties updatedShipmentItemProperties = shipmentItemPropertiesRepository.findOne(shipmentItemProperties.getId());
        updatedShipmentItemProperties
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .weight(UPDATED_WEIGHT);
        ShipmentItemPropertiesDTO shipmentItemPropertiesDTO = shipmentItemPropertiesMapper.toDto(updatedShipmentItemProperties);

        restShipmentItemPropertiesMockMvc.perform(put("/api/shipment-item-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemPropertiesDTO)))
            .andExpect(status().isOk());

        // Validate the ShipmentItemProperties in the database
        List<ShipmentItemProperties> shipmentItemPropertiesList = shipmentItemPropertiesRepository.findAll();
        assertThat(shipmentItemPropertiesList).hasSize(databaseSizeBeforeUpdate);
        ShipmentItemProperties testShipmentItemProperties = shipmentItemPropertiesList.get(shipmentItemPropertiesList.size() - 1);
        assertThat(testShipmentItemProperties.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testShipmentItemProperties.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testShipmentItemProperties.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testShipmentItemProperties.getWeight()).isEqualTo(UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentItemProperties() throws Exception {
        int databaseSizeBeforeUpdate = shipmentItemPropertiesRepository.findAll().size();

        // Create the ShipmentItemProperties
        ShipmentItemPropertiesDTO shipmentItemPropertiesDTO = shipmentItemPropertiesMapper.toDto(shipmentItemProperties);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShipmentItemPropertiesMockMvc.perform(put("/api/shipment-item-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemPropertiesDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentItemProperties in the database
        List<ShipmentItemProperties> shipmentItemPropertiesList = shipmentItemPropertiesRepository.findAll();
        assertThat(shipmentItemPropertiesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShipmentItemProperties() throws Exception {
        // Initialize the database
        shipmentItemPropertiesRepository.saveAndFlush(shipmentItemProperties);
        int databaseSizeBeforeDelete = shipmentItemPropertiesRepository.findAll().size();

        // Get the shipmentItemProperties
        restShipmentItemPropertiesMockMvc.perform(delete("/api/shipment-item-properties/{id}", shipmentItemProperties.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentItemProperties> shipmentItemPropertiesList = shipmentItemPropertiesRepository.findAll();
        assertThat(shipmentItemPropertiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentItemProperties.class);
        ShipmentItemProperties shipmentItemProperties1 = new ShipmentItemProperties();
        shipmentItemProperties1.setId(1L);
        ShipmentItemProperties shipmentItemProperties2 = new ShipmentItemProperties();
        shipmentItemProperties2.setId(shipmentItemProperties1.getId());
        assertThat(shipmentItemProperties1).isEqualTo(shipmentItemProperties2);
        shipmentItemProperties2.setId(2L);
        assertThat(shipmentItemProperties1).isNotEqualTo(shipmentItemProperties2);
        shipmentItemProperties1.setId(null);
        assertThat(shipmentItemProperties1).isNotEqualTo(shipmentItemProperties2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentItemPropertiesDTO.class);
        ShipmentItemPropertiesDTO shipmentItemPropertiesDTO1 = new ShipmentItemPropertiesDTO();
        shipmentItemPropertiesDTO1.setId(1L);
        ShipmentItemPropertiesDTO shipmentItemPropertiesDTO2 = new ShipmentItemPropertiesDTO();
        assertThat(shipmentItemPropertiesDTO1).isNotEqualTo(shipmentItemPropertiesDTO2);
        shipmentItemPropertiesDTO2.setId(shipmentItemPropertiesDTO1.getId());
        assertThat(shipmentItemPropertiesDTO1).isEqualTo(shipmentItemPropertiesDTO2);
        shipmentItemPropertiesDTO2.setId(2L);
        assertThat(shipmentItemPropertiesDTO1).isNotEqualTo(shipmentItemPropertiesDTO2);
        shipmentItemPropertiesDTO1.setId(null);
        assertThat(shipmentItemPropertiesDTO1).isNotEqualTo(shipmentItemPropertiesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipmentItemPropertiesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipmentItemPropertiesMapper.fromId(null)).isNull();
    }
}
