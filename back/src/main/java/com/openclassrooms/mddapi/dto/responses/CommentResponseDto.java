package com.openclassrooms.mddapi.dto.responses;

import lombok.Data;

@Data
public class CommentResponseDto {

    private Long id;
    private String content;
    private UserResponseDto author;
    private java.time.LocalDateTime createdAt;

}
