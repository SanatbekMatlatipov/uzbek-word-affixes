package uz.uzkaznlptools.admin.api.web.rest;

import uz.uzkaznlptools.admin.api.service.EndingsService;
import uz.uzkaznlptools.admin.api.service.dto.QueryValuesDTO;
import uz.uzkaznlptools.admin.api.web.rest.errors.BadRequestAlertException;
import uz.uzkaznlptools.admin.api.service.dto.EndingsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uz.uzkaznlptools.admin.api.domain.Endings}.
 */
@RestController
@RequestMapping("/api")
public class EndingsResource {

    private final Logger log = LoggerFactory.getLogger(EndingsResource.class);

    private static final String ENTITY_NAME = "endings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EndingsService endingsService;

    public EndingsResource(EndingsService endingsService) {
        this.endingsService = endingsService;
    }

    /**
     * {@code POST  /endings} : Create a new endings.
     *
     * @param endingsDTO the endingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new endingsDTO, or with status {@code 400 (Bad Request)} if the endings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/endings")
    public ResponseEntity<EndingsDTO> createEndings(@RequestBody EndingsDTO endingsDTO) throws URISyntaxException {
        log.debug("REST request to save Endings : {}", endingsDTO);
        if (endingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new endings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EndingsDTO result = endingsService.save(endingsDTO);
        return ResponseEntity.created(new URI("/api/endings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /endings} : Updates an existing endings.
     *
     * @param endingsDTO the endingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated endingsDTO,
     * or with status {@code 400 (Bad Request)} if the endingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the endingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/endings")
    public ResponseEntity<EndingsDTO> updateEndings(@RequestBody EndingsDTO endingsDTO) throws URISyntaxException {
        log.debug("REST request to update Endings : {}", endingsDTO);
        if (endingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EndingsDTO result = endingsService.save(endingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, endingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /endings} : get all the endings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of endings in body.
     */
    @GetMapping("/endings")
    public ResponseEntity<List<EndingsDTO>> getAllEndings(Pageable pageable) {
        log.debug("REST request to get a page of Endings");
        Page<EndingsDTO> page = endingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /endings/:id} : get the "id" endings.
     *
     * @param id the id of the endingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the endingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/endings/{id}")
    public ResponseEntity<EndingsDTO> getEndings(@PathVariable Long id) {
        log.debug("REST request to get Endings : {}", id);
        Optional<EndingsDTO> endingsDTO = endingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(endingsDTO);
    }

    /**
     * {@code DELETE  /endings/:id} : delete the "id" endings.
     *
     * @param id the id of the endingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/endings/{id}")
    public ResponseEntity<Void> deleteEndings(@PathVariable Long id) {
        log.debug("REST request to delete Endings : {}", id);
        endingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/endings/getStem")
    public ResponseEntity<QueryValuesDTO> getStem(@RequestParam("text") String text, @RequestParam("language") String language) {
        log.debug("REST request to get QueryValuesDTO : {}", text);
//        log.debug("REST request to get QueryValuesDTO : {}", id);
        log.debug("REST request to get QueryValuesDTO : {}", language);
//        Optional<EndingsDTO> endingsDTO = endingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(null);
    }
}
