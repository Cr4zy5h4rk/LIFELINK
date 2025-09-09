package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Article;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ArticleType;

import java.util.List;

/**
 * Spring Data JPA repository for the Article entity.
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * Find all articles of a specific type.
     *
     * @param articleType the article type to filter by
     * @return a list of articles
     */
    List<Article> findByArticleType(ArticleType articleType);

    /**
     * Find all articles of a specific type with pagination.
     *
     * @param articleType the article type to filter by
     * @param pageable the pagination information
     * @return a page of articles
     */
    Page<Article> findByArticleType(ArticleType articleType, Pageable pageable);
}