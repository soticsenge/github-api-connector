package github.repository.connector.models;

public class RepoInfo {
    private String fullName;
    private String owner;
    private String url;
    private String description;
    private String language;

    public RepoInfo(String fullName, String owner, String url, String description, String language) {
        this.fullName = fullName;
        this.owner = owner;
        this.url = url;
        this.description = description;
        this.language = language;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
