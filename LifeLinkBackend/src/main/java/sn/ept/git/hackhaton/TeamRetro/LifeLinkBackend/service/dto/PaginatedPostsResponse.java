package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for paginated post responses.
 * This matches the PaginatedPostsResponse class in Flutter.
 */
public class PaginatedPostsResponse implements Serializable {

    private List<PostDTO> posts;
    private int totalPages;
    private int currentPage;
    private int totalPosts;

    // Constructors
    public PaginatedPostsResponse() {
        // Empty constructor needed for Jackson
    }

    public PaginatedPostsResponse(List<PostDTO> posts, int totalPages, int currentPage, int totalPosts) {
        this.posts = posts;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.totalPosts = totalPosts;
    }

    // Getters and setters
    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(int totalPosts) {
        this.totalPosts = totalPosts;
    }

    @Override
    public String toString() {
        return "PaginatedPostsResponse{" +
                "posts size=" + (posts != null ? posts.size() : 0) +
                ", totalPages=" + totalPages +
                ", currentPage=" + currentPage +
                ", totalPosts=" + totalPosts +
                '}';
    }
}