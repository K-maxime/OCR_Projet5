package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de demande d'inscription.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    private String email;
    private String username;
    private String password;
}
