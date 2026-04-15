package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de creation d'article.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleRequestDto {

    private Long subjectId;
    private String title;
    private String content;
}
