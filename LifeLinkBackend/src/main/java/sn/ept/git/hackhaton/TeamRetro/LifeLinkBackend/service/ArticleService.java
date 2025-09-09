package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import java.util.List;
import java.util.Optional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.ArticleDTO;

/**
 * Service Interface for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Article}.
 */
public interface ArticleService {
    /**
     * Save a article.
     *
     * @param articleDTO the entity to save.
     * @return the persisted entity.
     */
    ArticleDTO save(ArticleDTO articleDTO);

    /**
     * Updates a article.
     *
     * @param articleDTO the entity to update.
     * @return the persisted entity.
     */
    ArticleDTO update(ArticleDTO articleDTO);

    /**
     * Partially updates a article.
     *
     * @param articleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArticleDTO> partialUpdate(ArticleDTO articleDTO);

    /**
     * Get all the articles.
     *
     * @return the list of entities.
     */
    List<ArticleDTO> findAll();

    /**
     * Get the "id" article.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleDTO> findOne(Long id);

    /**
     * Delete the "id" article.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
