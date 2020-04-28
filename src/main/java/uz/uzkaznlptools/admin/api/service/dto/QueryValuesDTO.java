package uz.uzkaznlptools.admin.api.service.dto;

public class QueryValuesDTO {

    private String root;

    private String ending;

    private Boolean hasCyrToLat;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getEnding() {
        return ending;
    }

    public void setEnding(String ending) {
        this.ending = ending;
    }

    public Boolean getHasCyrToLat() {
        return hasCyrToLat;
    }

    public void setHasCyrToLat(Boolean hasCyrToLat) {
        this.hasCyrToLat = hasCyrToLat;
    }
}
