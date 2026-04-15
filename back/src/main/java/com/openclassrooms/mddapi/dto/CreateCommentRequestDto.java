package com.openclassrooms.mddapi.dto;

/**
 * DTO de creation de commentaire.
 */
public class CreateCommentRequestDto {

    private String content;

    public CreateCommentRequestDto() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
