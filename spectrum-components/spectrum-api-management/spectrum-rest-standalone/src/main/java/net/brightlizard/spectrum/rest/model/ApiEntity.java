package net.brightlizard.spectrum.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.brightlizard.spectrum.repository.model.Api;
import java.util.Objects;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
public class ApiEntity {
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("version")
    private String version;

    @JsonProperty("context_path")
    private String contextPath;

    public ApiEntity() {
    }

    public ApiEntity(Api api) {
        this.id = api.getId();
        this.title = api.getTitle();
        this.version = api.getVersion();
        this.description = api.getDescription();
        this.contextPath = api.getContextPath();
    }

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

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiEntity apiEntity = (ApiEntity) o;
        return Objects.equals(id, apiEntity.id) &&
                Objects.equals(title, apiEntity.title) &&
                Objects.equals(description, apiEntity.description) &&
                Objects.equals(version, apiEntity.version) &&
                Objects.equals(contextPath, apiEntity.contextPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, version, contextPath);
    }
}
