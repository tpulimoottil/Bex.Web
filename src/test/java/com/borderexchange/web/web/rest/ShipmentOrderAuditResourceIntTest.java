package com.borderexchange.web.web.rest;

import com.borderexchange.web.BexWebApp;

import com.borderexchange.web.domain.ShipmentOrderAudit;
import com.borderexchange.web.repository.ShipmentOrderAuditRepository;
import com.borderexchange.web.service.ShipmentOrderAuditService;
import com.borderexchange.web.service.dto.ShipmentOrderAuditDTO;
import com.borderexchange.web.service.mapper.ShipmentOrderAuditMapper;
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
 * Test class for the ShipmentOrderAuditResource REST controller.
 *
 * @see ShipmentOrderAuditResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BexWebApp.class)
public class ShipmentOrderAuditResourceIntTest {

    private static final ShipmentOrderStatus DEFAULT_STATUS = ShipmentOrderStatus.ITEM_RECEIVED;
    private static final ShipmentOrderStatus UPDATED_STATUS = ShipmentOrderStatus.PACKED;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_COMMENTS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ShipmentOrderAuditRepository shipmentOrderAuditRepository;

    @Autowired
    private ShipmentOrderAuditMapper shipmentOrderAuditMapper;

    @Autowired
    private ShipmentOrderAuditService shipmentOrderAuditService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShipmentOrderAuditMockMvc;

    private ShipmentOrderAudit shipmentOrderAudit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentOrderAuditResource shipmentOrderAuditResource = new ShipmentOrderAuditResource(shipmentOrderAuditService);
        this.restShipmentOrderAuditMockMvc = MockMvcBuilders.standaloneSetup(shipmentOrderAuditResource)
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
    public static ShipmentOrderAudit createEntity(EntityManager em) {
        ShipmentOrderAudit shipmentOrderAudit = new ShipmentOrderAudit()
            .status(DEFAULT_STATUS)
            .comments(DEFAULT_COMMENTS)
            .additionalComments(DEFAULT_ADDITIONAL_COMMENTS)
            .createdOn(DEFAULT_CREATED_ON);
        return shipmentOrderAudit;
    }

