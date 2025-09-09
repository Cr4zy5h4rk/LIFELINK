package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Article;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.ArticleRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.ArticleService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.ArticleDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.ArticleMapper;

/**
 * Service Implementation for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Article}.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public ArticleDTO save(ArticleDTO articleDTO) {
        LOG.debug("Request to save Article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);
        article = articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    @Override
    public ArticleDTO update(ArticleDTO articleDTO) {
        LOG.debug("Request to update Article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);
        article = articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    @Override
    public Optional<ArticleDTO> partialUpdate(ArticleDTO articleDTO) {
        LOG.debug("Request to partially update Article : {}", articleDTO);

        return articleRepository
            .findById(articleDTO.getId())
            .map(existingArticle -> {
                articleMapper.partialUpdate(existingArticle, articleDTO);

                return existingArticle;
            })
            .map(articleRepository::save)
            .map(articleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDTO> findAll() {
        LOG.debug("Request to get all Articles");
        return articleRepository.findAll().stream().map(articleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleDTO> findOne(Long id) {
        LOG.debug("Request to get Article : {}", id);
        return articleRepository.findById(id).map(articleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Article : {}", id);
        articleRepository.deleteById(id);
    }
}
