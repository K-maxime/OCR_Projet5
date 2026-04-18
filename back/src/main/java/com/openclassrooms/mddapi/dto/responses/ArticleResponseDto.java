package com.openclassrooms.mddapi.dto.responses;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    // Relations (pour le front)
    private SubjectResponseDto subject;
    private UserResponseDto author;
    private List<CommentResponseDto> comments;


}