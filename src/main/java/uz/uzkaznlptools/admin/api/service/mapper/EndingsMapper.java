package uz.uzkaznlptools.admin.api.service.mapper;


import uz.uzkaznlptools.admin.api.domain.*;
import uz.uzkaznlptools.admin.api.service.dto.EndingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Endings} and its DTO {@link EndingsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EndingsMapper extends EntityMapper<EndingsDTO, Endings> {



    default Endings fromId(Long id) {
        if (id == null) {
            return null;
        }
        Endings endings = new Endings();
        endings.setId(id);
        return endings;
    }
}
