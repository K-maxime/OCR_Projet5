package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de creation de commentaire.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequestDto {

    private String content;
}
