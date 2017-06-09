package com.borderexchange.web.web.rest;

import com.borderexchange.web.BexWebApp;

import com.borderexchange.web.domain.UserAddress;
import com.borderexchange.web.repository.UserAddressRepository;
import com.borderexchange.web.service.UserAddressService;
import com.borderexchange.web.service.dto.UserAddressDTO;
import com.borderexchange.web.service.mapper.UserAddressMapper;
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
 * Test class for the UserAddressResource REST controller.
 *
 * @see UserAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BexWebApp.class)
public class UserAddressResourceIntTest {

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
    private UserAddressRepository userAddressRepository;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserAddressMockMvc;

    private UserAddress userAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserAddressResource userAddressResource = new UserAddressResource(userAddressService);
        this.restUserAddressMockMvc = MockMvcBuilders.standaloneSetup(userAddressResource)
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
    public static UserAddress createEntity(EntityManager em) {
        UserAddress userAddress = new UserAddress()
            .attentionTo(DEFAULT_ATTENTION_TO)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .landmark(DEFAULT_LANDMARK)
            .street(DEFAULT_STREET)
            .postalCode(DEFAULT_POSTAL_CODE);
        return userAddress;
    }

    @Before
    public void initTest() {
        userAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAddress() throws Exception {
        int databaseSizeBeforeCreate = userAddressRepository.findAll().size();

        // Create the UserAddress
        UserAddressDTO userAddressDTO = userAddressMapper.toDto(userAddress);
        restUserAddressMockMvc.perform(post("/api/user-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAddress in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeCreate + 1);
        UserAddress testUserAddress = userAddressList.get(userAddressList.size() - 1);
        assertThat(testUserAddress.getAttentionTo()).isEqualTo(DEFAULT_ATTENTION_TO);
        assertThat(testUserAddress.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testUserAddress.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testUserAddress.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
        assertThat(testUserAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testUserAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void createUserAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAddressRepository.findAll().size();

        // Create the UserAddress with an existing ID
        userAddress.setId(1L);
        UserAddressDTO userAddressDTO = userAddressMapper.toDto(userAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAddressMockMvc.perform(post("/api/user-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserAddresses() throws Exception {
        // Initialize the database
        userAddressRepository.saveAndFlush(userAddress);

        // Get all the userAddressList
        restUserAddressMockMvc.perform(get("/api/user-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].attentionTo").value(hasItem(DEFAULT_ATTENTION_TO.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())));
    }

    @Test
    @Transactional
    public void getUserAddress() throws Exception {
        // Initialize the database
        userAddressRepository.saveAndFlush(userAddress);

        // Get the userAddress
        restUserAddressMockMvc.perform(get("/api/user-addresses/{id}", userAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAddress.getId().intValue()))
            .andExpect(jsonPath("$.attentionTo").value(DEFAULT_ATTENTION_TO.toString()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1.toString()))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2.toString()))
            .andExpect(jsonPath("$.landmark").value(DEFAULT_LANDMARK.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAddress() throws Exception {
        // Get the userAddress
        restUserAddressMockMvc.perform(get("/api/user-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAddress() throws Exception {
        // Initialize the database
        userAddressRepository.saveAndFlush(userAddress);
        int databaseSizeBeforeUpdate = userAddressRepository.findAll().size();

        // Update the userAddress
        UserAddress updatedUserAddress = userAddressRepository.findOne(userAddress.getId());
        updatedUserAddress
            .attentionTo(UPDATED_ATTENTION_TO)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .landmark(UPDATED_LANDMARK)
            .street(UPDATED_STREET)
            .postalCode(UPDATED_POSTAL_CODE);
        UserAddressDTO userAddressDTO = userAddressMapper.toDto(updatedUserAddress);

        restUserAddressMockMvc.perform(put("/api/user-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddressDTO)))
            .andExpect(status().isOk());

        // Validate the UserAddress in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeUpdate);
        UserAddress testUserAddress = userAddressList.get(userAddressList.size() - 1);
        assertThat(testUserAddress.getAttentionTo()).isEqualTo(UPDATED_ATTENTION_TO);
        assertThat(testUserAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testUserAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testUserAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testUserAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testUserAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAddress() throws Exception {
        int databaseSizeBeforeUpdate = userAddressRepository.findAll().size();

        // Create the UserAddress
        UserAddressDTO userAddressDTO = userAddressMapper.toDto(userAddress);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserAddressMockMvc.perform(put("/api/user-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAddress in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserAddress() throws Exception {
        // Initialize the database
        userAddressRepository.saveAndFlush(userAddress);
        int databaseSizeBeforeDelete = userAddressRepository.findAll().size();

        // Get the userAddress
        restUserAddressMockMvc.perform(delete("/api/user-addresses/{id}", userAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAddress.class);
        UserAddress userAddress1 = new UserAddress();
        userAddress1.setId(1L);
        UserAddress userAddress2 = new UserAddress();
        userAddress2.setId(userAddress1.getId());
        assertThat(userAddress1).isEqualTo(userAddress2);
        userAddress2.setId(2L);
        assertThat(userAddress1).isNotEqualTo(userAddress2);
        userAddress1.setId(null);
        assertThat(userAddress1).isNotEqualTo(userAddress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAddressDTO.class);
        UserAddressDTO userAddressDTO1 = new UserAddressDTO();
        userAddressDTO1.setId(1L);
        UserAddressDTO userAddressDTO2 = new UserAddressDTO();
        assertThat(userAddressDTO1).isNotEqualTo(userAddressDTO2);
        userAddressDTO2.setId(userAddressDTO1.getId());
        assertThat(userAddressDTO1).isEqualTo(userAddressDTO2);
        userAddressDTO2.setId(2L);
        assertThat(userAddressDTO1).isNotEqualTo(userAddressDTO2);
        userAddressDTO1.setId(null);
        assertThat(userAddressDTO1).isNotEqualTo(userAddressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userAddressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userAddressMapper.fromId(null)).isNull();
    }
}
