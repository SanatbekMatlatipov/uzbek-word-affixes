package uz.uzkaznlptools.admin.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import uz.uzkaznlptools.admin.api.domain.enumeration.Language;

/**
 * A Endings.
 */
@Entity
@Table(name = "main_endings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Endings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_types")
    private Integer numberOfTypes;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Endings name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfTypes() {
        return numberOfTypes;
    }

    public Endings numberOfTypes(Integer numberOfTypes) {
        this.numberOfTypes = numberOfTypes;
        return this;
    }

    public void setNumberOfTypes(Integer numberOfTypes) {
        this.numberOfTypes = numberOfTypes;
    }

    public Language getLanguage() {
        return language;
    }

    public Endings language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Endings)) {
            return false;
        }
        return id != null && id.equals(((Endings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Endings{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", numberOfTypes=" + getNumberOfTypes() +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
