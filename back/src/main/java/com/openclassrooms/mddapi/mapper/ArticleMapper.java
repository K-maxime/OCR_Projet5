package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.responses.ArticleResponseDto;
import com.openclassrooms.mddapi.models.Article;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<ArticleResponseDto, Article> {
}