package uz.uzkaznlptools.admin.api.web.rest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import uz.uzkaznlptools.admin.api.domain.enumeration.Language;
import uz.uzkaznlptools.admin.api.service.EndingsService;
import uz.uzkaznlptools.admin.api.service.dto.DictionaryLatinDTO;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
    public ResponseEntity<List<QueryValuesDTO>> getStem(@RequestParam("text") String text,
                                                        @RequestParam("language") Language language) {
        log.debug("REST request to get text tokens : {}", text);
        log.debug("REST request to get tokens from : {}", language);
        List<EndingsDTO> endings = endingsService.findAllEntitiesByLanguage(language);
        ArrayList<String> validTokens = getValidLowerCaseTokensFromText(text);
        List<DictionaryLatinDTO> dictionaryLatins = getAllDictionaryLatin();
        List<QueryValuesDTO> result = new ArrayList<>();
        for (String validToken : validTokens) {
            QueryValuesDTO queryValuesDTO = new QueryValuesDTO();
            String fromCyrToLat = translate(validToken, dictionaryLatins);
            queryValuesDTO.setHasCyrToLat(false);
            if (!validToken.equals(fromCyrToLat)) {
                queryValuesDTO.setHasCyrToLat(true);
                validToken = fromCyrToLat;
            }
            if (validToken.length() < 3) {
                queryValuesDTO.setRoot(validToken);
            }
            int length = validToken.length();
            boolean isEndingFound = false;
            for (int i = 2; i < length; i++) {
                String maybeRoot = validToken.substring(0, i);
                String maybeEnding = validToken.substring(i, length);
                if (containsName(endings, maybeEnding)) {
                    queryValuesDTO.setRoot(maybeRoot);
                    queryValuesDTO.setEnding(maybeEnding);
                    isEndingFound = true;
                    break;
                }
            }
            if (!isEndingFound) {
                queryValuesDTO.setRoot(validToken);
            }
            result.add(queryValuesDTO);
        }
        return ResponseEntity.ok().body(result);
    }

    public boolean containsName(final List<EndingsDTO> list, final String name) {
        return list.stream().anyMatch(o -> o.getName().equals(name));
    }

    private String translate(String validToken, List<DictionaryLatinDTO> dictionaryLatins) {
        String result = validToken;
        for (DictionaryLatinDTO dictionaryLatin : dictionaryLatins) {
            result = validToken.replaceAll(dictionaryLatin.getLetterCyrill(), dictionaryLatin.getLetterLatin());
            validToken = result;
        }
        return result;
    }

    private List<DictionaryLatinDTO> getAllDictionaryLatin() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<DictionaryLatinDTO>> response = restTemplate
            .exchange(
                "https://api.bexatobot.uz/v1/dictionary-latins",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DictionaryLatinDTO>>() {
                }
            );
        return response.getBody();
    }

    private ArrayList<String> getValidLowerCaseTokensFromText(String text) {
        text = text.replaceAll("[^A-Za-z0-9А-Яа-яўқғ'’`‘]", " ");
        String[] tokens = text.split(" ");
        ArrayList<String> result = new ArrayList<>();
        for (String token : tokens) {
            if (token.length() >= 1 && !token.equals(" ")) {
                result.add(token.toLowerCase());
            }
        }
        return result;
    }
}
