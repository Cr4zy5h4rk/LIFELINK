package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Article;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.PostDTO;

/**
 * Mapper interface for converting between {@link Article} and {@link PostDTO}.
 */
public interface PostMapper {

    /**
     * Convert an Article entity to a PostDTO.
     *
     * @param article the entity to convert
     * @param currentDonorId the ID of the current donor (for checking favorite status)
     * @return the DTO
     */
    PostDTO toDto(Article article, String currentDonorId);

    /**
     * Convert a PostDTO to an Article entity.
     *
     * @param postDTO the DTO to convert
     * @return the entity
     */
    Article toEntity(PostDTO postDTO);
}