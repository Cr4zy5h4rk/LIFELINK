package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Article;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.Donor;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.ArticleDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.DonorDTO;

/**
 * Mapper for the entity {@link Article} and its DTO {@link ArticleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {
    @Mapping(target = "donor", source = "donor", qualifiedByName = "donorId")
    @Mapping(target = "favoritedBies", source = "favoritedBies", qualifiedByName = "donorIdSet")
    ArticleDTO toDto(Article s);

    @Mapping(target = "favoritedBies", ignore = true)
    @Mapping(target = "removeFavoritedBy", ignore = true)
    Article toEntity(ArticleDTO articleDTO);

    @Named("donorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonorDTO toDtoDonorId(Donor donor);

    @Named("donorIdSet")
    default Set<DonorDTO> toDtoDonorIdSet(Set<Donor> donor) {
        return donor.stream().map(this::toDtoDonorId).collect(Collectors.toSet());
    }
}
