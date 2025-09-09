package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ArticleType;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;


    @Column(name = "content",columnDefinition = "TEXT")
    private String content;

    @Column(name = "wolof_audio_resume")
    private String wolofAudioResume;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "article_type")
    private ArticleType articleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "address",
            "favorites",
            "roles",
            "medicalStaff",
            "createdBloodRequests",
            "canceledBloodRequests",
            "employees",
            "bloodDonationHistories",
            "createdBies",
            "donorCampaigns",
            "userNotifications",
        },
        allowSetters = true
    )
    private Donor donor;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favorites")
    @JsonIgnoreProperties(
        value = {
            "address",
            "favorites",
            "roles",
            "medicalStaff",
            "createdBloodRequests",
            "canceledBloodRequests",
            "employees",
            "bloodDonationHistories",
            "createdBies",
            "donorCampaigns",
            "userNotifications",
        },
        allowSetters = true
    )
    private Set<Donor> favoritedBies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Article id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Article title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return this.image;
    }

    public Article image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return this.content;
    }

    public Article content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWolofAudioResume() {
        return this.wolofAudioResume;
    }

    public Article wolofAudioResume(String wolofAudioResume) {
        this.setWolofAudioResume(wolofAudioResume);
        return this;
    }

    public void setWolofAudioResume(String wolofAudioResume) {
        this.wolofAudioResume = wolofAudioResume;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Article createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ArticleType getArticleType() {
        return this.articleType;
    }

    public Article articleType(ArticleType articleType) {
        this.setArticleType(articleType);
        return this;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public Donor getDonor() {
        return this.donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public Article donor(Donor donor) {
        this.setDonor(donor);
        return this;
    }

    public Set<Donor> getFavoritedBies() {
        return this.favoritedBies;
    }

    public void setFavoritedBies(Set<Donor> donors) {
        if (this.favoritedBies != null) {
            this.favoritedBies.forEach(i -> i.removeFavorites(this));
        }
        if (donors != null) {
            donors.forEach(i -> i.addFavorites(this));
        }
        this.favoritedBies = donors;
    }

    public Article favoritedBies(Set<Donor> donors) {
        this.setFavoritedBies(donors);
        return this;
    }

    public Article addFavoritedBy(Donor donor) {
        this.favoritedBies.add(donor);
        donor.getFavorites().add(this);
        return this;
    }

    public Article removeFavoritedBy(Donor donor) {
        this.favoritedBies.remove(donor);
        donor.getFavorites().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return getId() != null && getId().equals(((Article) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", content='" + getContent() + "'" +
            ", wolofAudioResume='" + getWolofAudioResume() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", articleType='" + getArticleType() + "'" +
            "}";
    }
}
