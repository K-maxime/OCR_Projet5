package com.openclassrooms.mddapi.dto;

/**
 * DTO de demande de connexion.
 */
public class LoginRequestDto {

    private String login;
    private String password;

    public LoginRequestDto() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
