package net.brightlizard.spectrum.repository.model;

import java.util.Objects;

public class Api {

    private String id;

    private String specId;

    private String title;

    private String description;

    private String version;

    private String contextPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
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
        Api api = (Api) o;
        return Objects.equals(id, api.id) &&
                Objects.equals(specId, api.specId) &&
                Objects.equals(title, api.title) &&
                Objects.equals(description, api.description) &&
                Objects.equals(version, api.version) &&
                Objects.equals(contextPath, api.contextPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, specId, title, description, version, contextPath);
    }

    @Override
    public String toString() {
        return "Api{" +
                "id='" + id + '\'' +
                ", specId='" + specId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

}
