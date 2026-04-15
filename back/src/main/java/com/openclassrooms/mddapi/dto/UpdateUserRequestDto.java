package com.openclassrooms.mddapi.dto;

/**
 * DTO de mise a jour du profil utilisateur.
 */
public class UpdateUserRequestDto {

    private String email;
    private String username;
    private String password;

    public UpdateUserRequestDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
