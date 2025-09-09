package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ArticleType;

/**
 * A DTO for the {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Article} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleDTO implements Serializable {

    private Long id;

    private String title;

    private String image;

    @Lob
    private String content;

    private String wolofAudioResume;

    private ZonedDateTime createdAt;

    private ArticleType articleType;

    private DonorDTO donor;

    private Set<DonorDTO> favoritedBies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWolofAudioResume() {
        return wolofAudioResume;
    }

    public void setWolofAudioResume(String wolofAudioResume) {
        this.wolofAudioResume = wolofAudioResume;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public DonorDTO getDonor() {
        return donor;
    }

    public void setDonor(DonorDTO donor) {
        this.donor = donor;
    }

    public Set<DonorDTO> getFavoritedBies() {
        return favoritedBies;
    }

    public void setFavoritedBies(Set<DonorDTO> favoritedBies) {
        this.favoritedBies = favoritedBies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleDTO)) {
            return false;
        }

        ArticleDTO articleDTO = (ArticleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, articleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", content='" + getContent() + "'" +
            ", wolofAudioResume='" + getWolofAudioResume() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", articleType='" + getArticleType() + "'" +
            ", donor=" + getDonor() +
            ", favoritedBies=" + getFavoritedBies() +
            "}";
    }
}
