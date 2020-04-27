package uz.uzkaznlptools.admin.api.repository;

import uz.uzkaznlptools.admin.api.domain.Endings;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Endings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndingsRepository extends JpaRepository<Endings, Long> {
}
