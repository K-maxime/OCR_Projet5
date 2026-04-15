package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de demande de connexion.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    private String login;
    private String password;
}
