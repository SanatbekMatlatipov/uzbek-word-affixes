package uz.uzkaznlptools.admin.api.service.dto;

import javax.validation.constraints.NotNull;

public class DictionaryLatinDTO {

    private Long id;

    @NotNull
    private String letterLatin;

    private String letterCyrill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLetterLatin() {
        return letterLatin;
    }

    public void setLetterLatin(String letterLatin) {
        this.letterLatin = letterLatin;
    }

    public String getLetterCyrill() {
        return letterCyrill;
    }

    public void setLetterCyrill(String letterCyrill) {
        this.letterCyrill = letterCyrill;
    }
}
