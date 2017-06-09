package com.borderexchange.web.web.rest;

import com.borderexchange.web.BexWebApp;

import com.borderexchange.web.domain.ShipmentOrder;
import com.borderexchange.web.repository.ShipmentOrderRepository;
import com.borderexchange.web.service.ShipmentOrderService;
import com.borderexchange.web.service.dto.ShipmentOrderDTO;
import com.borderexchange.web.service.mapper.ShipmentOrderMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.borderexchange.web.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.borderexchange.web.domain.enumeration.ShipmentOrderStatus;
/**
 * Test class for the ShipmentOrderResource REST controller.
 *
 * @see ShipmentOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BexWebApp.class)
public class ShipmentOrderResourceIntTest {

    private static final ShipmentOrderStatus DEFAULT_STATUS = ShipmentOrderStatus.ITEM_RECEIVED;
    private static final ShipmentOrderStatus UPDATED_STATUS = ShipmentOrderStatus.PACKED;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    @Autowired
    private ShipmentOrderRepository shipmentOrderRepository;

    @Autowired
    private ShipmentOrderMapper shipmentOrderMapper;

    @Autowired
    private ShipmentOrderService shipmentOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShipmentOrderMockMvc;

    private ShipmentOrder shipmentOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentOrderResource shipmentOrderResource = new ShipmentOrderResource(shipmentOrderService);
        this.restShipmentOrderMockMvc = MockMvcBuilders.standaloneSetup(shipmentOrderResource)
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
    public static ShipmentOrder createEntity(EntityManager em) {
        ShipmentOrder shipmentOrder = new ShipmentOrder()
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .amount(DEFAULT_AMOUNT);
        return shipmentOrder;
    }

    @Before
    public void initTest() {
        shipmentOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentOrder() throws Exception {
        int databaseSizeBeforeCreate = shipmentOrderRepository.findAll().size();

        // Create the ShipmentOrder
        ShipmentOrderDTO shipmentOrderDTO = shipmentOrderMapper.toDto(shipmentOrder);
        restShipmentOrderMockMvc.perform(post("/api/shipment-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentOrder in the database
        List<ShipmentOrder> shipmentOrderList = shipmentOrderRepository.findAll();
        assertThat(shipmentOrderList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentOrder testShipmentOrder = shipmentOrderList.get(shipmentOrderList.size() - 1);
        assertThat(testShipmentOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testShipmentOrder.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testShipmentOrder.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createShipmentOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentOrderRepository.findAll().size();

        // Create the ShipmentOrder with an existing ID
        shipmentOrder.setId(1L);
        ShipmentOrderDTO shipmentOrderDTO = shipmentOrderMapper.toDto(shipmentOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentOrderMockMvc.perform(post("/api/shipment-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShipmentOrder> shipmentOrderList = shipmentOrderRepository.findAll();
        assertThat(shipmentOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShipmentOrders() throws Exception {
        // Initialize the database
        shipmentOrderRepository.saveAndFlush(shipmentOrder);

        // Get all the shipmentOrderList
        restShipmentOrderMockMvc.perform(get("/api/shipment-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getShipmentOrder() throws Exception {
        // Initialize the database
        shipmentOrderRepository.saveAndFlush(shipmentOrder);

        // Get the shipmentOrder
        restShipmentOrderMockMvc.perform(get("/api/shipment-orders/{id}", shipmentOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentOrder.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentOrder() throws Exception {
        // Get the shipmentOrder
        restShipmentOrderMockMvc.perform(get("/api/shipment-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentOrder() throws Exception {
        // Initialize the database
        shipmentOrderRepository.saveAndFlush(shipmentOrder);
        int databaseSizeBeforeUpdate = shipmentOrderRepository.findAll().size();

        // Update the shipmentOrder
        ShipmentOrder updatedShipmentOrder = shipmentOrderRepository.findOne(shipmentOrder.getId());
        updatedShipmentOrder
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .amount(UPDATED_AMOUNT);
        ShipmentOrderDTO shipmentOrderDTO = shipmentOrderMapper.toDto(updatedShipmentOrder);

        restShipmentOrderMockMvc.perform(put("/api/shipment-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentOrderDTO)))
            .andExpect(status().isOk());

        // Validate the ShipmentOrder in the database
        List<ShipmentOrder> shipmentOrderList = shipmentOrderRepository.findAll();
        assertThat(shipmentOrderList).hasSize(databaseSizeBeforeUpdate);
        ShipmentOrder testShipmentOrder = shipmentOrderList.get(shipmentOrderList.size() - 1);
        assertThat(testShipmentOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShipmentOrder.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testShipmentOrder.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentOrder() throws Exception {
        int databaseSizeBeforeUpdate = shipmentOrderRepository.findAll().size();

        // Create the ShipmentOrder
        ShipmentOrderDTO shipmentOrderDTO = shipmentOrderMapper.toDto(shipmentOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShipmentOrderMockMvc.perform(put("/api/shipment-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentOrder in the database
        List<ShipmentOrder> shipmentOrderList = shipmentOrderRepository.findAll();
        assertThat(shipmentOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShipmentOrder() throws Exception {
        // Initialize the database
        shipmentOrderRepository.saveAndFlush(shipmentOrder);
        int databaseSizeBeforeDelete = shipmentOrderRepository.findAll().size();

        // Get the shipmentOrder
        restShipmentOrderMockMvc.perform(delete("/api/shipment-orders/{id}", shipmentOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentOrder> shipmentOrderList = shipmentOrderRepository.findAll();
        assertThat(shipmentOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentOrder.class);
        ShipmentOrder shipmentOrder1 = new ShipmentOrder();
        shipmentOrder1.setId(1L);
        ShipmentOrder shipmentOrder2 = new ShipmentOrder();
        shipmentOrder2.setId(shipmentOrder1.getId());
        assertThat(shipmentOrder1).isEqualTo(shipmentOrder2);
        shipmentOrder2.setId(2L);
        assertThat(shipmentOrder1).isNotEqualTo(shipmentOrder2);
        shipmentOrder1.setId(null);
        assertThat(shipmentOrder1).isNotEqualTo(shipmentOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentOrderDTO.class);
        ShipmentOrderDTO shipmentOrderDTO1 = new ShipmentOrderDTO();
        shipmentOrderDTO1.setId(1L);
        ShipmentOrderDTO shipmentOrderDTO2 = new ShipmentOrderDTO();
        assertThat(shipmentOrderDTO1).isNotEqualTo(shipmentOrderDTO2);
        shipmentOrderDTO2.setId(shipmentOrderDTO1.getId());
        assertThat(shipmentOrderDTO1).isEqualTo(shipmentOrderDTO2);
        shipmentOrderDTO2.setId(2L);
        assertThat(shipmentOrderDTO1).isNotEqualTo(shipmentOrderDTO2);
        shipmentOrderDTO1.setId(null);
        assertThat(shipmentOrderDTO1).isNotEqualTo(shipmentOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipmentOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipmentOrderMapper.fromId(null)).isNull();
    }
}
