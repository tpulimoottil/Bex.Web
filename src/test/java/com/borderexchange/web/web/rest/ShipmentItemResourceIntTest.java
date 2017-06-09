package com.borderexchange.web.web.rest;

import com.borderexchange.web.BexWebApp;

import com.borderexchange.web.domain.ShipmentItem;
import com.borderexchange.web.repository.ShipmentItemRepository;
import com.borderexchange.web.service.ShipmentItemService;
import com.borderexchange.web.service.dto.ShipmentItemDTO;
import com.borderexchange.web.service.mapper.ShipmentItemMapper;
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

import com.borderexchange.web.domain.enumeration.ShipmentItemStatus;
/**
 * Test class for the ShipmentItemResource REST controller.
 *
 * @see ShipmentItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BexWebApp.class)
public class ShipmentItemResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final ZonedDateTime DEFAULT_FREE_STORAGE_ENDING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FREE_STORAGE_ENDING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ShipmentItemStatus DEFAULT_STATUS = ShipmentItemStatus.ITEM_RECEIVED;
    private static final ShipmentItemStatus UPDATED_STATUS = ShipmentItemStatus.PACKED;

    @Autowired
    private ShipmentItemRepository shipmentItemRepository;

    @Autowired
    private ShipmentItemMapper shipmentItemMapper;

    @Autowired
    private ShipmentItemService shipmentItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShipmentItemMockMvc;

    private ShipmentItem shipmentItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentItemResource shipmentItemResource = new ShipmentItemResource(shipmentItemService);
        this.restShipmentItemMockMvc = MockMvcBuilders.standaloneSetup(shipmentItemResource)
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
    public static ShipmentItem createEntity(EntityManager em) {
        ShipmentItem shipmentItem = new ShipmentItem()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .longDescription(DEFAULT_LONG_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .freeStorageEndingDate(DEFAULT_FREE_STORAGE_ENDING_DATE)
            .createdOn(DEFAULT_CREATED_ON)
            .status(DEFAULT_STATUS);
        return shipmentItem;
    }

    @Before
    public void initTest() {
        shipmentItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentItem() throws Exception {
        int databaseSizeBeforeCreate = shipmentItemRepository.findAll().size();

        // Create the ShipmentItem
        ShipmentItemDTO shipmentItemDTO = shipmentItemMapper.toDto(shipmentItem);
        restShipmentItemMockMvc.perform(post("/api/shipment-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentItem in the database
        List<ShipmentItem> shipmentItemList = shipmentItemRepository.findAll();
        assertThat(shipmentItemList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentItem testShipmentItem = shipmentItemList.get(shipmentItemList.size() - 1);
        assertThat(testShipmentItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShipmentItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testShipmentItem.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testShipmentItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testShipmentItem.getFreeStorageEndingDate()).isEqualTo(DEFAULT_FREE_STORAGE_ENDING_DATE);
        assertThat(testShipmentItem.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testShipmentItem.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createShipmentItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentItemRepository.findAll().size();

        // Create the ShipmentItem with an existing ID
        shipmentItem.setId(1L);
        ShipmentItemDTO shipmentItemDTO = shipmentItemMapper.toDto(shipmentItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentItemMockMvc.perform(post("/api/shipment-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShipmentItem> shipmentItemList = shipmentItemRepository.findAll();
        assertThat(shipmentItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShipmentItems() throws Exception {
        // Initialize the database
        shipmentItemRepository.saveAndFlush(shipmentItem);

        // Get all the shipmentItemList
        restShipmentItemMockMvc.perform(get("/api/shipment-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].freeStorageEndingDate").value(hasItem(sameInstant(DEFAULT_FREE_STORAGE_ENDING_DATE))))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getShipmentItem() throws Exception {
        // Initialize the database
        shipmentItemRepository.saveAndFlush(shipmentItem);

        // Get the shipmentItem
        restShipmentItemMockMvc.perform(get("/api/shipment-items/{id}", shipmentItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.freeStorageEndingDate").value(sameInstant(DEFAULT_FREE_STORAGE_ENDING_DATE)))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentItem() throws Exception {
        // Get the shipmentItem
        restShipmentItemMockMvc.perform(get("/api/shipment-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentItem() throws Exception {
        // Initialize the database
        shipmentItemRepository.saveAndFlush(shipmentItem);
        int databaseSizeBeforeUpdate = shipmentItemRepository.findAll().size();

        // Update the shipmentItem
        ShipmentItem updatedShipmentItem = shipmentItemRepository.findOne(shipmentItem.getId());
        updatedShipmentItem
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .price(UPDATED_PRICE)
            .freeStorageEndingDate(UPDATED_FREE_STORAGE_ENDING_DATE)
            .createdOn(UPDATED_CREATED_ON)
            .status(UPDATED_STATUS);
        ShipmentItemDTO shipmentItemDTO = shipmentItemMapper.toDto(updatedShipmentItem);

        restShipmentItemMockMvc.perform(put("/api/shipment-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemDTO)))
            .andExpect(status().isOk());

        // Validate the ShipmentItem in the database
        List<ShipmentItem> shipmentItemList = shipmentItemRepository.findAll();
        assertThat(shipmentItemList).hasSize(databaseSizeBeforeUpdate);
        ShipmentItem testShipmentItem = shipmentItemList.get(shipmentItemList.size() - 1);
        assertThat(testShipmentItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipmentItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testShipmentItem.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testShipmentItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testShipmentItem.getFreeStorageEndingDate()).isEqualTo(UPDATED_FREE_STORAGE_ENDING_DATE);
        assertThat(testShipmentItem.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testShipmentItem.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentItem() throws Exception {
        int databaseSizeBeforeUpdate = shipmentItemRepository.findAll().size();

        // Create the ShipmentItem
        ShipmentItemDTO shipmentItemDTO = shipmentItemMapper.toDto(shipmentItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShipmentItemMockMvc.perform(put("/api/shipment-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentItemDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentItem in the database
        List<ShipmentItem> shipmentItemList = shipmentItemRepository.findAll();
        assertThat(shipmentItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShipmentItem() throws Exception {
        // Initialize the database
        shipmentItemRepository.saveAndFlush(shipmentItem);
        int databaseSizeBeforeDelete = shipmentItemRepository.findAll().size();

        // Get the shipmentItem
        restShipmentItemMockMvc.perform(delete("/api/shipment-items/{id}", shipmentItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentItem> shipmentItemList = shipmentItemRepository.findAll();
        assertThat(shipmentItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentItem.class);
        ShipmentItem shipmentItem1 = new ShipmentItem();
        shipmentItem1.setId(1L);
        ShipmentItem shipmentItem2 = new ShipmentItem();
        shipmentItem2.setId(shipmentItem1.getId());
        assertThat(shipmentItem1).isEqualTo(shipmentItem2);
        shipmentItem2.setId(2L);
        assertThat(shipmentItem1).isNotEqualTo(shipmentItem2);
        shipmentItem1.setId(null);
        assertThat(shipmentItem1).isNotEqualTo(shipmentItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentItemDTO.class);
        ShipmentItemDTO shipmentItemDTO1 = new ShipmentItemDTO();
        shipmentItemDTO1.setId(1L);
        ShipmentItemDTO shipmentItemDTO2 = new ShipmentItemDTO();
        assertThat(shipmentItemDTO1).isNotEqualTo(shipmentItemDTO2);
        shipmentItemDTO2.setId(shipmentItemDTO1.getId());
        assertThat(shipmentItemDTO1).isEqualTo(shipmentItemDTO2);
        shipmentItemDTO2.setId(2L);
        assertThat(shipmentItemDTO1).isNotEqualTo(shipmentItemDTO2);
        shipmentItemDTO1.setId(null);
        assertThat(shipmentItemDTO1).isNotEqualTo(shipmentItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipmentItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipmentItemMapper.fromId(null)).isNull();
    }
}
