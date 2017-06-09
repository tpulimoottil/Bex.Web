package com.borderexchange.web.web.rest;

import com.borderexchange.web.BexWebApp;

import com.borderexchange.web.domain.ShipmentAddress;
import com.borderexchange.web.repository.ShipmentAddressRepository;
import com.borderexchange.web.service.ShipmentAddressService;
import com.borderexchange.web.service.dto.ShipmentAddressDTO;
import com.borderexchange.web.service.mapper.ShipmentAddressMapper;
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
 * Test class for the ShipmentAddressResource REST controller.
 *
 * @see ShipmentAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BexWebApp.class)
public class ShipmentAddressResourceIntTest {

    private static final String DEFAULT_ATTENTION_TO = "AAAAAAAAAA";
    private static final String UPDATED_ATTENTION_TO = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LANDMARK = "AAAAAAAAAA";
    private static final String UPDATED_LANDMARK = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    @Autowired
    private ShipmentAddressRepository shipmentAddressRepository;

    @Autowired
    private ShipmentAddressMapper shipmentAddressMapper;

    @Autowired
    private ShipmentAddressService shipmentAddressService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShipmentAddressMockMvc;

    private ShipmentAddress shipmentAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentAddressResource shipmentAddressResource = new ShipmentAddressResource(shipmentAddressService);
        this.restShipmentAddressMockMvc = MockMvcBuilders.standaloneSetup(shipmentAddressResource)
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
    public static ShipmentAddress createEntity(EntityManager em) {
        ShipmentAddress shipmentAddress = new ShipmentAddress()
            .attentionTo(DEFAULT_ATTENTION_TO)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .landmark(DEFAULT_LANDMARK)
            .street(DEFAULT_STREET)
            .postalCode(DEFAULT_POSTAL_CODE);
        return shipmentAddress;
    }

