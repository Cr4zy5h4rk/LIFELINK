package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.PaginatedPostsResponse;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.PostDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.security.DonorPrincipal;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.PostService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing Posts.
 */
@RestController
@CrossOrigin(origins = "*")
public class PostController {

    private final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * GET /recent-posts : get recent posts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of posts in body
     */
    @GetMapping("/recent-posts")
    public ResponseEntity<List<PostDTO>> getRecentPosts() {
        log.debug("REST request to get recent posts");
        List<PostDTO> posts = postService.getRecentPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * GET /posts/paginated : get paginated posts.
     *
     * @param page the page number (default: 1)
     * @param limit the number of posts per page (default: 10)
     * @param category the category filter (optional)
     * @return the ResponseEntity with status 200 (OK) and the paginated posts in body
     */
    @GetMapping("/posts/paginated")
    public ResponseEntity<PaginatedPostsResponse> getPaginatedPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String category) {

        log.debug("REST request to get paginated posts: page={}, limit={}, category={}", page, limit, category);
        PaginatedPostsResponse response = postService.getPaginatedPosts(page, limit, category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /testimonies : get all testimonies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of testimonies in body
     */
    @GetMapping("/testimonies")
    public ResponseEntity<List<PostDTO>> getTestimonies() {
        log.debug("REST request to get all testimonies");
        List<PostDTO> testimonies = postService.getTestimonies();
        return new ResponseEntity<>(testimonies, HttpStatus.OK);
    }

    /**
     * GET /posts/:id : get the "id" post.
     *
     * @param id the id of the post to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the post, or with status 404 (Not Found)
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable String id) {
        log.debug("REST request to get Post : {}", id);

        try {
            Long postId = Long.parseLong(id);
            Optional<PostDTO> post = postService.getPost(postId);
            return post.map(response -> ResponseEntity.ok().body(response))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (NumberFormatException e) {
            log.error("Invalid post ID format: {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * PUT /posts/:id/favorite : Toggle favorite status of a post.
     *
     * @param id the id of the post to favorite/unfavorite
     * @param favoriteRequest the request with favorite status
     * @param userPrincipal the authenticated user
     * @return the ResponseEntity with status 200 (OK) and with body the updated post,
     *         or with status 404 (Not Found)
     */
    @PutMapping("/posts/{id}/favorite")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostDTO> toggleFavorite(
            @PathVariable String id,
            @RequestBody Map<String, Boolean> favoriteRequest,
            @AuthenticationPrincipal DonorPrincipal userPrincipal) {

        log.debug("REST request to toggle favorite for Post : {}", id);

        Boolean isFavorite = favoriteRequest.get("isFavorite");
        if (isFavorite == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Long postId = Long.parseLong(id);
            Optional<PostDTO> updatedPost = postService.toggleFavorite(postId, userPrincipal.getId(), isFavorite);
            return updatedPost.map(response -> ResponseEntity.ok().body(response))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (NumberFormatException e) {
            log.error("Invalid post ID format: {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}