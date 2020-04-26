package uz.uzkaznlptools.admin.api.web.rest;

import uz.uzkaznlptools.admin.api.UzKazNlpToolsApp;
import uz.uzkaznlptools.admin.api.domain.Endings;
import uz.uzkaznlptools.admin.api.repository.EndingsRepository;
import uz.uzkaznlptools.admin.api.service.EndingsService;
import uz.uzkaznlptools.admin.api.service.dto.EndingsDTO;
import uz.uzkaznlptools.admin.api.service.mapper.EndingsMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uz.uzkaznlptools.admin.api.domain.enumeration.Language;
/**
 * Integration tests for the {@link EndingsResource} REST controller.
 */
@SpringBootTest(classes = UzKazNlpToolsApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EndingsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_TYPES = 1;
    private static final Integer UPDATED_NUMBER_OF_TYPES = 2;

    private static final Language DEFAULT_LANGUAGE = Language.UZBEK;
    private static final Language UPDATED_LANGUAGE = Language.KAZAKH;

    @Autowired
    private EndingsRepository endingsRepository;

    @Autowired
    private EndingsMapper endingsMapper;

    @Autowired
    private EndingsService endingsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEndingsMockMvc;

    private Endings endings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Endings createEntity(EntityManager em) {
        Endings endings = new Endings()
            .name(DEFAULT_NAME)
            .numberOfTypes(DEFAULT_NUMBER_OF_TYPES)
            .language(DEFAULT_LANGUAGE);
        return endings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Endings createUpdatedEntity(EntityManager em) {
        Endings endings = new Endings()
            .name(UPDATED_NAME)
            .numberOfTypes(UPDATED_NUMBER_OF_TYPES)
            .language(UPDATED_LANGUAGE);
        return endings;
    }

    @BeforeEach
    public void initTest() {
        endings = createEntity(em);
    }

    @Test
    @Transactional
    public void createEndings() throws Exception {
        int databaseSizeBeforeCreate = endingsRepository.findAll().size();

        // Create the Endings
        EndingsDTO endingsDTO = endingsMapper.toDto(endings);
        restEndingsMockMvc.perform(post("/api/endings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endingsDTO)))
            .andExpect(status().isCreated());

        // Validate the Endings in the database
        List<Endings> endingsList = endingsRepository.findAll();
        assertThat(endingsList).hasSize(databaseSizeBeforeCreate + 1);
        Endings testEndings = endingsList.get(endingsList.size() - 1);
        assertThat(testEndings.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEndings.getNumberOfTypes()).isEqualTo(DEFAULT_NUMBER_OF_TYPES);
        assertThat(testEndings.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    public void createEndingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = endingsRepository.findAll().size();

        // Create the Endings with an existing ID
        endings.setId(1L);
        EndingsDTO endingsDTO = endingsMapper.toDto(endings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEndingsMockMvc.perform(post("/api/endings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Endings in the database
        List<Endings> endingsList = endingsRepository.findAll();
        assertThat(endingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEndings() throws Exception {
        // Initialize the database
        endingsRepository.saveAndFlush(endings);

        // Get all the endingsList
        restEndingsMockMvc.perform(get("/api/endings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endings.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].numberOfTypes").value(hasItem(DEFAULT_NUMBER_OF_TYPES)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getEndings() throws Exception {
        // Initialize the database
        endingsRepository.saveAndFlush(endings);

        // Get the endings
        restEndingsMockMvc.perform(get("/api/endings/{id}", endings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(endings.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.numberOfTypes").value(DEFAULT_NUMBER_OF_TYPES))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEndings() throws Exception {
        // Get the endings
        restEndingsMockMvc.perform(get("/api/endings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEndings() throws Exception {
        // Initialize the database
        endingsRepository.saveAndFlush(endings);

        int databaseSizeBeforeUpdate = endingsRepository.findAll().size();

        // Update the endings
        Endings updatedEndings = endingsRepository.findById(endings.getId()).get();
        // Disconnect from session so that the updates on updatedEndings are not directly saved in db
        em.detach(updatedEndings);
        updatedEndings
            .name(UPDATED_NAME)
            .numberOfTypes(UPDATED_NUMBER_OF_TYPES)
            .language(UPDATED_LANGUAGE);
        EndingsDTO endingsDTO = endingsMapper.toDto(updatedEndings);

        restEndingsMockMvc.perform(put("/api/endings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endingsDTO)))
            .andExpect(status().isOk());

        // Validate the Endings in the database
        List<Endings> endingsList = endingsRepository.findAll();
        assertThat(endingsList).hasSize(databaseSizeBeforeUpdate);
        Endings testEndings = endingsList.get(endingsList.size() - 1);
        assertThat(testEndings.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEndings.getNumberOfTypes()).isEqualTo(UPDATED_NUMBER_OF_TYPES);
        assertThat(testEndings.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingEndings() throws Exception {
        int databaseSizeBeforeUpdate = endingsRepository.findAll().size();

        // Create the Endings
        EndingsDTO endingsDTO = endingsMapper.toDto(endings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEndingsMockMvc.perform(put("/api/endings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Endings in the database
        List<Endings> endingsList = endingsRepository.findAll();
        assertThat(endingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEndings() throws Exception {
        // Initialize the database
        endingsRepository.saveAndFlush(endings);

        int databaseSizeBeforeDelete = endingsRepository.findAll().size();

        // Delete the endings
        restEndingsMockMvc.perform(delete("/api/endings/{id}", endings.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Endings> endingsList = endingsRepository.findAll();
        assertThat(endingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
