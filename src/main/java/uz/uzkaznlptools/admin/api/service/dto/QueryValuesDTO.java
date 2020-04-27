package uz.uzkaznlptools.admin.api.service.dto;

import uz.uzkaznlptools.admin.api.domain.enumeration.Language;

public class QueryValuesDTO {

    private Long text;

    private Long language;

    public Long getText() {
        return text;
    }

    public void setText(Long text) {
        this.text = text;
    }

    public Long getLanguage() {
        return language;
    }

    public void setLanguage(Long language) {
        this.language = language;
    }
}
