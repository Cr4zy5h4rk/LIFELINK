package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import org.springframework.stereotype.Service;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Article;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ArticleType;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.PostDTO;

import java.time.ZonedDateTime;

/**
 * Implementation of the PostMapper interface for converting between {@link Article} and {@link PostDTO}.
 */
@Service
public class PostMapperImpl implements PostMapper {

    /**
     * Convert an Article entity to a PostDTO.
     *
     * @param article the entity to convert
     * @param currentDonorId the ID of the current donor (for checking favorite status)
     * @return the DTO
     */
    @Override
    public PostDTO toDto(Article article, String currentDonorId) {
        if (article == null) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        // Set ID as String (Flutter expects String IDs)
        postDTO.setId(article.getId().toString());

        // Set title
        postDTO.setTitle(article.getTitle());

        // Set content
        postDTO.setContent(article.getContent());

        // Set image URL
        postDTO.setImageUrl(article.getImage());

        // Set publish date
        postDTO.setPublishDate(article.getCreatedAt());

        // Set author information
        Donor author = article.getDonor();
        if (author != null) {
            String authorName = (author.getFirstName() != null && author.getLastName() != null)
                    ? author.getFirstName() + " " + author.getLastName()
                    : "Auteur anonyme";
            postDTO.setAuthor(authorName);
            postDTO.setAuthorImageUrl(author.getPicture());
        } else {
            postDTO.setAuthor("Auteur anonyme");
        }

        // Set audio information
        boolean hasAudio = article.getWolofAudioResume() != null && !article.getWolofAudioResume().isEmpty();
        postDTO.setHasAudio(hasAudio);
        if (hasAudio) {
            postDTO.setAudioLanguage("Wolof");
        }

        // Check if current donor has favorited this article
        boolean isFavorite = false;
        if (currentDonorId != null && article.getFavoritedBies() != null) {
            isFavorite = article.getFavoritedBies().stream()
                    .anyMatch(donor -> donor.getId() != null && donor.getId().equals(currentDonorId));
        }
        postDTO.setIsFavorite(isFavorite);

        return postDTO;
    }

    /**
     * Convert a PostDTO to an Article entity.
     *
     * @param postDTO the DTO to convert
     * @return the entity
     */
    @Override
    public Article toEntity(PostDTO postDTO) {
        if (postDTO == null) {
            return null;
        }

        Article article = new Article();

        // Set ID if provided
        if (postDTO.getId() != null && !postDTO.getId().isEmpty()) {
            try {
                article.setId(Long.parseLong(postDTO.getId()));
            } catch (NumberFormatException e) {
                // Log error or handle appropriately
            }
        }

        // Set basic fields
        article.setTitle(postDTO.getTitle());
        article.setContent(postDTO.getContent());
        article.setImage(postDTO.getImageUrl());

        // Set creation date
        article.setCreatedAt(postDTO.getPublishDate() != null ?
                postDTO.getPublishDate() : ZonedDateTime.now());

        // Set article type - default to OTHER
        article.setArticleType(ArticleType.OTHER);

        // Set audio information
        if (postDTO.isHasAudio() && "Wolof".equals(postDTO.getAudioLanguage())) {
            article.setWolofAudioResume("https://lifelink.sn/audio/post_" +
                    (postDTO.getId() != null ? postDTO.getId() : "new") + "_wolof.mp3");
        }

        return article;
    }
}