    @Before
    public void initTest() {
        shipmentOrderAudit = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentOrderAudit() throws Exception {
        int databaseSizeBeforeCreate = shipmentOrderAuditRepository.findAll().size();

        // Create the ShipmentOrderAudit
        ShipmentOrderAuditDTO shipmentOrderAuditDTO = shipmentOrderAuditMapper.toDto(shipmentOrderAudit);
        restShipmentOrderAuditMockMvc.perform(post("/api/shipment-order-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentOrderAuditDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentOrderAudit in the database
        List<ShipmentOrderAudit> shipmentOrderAuditList = shipmentOrderAuditRepository.findAll();
        assertThat(shipmentOrderAuditList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentOrderAudit testShipmentOrderAudit = shipmentOrderAuditList.get(shipmentOrderAuditList.size() - 1);
        assertThat(testShipmentOrderAudit.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testShipmentOrderAudit.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testShipmentOrderAudit.getAdditionalComments()).isEqualTo(DEFAULT_ADDITIONAL_COMMENTS);
        assertThat(testShipmentOrderAudit.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createShipmentOrderAuditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentOrderAuditRepository.findAll().size();

        // Create the ShipmentOrderAudit with an existing ID
        shipmentOrderAudit.setId(1L);
        ShipmentOrderAuditDTO shipmentOrderAuditDTO = shipmentOrderAuditMapper.toDto(shipmentOrderAudit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentOrderAuditMockMvc.perform(post("/api/shipment-order-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentOrderAuditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShipmentOrderAudit> shipmentOrderAuditList = shipmentOrderAuditRepository.findAll();
        assertThat(shipmentOrderAuditList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShipmentOrderAudits() throws Exception {
        // Initialize the database
        shipmentOrderAuditRepository.saveAndFlush(shipmentOrderAudit);

        // Get all the shipmentOrderAuditList
        restShipmentOrderAuditMockMvc.perform(get("/api/shipment-order-audits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentOrderAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].additionalComments").value(hasItem(DEFAULT_ADDITIONAL_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))));
    }

    @Test
    @Transactional
    public void getShipmentOrderAudit() throws Exception {
        // Initialize the database
        shipmentOrderAuditRepository.saveAndFlush(shipmentOrderAudit);

        // Get the shipmentOrderAudit
        restShipmentOrderAuditMockMvc.perform(get("/api/shipment-order-audits/{id}", shipmentOrderAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentOrderAudit.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.additionalComments").value(DEFAULT_ADDITIONAL_COMMENTS.toString()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentOrderAudit() throws Exception {
        // Get the shipmentOrderAudit
        restShipmentOrderAuditMockMvc.perform(get("/api/shipment-order-audits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentOrderAudit() throws Exception {
        // Initialize the database
        shipmentOrderAuditRepository.saveAndFlush(shipmentOrderAudit);
        int databaseSizeBeforeUpdate = shipmentOrderAuditRepository.findAll().size();

        // Update the shipmentOrderAudit
        ShipmentOrderAudit updatedShipmentOrderAudit = shipmentOrderAuditRepository.findOne(shipmentOrderAudit.getId());
        updatedShipmentOrderAudit
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS)
            .additionalComments(UPDATED_ADDITIONAL_COMMENTS)
            .createdOn(UPDATED_CREATED_ON);
        ShipmentOrderAuditDTO shipmentOrderAuditDTO = shipmentOrderAuditMapper.toDto(updatedShipmentOrderAudit);

        restShipmentOrderAuditMockMvc.perform(put("/api/shipment-order-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentOrderAuditDTO)))
            .andExpect(status().isOk());

        // Validate the ShipmentOrderAudit in the database
        List<ShipmentOrderAudit> shipmentOrderAuditList = shipmentOrderAuditRepository.findAll();
        assertThat(shipmentOrderAuditList).hasSize(databaseSizeBeforeUpdate);
        ShipmentOrderAudit testShipmentOrderAudit = shipmentOrderAuditList.get(shipmentOrderAuditList.size() - 1);
        assertThat(testShipmentOrderAudit.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShipmentOrderAudit.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testShipmentOrderAudit.getAdditionalComments()).isEqualTo(UPDATED_ADDITIONAL_COMMENTS);
        assertThat(testShipmentOrderAudit.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentOrderAudit() throws Exception {
        int databaseSizeBeforeUpdate = shipmentOrderAuditRepository.findAll().size();

        // Create the ShipmentOrderAudit
        ShipmentOrderAuditDTO shipmentOrderAuditDTO = shipmentOrderAuditMapper.toDto(shipmentOrderAudit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShipmentOrderAuditMockMvc.perform(put("/api/shipment-order-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentOrderAuditDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentOrderAudit in the database
        List<ShipmentOrderAudit> shipmentOrderAuditList = shipmentOrderAuditRepository.findAll();
        assertThat(shipmentOrderAuditList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShipmentOrderAudit() throws Exception {
        // Initialize the database
        shipmentOrderAuditRepository.saveAndFlush(shipmentOrderAudit);
        int databaseSizeBeforeDelete = shipmentOrderAuditRepository.findAll().size();

        // Get the shipmentOrderAudit
        restShipmentOrderAuditMockMvc.perform(delete("/api/shipment-order-audits/{id}", shipmentOrderAudit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentOrderAudit> shipmentOrderAuditList = shipmentOrderAuditRepository.findAll();
        assertThat(shipmentOrderAuditList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentOrderAudit.class);
        ShipmentOrderAudit shipmentOrderAudit1 = new ShipmentOrderAudit();
        shipmentOrderAudit1.setId(1L);
        ShipmentOrderAudit shipmentOrderAudit2 = new ShipmentOrderAudit();
        shipmentOrderAudit2.setId(shipmentOrderAudit1.getId());
        assertThat(shipmentOrderAudit1).isEqualTo(shipmentOrderAudit2);
        shipmentOrderAudit2.setId(2L);
        assertThat(shipmentOrderAudit1).isNotEqualTo(shipmentOrderAudit2);
        shipmentOrderAudit1.setId(null);
        assertThat(shipmentOrderAudit1).isNotEqualTo(shipmentOrderAudit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentOrderAuditDTO.class);
        ShipmentOrderAuditDTO shipmentOrderAuditDTO1 = new ShipmentOrderAuditDTO();
        shipmentOrderAuditDTO1.setId(1L);
        ShipmentOrderAuditDTO shipmentOrderAuditDTO2 = new ShipmentOrderAuditDTO();
        assertThat(shipmentOrderAuditDTO1).isNotEqualTo(shipmentOrderAuditDTO2);
        shipmentOrderAuditDTO2.setId(shipmentOrderAuditDTO1.getId());
        assertThat(shipmentOrderAuditDTO1).isEqualTo(shipmentOrderAuditDTO2);
        shipmentOrderAuditDTO2.setId(2L);
        assertThat(shipmentOrderAuditDTO1).isNotEqualTo(shipmentOrderAuditDTO2);
        shipmentOrderAuditDTO1.setId(null);
        assertThat(shipmentOrderAuditDTO1).isNotEqualTo(shipmentOrderAuditDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipmentOrderAuditMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipmentOrderAuditMapper.fromId(null)).isNull();
    }
}
