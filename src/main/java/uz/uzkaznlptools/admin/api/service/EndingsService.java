package uz.uzkaznlptools.admin.api.service;

import uz.uzkaznlptools.admin.api.domain.Endings;
import uz.uzkaznlptools.admin.api.repository.EndingsRepository;
import uz.uzkaznlptools.admin.api.service.dto.EndingsDTO;
import uz.uzkaznlptools.admin.api.service.mapper.EndingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Endings}.
 */
@Service
@Transactional
public class EndingsService {

    private final Logger log = LoggerFactory.getLogger(EndingsService.class);

    private final EndingsRepository endingsRepository;

    private final EndingsMapper endingsMapper;

    public EndingsService(EndingsRepository endingsRepository, EndingsMapper endingsMapper) {
        this.endingsRepository = endingsRepository;
        this.endingsMapper = endingsMapper;
    }

    /**
     * Save a endings.
     *
     * @param endingsDTO the entity to save.
     * @return the persisted entity.
     */
    public EndingsDTO save(EndingsDTO endingsDTO) {
        log.debug("Request to save Endings : {}", endingsDTO);
        Endings endings = endingsMapper.toEntity(endingsDTO);
        endings = endingsRepository.save(endings);
        return endingsMapper.toDto(endings);
    }

    /**
     * Get all the endings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EndingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Endings");
        return endingsRepository.findAll(pageable)
            .map(endingsMapper::toDto);
    }

    /**
     * Get one endings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EndingsDTO> findOne(Long id) {
        log.debug("Request to get Endings : {}", id);
        return endingsRepository.findById(id)
            .map(endingsMapper::toDto);
    }

    /**
     * Delete the endings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Endings : {}", id);
        endingsRepository.deleteById(id);
    }
}
