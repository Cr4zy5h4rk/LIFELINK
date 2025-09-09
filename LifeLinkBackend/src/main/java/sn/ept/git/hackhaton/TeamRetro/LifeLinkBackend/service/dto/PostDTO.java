package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * DTO for transferring post data between client and server.
 * This DTO is designed to match the Flutter Post model structure.
 */
public class PostDTO implements Serializable {

    private String id;
    private String title;
    private String content;
    private String imageUrl;
    private ZonedDateTime publishDate;
    private String author;
    private String audioLanguage;
    private boolean hasAudio;
    private boolean isFavorite;
    private String authorImageUrl;

    // Constructors
    public PostDTO() {
        // Empty constructor needed for Jackson
    }

    // Getters and setters
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ZonedDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(ZonedDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAudioLanguage() {
        return audioLanguage;
    }

    public void setAudioLanguage(String audioLanguage) {
        this.audioLanguage = audioLanguage;
    }

    public boolean isHasAudio() {
        return hasAudio;
    }

    public void setHasAudio(boolean hasAudio) {
        this.hasAudio = hasAudio;
    }

    public boolean isIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        return id != null && id.equals(((PostDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id='" + getId() + "'" +
                ", title='" + getTitle() + "'" +
                ", content='" + getContent() + "'" +
                ", imageUrl='" + getImageUrl() + "'" +
                ", publishDate='" + getPublishDate() + "'" +
                ", author='" + getAuthor() + "'" +
                ", audioLanguage='" + getAudioLanguage() + "'" +
                ", hasAudio='" + isHasAudio() + "'" +
                ", isFavorite='" + isIsFavorite() + "'" +
                ", authorImageUrl='" + getAuthorImageUrl() + "'" +
                "}";
    }
}