package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Article;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.enumeration.ArticleType;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.PaginatedPostsResponse;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.PostDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.ArticleRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.DonorRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.security.DonorPrincipal;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.PostMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Article} as posts for the Flutter application.
 */
@Service
@Transactional
public class PostService {

    private final Logger log = LoggerFactory.getLogger(PostService.class);

    private final ArticleRepository articleRepository;
    private final DonorRepository donorRepository;
    private final PostMapper postMapper;

    public PostService(
            ArticleRepository articleRepository,
            DonorRepository donorRepository,
            PostMapper postMapper) {
        this.articleRepository = articleRepository;
        this.donorRepository = donorRepository;
        this.postMapper = postMapper;
    }

    /**
     * Get recent posts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PostDTO> getRecentPosts() {
        log.debug("Request to get recent posts");

        // Get current donor ID for favorite checking from security context
        String currentDonorId = getCurrentDonorId().orElse(null);

        // Get recent posts, sorted by creation date (newest first)
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Article> recentArticles = articleRepository.findByArticleType(ArticleType.OTHER, pageable);

        return recentArticles.getContent().stream()
                .map(article -> postMapper.toDto(article, currentDonorId))
                .collect(Collectors.toList());
    }

    /**
     * Get recent testimonies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PostDTO> getRecentTestimonies() {
        log.debug("Request to get recent testimonies");

        // Get current donor ID for favorite checking from security context
        String currentDonorId = getCurrentDonorId().orElse(null);

        // Get recent posts, sorted by creation date (newest first)
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Article> recentTestimonies = articleRepository.findByArticleType(ArticleType.TESTIMONY, pageable);

        return recentTestimonies.getContent().stream()
                .map(article -> postMapper.toDto(article, currentDonorId))
                .collect(Collectors.toList());
    }

    /**
     * Get paginated posts.
     *
     * @param page the page number
     * @param limit the page size
     * @param category the category (can be null)
     * @return the paginated response.
     */
    @Transactional(readOnly = true)
    public PaginatedPostsResponse getPaginatedPosts(int page, int limit, String category) {
        log.debug("Request to get paginated posts: page={}, limit={}, category={}", page, limit, category);

        // Get current donor ID for favorite checking from security context
        String currentDonorId = getCurrentDonorId().orElse(null);

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Article> articlePage;

        // Filter by category if specified
        if (category != null && category.equalsIgnoreCase("testimonies")) {
            articlePage = articleRepository.findByArticleType(ArticleType.TESTIMONY, pageable);
        } else {
            articlePage = articleRepository.findByArticleType(ArticleType.OTHER, pageable);
        }

        List<PostDTO> posts = articlePage.getContent().stream()
                .map(article -> postMapper.toDto(article, currentDonorId))
                .collect(Collectors.toList());

        return new PaginatedPostsResponse(
                posts,
                articlePage.getTotalPages(),
                page,
                (int) articlePage.getTotalElements()
        );
    }

    /**
     * Get all testimonies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PostDTO> getTestimonies() {
        log.debug("Request to get all testimonies");

        // Get current donor ID for favorite checking from security context
        String currentDonorId = getCurrentDonorId().orElse(null);

        List<Article> testimonies = articleRepository.findByArticleType(ArticleType.TESTIMONY);

        return testimonies.stream()
                .map(article -> postMapper.toDto(article, currentDonorId))
                .collect(Collectors.toList());
    }

    /**
     * Get one post by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PostDTO> getPost(Long id) {
        log.debug("Request to get Post : {}", id);

        // Get current donor ID for favorite checking from security context
        String currentDonorId = getCurrentDonorId().orElse(null);

        return articleRepository.findById(id)
                .map(article -> postMapper.toDto(article, currentDonorId));
    }

    /**
     * Toggle favorite status of a post for a donor.
     *
     * @param postId the id of the post.
     * @param donorId the id of the donor.
     * @param isFavorite the new favorite status.
     * @return the updated entity.
     */
    public Optional<PostDTO> toggleFavorite(Long postId, String donorId, boolean isFavorite) {
        log.debug("Request to toggle favorite status of post {} for donor {}: {}", postId, donorId, isFavorite);

        Optional<Article> articleOpt = articleRepository.findById(postId);
        Optional<Donor> donorOpt = donorRepository.findById(donorId);

        if (articleOpt.isPresent() && donorOpt.isPresent()) {
            Article article = articleOpt.get();
            Donor donor = donorOpt.get();

            if (isFavorite) {
                // Add to favorites if not already favorited
                if (!article.getFavoritedBies().contains(donor)) {
                    article.addFavoritedBy(donor);
                }
            } else {
                // Remove from favorites if currently favorited
                if (article.getFavoritedBies().contains(donor)) {
                    article.removeFavoritedBy(donor);
                }
            }

            article = articleRepository.save(article);
            return Optional.of(postMapper.toDto(article, donorId));
        }

        return Optional.empty();
    }

    /**
     * Get the current donor ID from the security context.
     *
     * @return an Optional containing the current donor ID, or empty if not authenticated
     */
    private Optional<String> getCurrentDonorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof DonorPrincipal) {
            DonorPrincipal principal = (DonorPrincipal) authentication.getPrincipal();
            return Optional.ofNullable(principal.getId());
        }
        return Optional.empty();
    }
}