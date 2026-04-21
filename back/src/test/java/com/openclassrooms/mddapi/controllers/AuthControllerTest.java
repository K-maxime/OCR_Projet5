package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.request.LoginRequestDto;
import com.openclassrooms.mddapi.dto.request.RegisterRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends AbstractControllerIntegrationTest {

    @BeforeEach
    void setUp() {
        resetDatabase();
        createDefaultUser();
    }

    @Test
    void testRegisterUser_WhenRequestIsValid_ThenReturn200() throws Exception {
        RegisterRequestDto request = new RegisterRequestDto("alice@test.com", "alice", "Password123!");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    void testRegisterUser_WhenUserAlreadyExists_ThenReturn409() throws Exception {
        RegisterRequestDto request = new RegisterRequestDto("john.doe@test.com", "john.doe", "Password123!");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().string("email ou nom d'utilisateur deja utilisé"));
    }

    @Test
    void testRegisterUser_WhenEmailIsInvalid_ThenReturn400() throws Exception {
        RegisterRequestDto request = new RegisterRequestDto("not-an-email", "alice", "Password123!");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("email: Invalid email format"));
    }

    @Test
    void testRegisterUser_WhenPasswordIsInvalid_ThenReturn400() throws Exception {
        RegisterRequestDto request = new RegisterRequestDto("alice@test.com", "alice", "weak");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void testLoginUser_WhenCredentialsAreValid_ThenReturn200() throws Exception {
        LoginRequestDto request = new LoginRequestDto("john.doe@test.com", "Password123!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("john.doe"))
                .andExpect(jsonPath("$.email").value("john.doe@test.com"));
    }

    @Test
    void testLoginUser_WhenCredentialsAreInvalid_ThenReturn401() throws Exception {
        LoginRequestDto request = new LoginRequestDto("john.doe@test.com", "WrongPassword123!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Aucun utilisateur trouve avec le login : "));
    }

    @Test
    void testLoginUser_WhenPasswordIsBlank_ThenReturn400() throws Exception {
        LoginRequestDto request = new LoginRequestDto("john.doe@test.com", "");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("password: Le mot de passe est requis"));
    }

    @Test
    void testGetAuthenticatedUser_WhenUserExists_ThenReturn200() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("john.doe@test.com"))
                .andExpect(jsonPath("$.username").value("john.doe"));
    }

    @Test
    void testGetAuthenticatedUser_WhenUserDoesNotExist_ThenReturn404() throws Exception {
        resetDatabase();

        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Aucun utilisateur trouve avec l'Id 1"));
    }

    @Test
    void testLogoutUser_WhenRequestIsValid_ThenReturn200() throws Exception {
        mockMvc.perform(post("/api/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", containsString("connexion")));
    }
}