    @Before
    public void initTest() {
        shipmentAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentAddress() throws Exception {
        int databaseSizeBeforeCreate = shipmentAddressRepository.findAll().size();

        // Create the ShipmentAddress
        ShipmentAddressDTO shipmentAddressDTO = shipmentAddressMapper.toDto(shipmentAddress);
        restShipmentAddressMockMvc.perform(post("/api/shipment-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentAddress in the database
        List<ShipmentAddress> shipmentAddressList = shipmentAddressRepository.findAll();
        assertThat(shipmentAddressList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentAddress testShipmentAddress = shipmentAddressList.get(shipmentAddressList.size() - 1);
        assertThat(testShipmentAddress.getAttentionTo()).isEqualTo(DEFAULT_ATTENTION_TO);
        assertThat(testShipmentAddress.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testShipmentAddress.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testShipmentAddress.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
        assertThat(testShipmentAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testShipmentAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void createShipmentAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentAddressRepository.findAll().size();

        // Create the ShipmentAddress with an existing ID
        shipmentAddress.setId(1L);
        ShipmentAddressDTO shipmentAddressDTO = shipmentAddressMapper.toDto(shipmentAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentAddressMockMvc.perform(post("/api/shipment-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShipmentAddress> shipmentAddressList = shipmentAddressRepository.findAll();
        assertThat(shipmentAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShipmentAddresses() throws Exception {
        // Initialize the database
        shipmentAddressRepository.saveAndFlush(shipmentAddress);

        // Get all the shipmentAddressList
        restShipmentAddressMockMvc.perform(get("/api/shipment-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].attentionTo").value(hasItem(DEFAULT_ATTENTION_TO.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())));
    }

    @Test
    @Transactional
    public void getShipmentAddress() throws Exception {
        // Initialize the database
        shipmentAddressRepository.saveAndFlush(shipmentAddress);

        // Get the shipmentAddress
        restShipmentAddressMockMvc.perform(get("/api/shipment-addresses/{id}", shipmentAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentAddress.getId().intValue()))
            .andExpect(jsonPath("$.attentionTo").value(DEFAULT_ATTENTION_TO.toString()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1.toString()))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2.toString()))
            .andExpect(jsonPath("$.landmark").value(DEFAULT_LANDMARK.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentAddress() throws Exception {
        // Get the shipmentAddress
        restShipmentAddressMockMvc.perform(get("/api/shipment-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentAddress() throws Exception {
        // Initialize the database
        shipmentAddressRepository.saveAndFlush(shipmentAddress);
        int databaseSizeBeforeUpdate = shipmentAddressRepository.findAll().size();

        // Update the shipmentAddress
        ShipmentAddress updatedShipmentAddress = shipmentAddressRepository.findOne(shipmentAddress.getId());
        updatedShipmentAddress
            .attentionTo(UPDATED_ATTENTION_TO)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .landmark(UPDATED_LANDMARK)
            .street(UPDATED_STREET)
            .postalCode(UPDATED_POSTAL_CODE);
        ShipmentAddressDTO shipmentAddressDTO = shipmentAddressMapper.toDto(updatedShipmentAddress);

        restShipmentAddressMockMvc.perform(put("/api/shipment-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentAddressDTO)))
            .andExpect(status().isOk());

        // Validate the ShipmentAddress in the database
        List<ShipmentAddress> shipmentAddressList = shipmentAddressRepository.findAll();
        assertThat(shipmentAddressList).hasSize(databaseSizeBeforeUpdate);
        ShipmentAddress testShipmentAddress = shipmentAddressList.get(shipmentAddressList.size() - 1);
        assertThat(testShipmentAddress.getAttentionTo()).isEqualTo(UPDATED_ATTENTION_TO);
        assertThat(testShipmentAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testShipmentAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testShipmentAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testShipmentAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testShipmentAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentAddress() throws Exception {
        int databaseSizeBeforeUpdate = shipmentAddressRepository.findAll().size();

        // Create the ShipmentAddress
        ShipmentAddressDTO shipmentAddressDTO = shipmentAddressMapper.toDto(shipmentAddress);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShipmentAddressMockMvc.perform(put("/api/shipment-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentAddress in the database
        List<ShipmentAddress> shipmentAddressList = shipmentAddressRepository.findAll();
        assertThat(shipmentAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShipmentAddress() throws Exception {
        // Initialize the database
        shipmentAddressRepository.saveAndFlush(shipmentAddress);
        int databaseSizeBeforeDelete = shipmentAddressRepository.findAll().size();

        // Get the shipmentAddress
        restShipmentAddressMockMvc.perform(delete("/api/shipment-addresses/{id}", shipmentAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentAddress> shipmentAddressList = shipmentAddressRepository.findAll();
        assertThat(shipmentAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentAddress.class);
        ShipmentAddress shipmentAddress1 = new ShipmentAddress();
        shipmentAddress1.setId(1L);
        ShipmentAddress shipmentAddress2 = new ShipmentAddress();
        shipmentAddress2.setId(shipmentAddress1.getId());
        assertThat(shipmentAddress1).isEqualTo(shipmentAddress2);
        shipmentAddress2.setId(2L);
        assertThat(shipmentAddress1).isNotEqualTo(shipmentAddress2);
        shipmentAddress1.setId(null);
        assertThat(shipmentAddress1).isNotEqualTo(shipmentAddress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentAddressDTO.class);
        ShipmentAddressDTO shipmentAddressDTO1 = new ShipmentAddressDTO();
        shipmentAddressDTO1.setId(1L);
        ShipmentAddressDTO shipmentAddressDTO2 = new ShipmentAddressDTO();
        assertThat(shipmentAddressDTO1).isNotEqualTo(shipmentAddressDTO2);
        shipmentAddressDTO2.setId(shipmentAddressDTO1.getId());
        assertThat(shipmentAddressDTO1).isEqualTo(shipmentAddressDTO2);
        shipmentAddressDTO2.setId(2L);
        assertThat(shipmentAddressDTO1).isNotEqualTo(shipmentAddressDTO2);
        shipmentAddressDTO1.setId(null);
        assertThat(shipmentAddressDTO1).isNotEqualTo(shipmentAddressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipmentAddressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipmentAddressMapper.fromId(null)).isNull();
    }
}
