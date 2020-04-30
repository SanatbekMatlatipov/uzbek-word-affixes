package uz.uzkaznlptools.admin.api.repository;

import uz.uzkaznlptools.admin.api.domain.Endings;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.uzkaznlptools.admin.api.domain.enumeration.Language;

import java.util.List;

/**
 * Spring Data  repository for the Endings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndingsRepository extends JpaRepository<Endings, Long> {

    List<Endings> findAllByLanguageOrderByNumberOfTypesDesc(Language language);
}
