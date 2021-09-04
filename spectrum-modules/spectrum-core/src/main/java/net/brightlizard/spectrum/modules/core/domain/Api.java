package net.brightlizard.spectrum.modules.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;

public class Api implements Serializable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("version")
    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Api api = (Api) o;
        return Objects.equals(id, api.id) &&
                Objects.equals(title, api.title) &&
                Objects.equals(description, api.description) &&
                Objects.equals(version, api.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, version);
    }

    @Override
    public String toString() {
        return "Api{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

}
