package com.openclassrooms.mddapi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de reponse apres une authentification reussie.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private String token;

}
