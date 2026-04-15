package com.openclassrooms.mddapi.dto;

/**
 * DTO de creation d'article.
 */
public class CreateArticleRequestDto {

    private Long subjectId;
    private String title;
    private String content;

    public CreateArticleRequestDto() {
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
