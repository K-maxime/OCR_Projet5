package com.openclassrooms.mddapi.dto.responses;

import com.openclassrooms.mddapi.models.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de reponse apres une demande d'information de l'utilisateur connecté.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String email;
    private String username;

}
