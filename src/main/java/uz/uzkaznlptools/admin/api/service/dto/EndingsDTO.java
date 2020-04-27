package uz.uzkaznlptools.admin.api.service.dto;

import java.io.Serializable;
import java.util.Objects;
import uz.uzkaznlptools.admin.api.domain.enumeration.Language;

/**
 * A DTO for the {@link uz.uzkaznlptools.admin.api.domain.Endings} entity.
 */
public class EndingsDTO implements Serializable {
    
    private Long id;

    private String name;

    private Integer numberOfTypes;

    private Language language;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfTypes() {
        return numberOfTypes;
    }

    public void setNumberOfTypes(Integer numberOfTypes) {
        this.numberOfTypes = numberOfTypes;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EndingsDTO endingsDTO = (EndingsDTO) o;
        if (endingsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), endingsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EndingsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", numberOfTypes=" + getNumberOfTypes() +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
